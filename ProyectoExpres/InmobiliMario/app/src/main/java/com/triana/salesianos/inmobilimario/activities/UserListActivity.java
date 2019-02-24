package com.triana.salesianos.inmobilimario.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.triana.salesianos.inmobilimario.models.ResponseContainer;
import com.triana.salesianos.inmobilimario.models.User;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.generator.TipoAutenticacion;
import com.triana.salesianos.inmobilimario.retrofit.services.OtherService;
import com.triana.salesianos.inmobilimario.UtilToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String jwt = UtilToken.getToken(this);

        if (jwt == null) {

        }

        OtherService service = ServiceGenerator.createService(OtherService.class,
                jwt, TipoAutenticacion.JWT);

        Call<ResponseContainer<User>> callList =  service.listUsers();


        callList.enqueue(new Callback<ResponseContainer<User>>() {
            @Override
            public void onResponse(Call<ResponseContainer<User>> call, Response<ResponseContainer<User>> response) {
                if (response.isSuccessful()) {
                    cargarDatos(response.body().getRows());
                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<User>> call, Throwable t) {

            }
        });



    }

    public void cargarDatos(List<User> datos) {
        setListAdapter(new ArrayAdapter<User>(this, android.R.layout.simple_list_item_2, datos) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                User user = getItem(position);
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
                }
                TextView txtName = convertView.findViewById(android.R.id.text1);
                TextView txtEmail = convertView.findViewById(android.R.id.text2);
                txtName.setText(user.getName());
                txtEmail.setText(user.getEmail());
                return convertView;
            }
        });
    }

}
