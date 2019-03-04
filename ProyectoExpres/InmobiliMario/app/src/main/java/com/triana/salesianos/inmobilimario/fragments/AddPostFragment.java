package com.triana.salesianos.inmobilimario.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.User;
import com.triana.salesianos.inmobilimario.retrofit.generator.AuthType;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.services.OtherService;
import com.triana.salesianos.inmobilimario.retrofit.services.PostService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPostFragment extends DialogFragment {

    private static final String ARG_ID_USUARIO = "id_usuario";
    private static final int READ_REQUEST_CODE = 42;
    private int idNewPost;
    private EditText etTitle, etD, etDimensiones;
    private ImageView imgCargada;
    private Button btnUpload ;
    private Uri uriSelected;
    private User userL;
    MultipartBody.Part body;
    Context ctx;


    public AddPostFragment() {

    }

    public void performFileSearch(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void onActivityResult( int requestCode, int resultCode,
                                  Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
                Glide
                        .with(this)
                        .load(uri)
                        .into(imgCargada);
                uriSelected = uri;

            }
        }
    }

    public static AddPostFragment newInstance(String idUsuario) {
        AddPostFragment fragment = new AddPostFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_USUARIO, idUsuario);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idNewHuerto = getArguments().getInt(ARG_ID_USUARIO);
        }
    }



    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setTitle("Añadir Post");
        builder.setMessage("")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final PostService service = ServiceGenerator.createService(PostService.class,
                                UtilToken.getToken(ctx), AuthType.JWT);

                        if (uriSelected != null) {

                            try {
                                InputStream inputStream = getActivity().getContentResolver().openInputStream(uriSelected);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                                int cantBytes;
                                byte[] buffer = new byte[1024*4];

                                while ((cantBytes = bufferedInputStream.read(buffer,0,1024*4)) != -1) {
                                    baos.write(buffer,0,cantBytes);
                                }


                                RequestBody requestFile =
                                        RequestBody.create(
                                                MediaType.parse(getActivity().getContentResolver().getType(uriSelected)), baos.toByteArray());


                                body =
                                        MultipartBody.Part.createFormData("foto", "foto", requestFile);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        }


                        OtherService serviceUser = ServiceGenerator.createService(OtherService.class,
                                UtilToken.getToken(ctx), AuthType.JWT);

                        Call<User> call = serviceUser.getUser(UtilToken.getIdUser(ctx));
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {

                                    userL = response.body();
                                    userL.getId();
                                    RequestBody nombre = RequestBody.create(MultipartBody.FORM, etNombre.getText().toString().trim());
                                    RequestBody direccion = RequestBody.create(MultipartBody.FORM, etDireccion.getText().toString().trim());
                                    RequestBody dimensiones = RequestBody.create(MultipartBody.FORM, etDimensiones.getText().toString().trim());
                                    RequestBody user = RequestBody.create(MultipartBody.FORM, UtilToken.getIdUser(ctx).trim());
                                    Call <PostResponse> calla = service.create(body, nombre,direccion,dimensiones,user);
                                    calla.enqueue(new Callback<PostResponse>() {
                                        @Override
                                        public void onResponse(Call<PostResponse> calla, Response<PostResponse> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(ctx, "Response Successful", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(ctx, "Response False", Toast.LENGTH_LONG).show();
                                            }


                                        }

                                        @Override
                                        public void onFailure(Call<PostResponse> calla, Throwable t) {
                                            // Toast
                                            Log.i("onFailure", "error en retrofit");
                                        }
                                    });
                                    //recyclerView.setAdapter(new MyHuertoRecyclerViewAdapter(ctx, response.body().getRows(), mListener));
                                } else {
                                    // Toast
                                }

                            }
                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                // Toast
                                Log.i("onFailure", "error en retrofit");
                            }
                        });


                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_add_post, null);

        etNombre = v.findViewById(R.id.editNombreH);
        etDireccion = v.findViewById(R.id.editDireccionH);
        etDimensiones = v.findViewById(R.id.editDimensionesH);
        imgCargada = v.findViewById(R.id.imagenPrev);
        btnUpload = v.findViewById(R.id.buttonUploadImg);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();


            }
        });



        // Llamaría a Retrofit con el idUsuario que he recibido
        // y en el método onResponse de Retrofit tendría que poner
        // todas las líneas de código que vienen a continuación

        builder.setView(v);
        // Create the AlertDialog object and return it
        return builder.create();
    }



    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        this.ctx = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
