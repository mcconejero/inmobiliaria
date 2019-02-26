package com.triana.salesianos.inmobilimario.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.models.LoginResponse;
import com.triana.salesianos.inmobilimario.models.SignUp;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.services.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    EditText etName, etEmail, etPassword, etRepeatPassword;
    CheckBox cbCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etRepeatPassword = findViewById(R.id.etRPassword);
        cbCredentials = findViewById(R.id.cbCredentials);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().matches("")
                        || etEmail.getText().toString().matches("")
                        || etPassword.getText().toString().matches("")
                        || etRepeatPassword.getText().toString().matches("")){
                    Toast.makeText(SignUpActivity.this, "Debe introducir todos los campos.", Toast.LENGTH_SHORT).show();
                } else if(!cbCredentials.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "Debe leer y aceptar los términos y condiciones.", Toast.LENGTH_SHORT).show();
                } else if (!etEmail.getText().toString().matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$")) {
                    Toast.makeText(SignUpActivity.this, "Debe introducir un correo válido.", Toast.LENGTH_SHORT).show();
                } else if (!etPassword.getText().toString().equals(etRepeatPassword.getText().toString())) {
                    Toast.makeText(SignUpActivity.this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
                } else {

                    // Recoger los datos del formulario
                    String firstName = etName.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();
                    String pass = etPassword.getText().toString().trim();

                    SignUp signUp = new SignUp(firstName, email, pass);

                    LoginService service = ServiceGenerator.createService(LoginService.class);

                    Call<LoginResponse> loginReponseCall = service.doSignUp(signUp);

                    loginReponseCall.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.code() == 201) {
                                //ServiceGenerator.jwtToken = response.body().getToken();
                                UtilToken.setToken(SignUpActivity.this, response.body().getToken());
                                UtilToken.setIdUser(SignUpActivity.this, response.body().getUser().getId());

                                startActivity(new Intent(SignUpActivity.this, MainActivity.class));                        // Toast.makeText(RegistroActivity.this, "Usuario registrado y logeado con éxito", Toast.LENGTH_LONG).show();
                                finish();

                            } else {
                                // error
                                Toast.makeText(SignUpActivity.this, "Error en el signUp. Revise los datos introducidos", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Log.e("NetworkFailure", t.getMessage());
                            Toast.makeText(SignUpActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();

                        }
                    });






                }

            }
        });
    }

}
