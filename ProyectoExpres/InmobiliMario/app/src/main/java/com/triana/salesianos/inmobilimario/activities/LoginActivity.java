package com.triana.salesianos.inmobilimario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.models.LoginResponse;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.services.LoginService;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btn_login, btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        btn_login = findViewById(R.id.btnLogin);
        btn_signup = findViewById(R.id.btnSignUp);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username_txt = email.getText().toString();
                String password_txt = password.getText().toString();

                String credentials = Credentials.basic(username_txt, password_txt);

                LoginService service = ServiceGenerator.createService(LoginService.class);
                Call<LoginResponse> call = service.doLogin(credentials);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.code() != 201) {
                            // error
                            Log.e("RequestError", response.message());
                            Toast.makeText(LoginActivity.this, "Error de petición", Toast.LENGTH_SHORT).show();
                        } else {
                            UtilToken.setToken(LoginActivity.this, response.body().getToken());

                            startActivity(new Intent(LoginActivity.this, UserListActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.e("NetworkFailure", t.getMessage());
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

    }
}

