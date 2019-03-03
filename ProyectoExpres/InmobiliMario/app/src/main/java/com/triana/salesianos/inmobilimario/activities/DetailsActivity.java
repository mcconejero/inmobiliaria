package com.triana.salesianos.inmobilimario.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.models.FavsResponse;
import com.triana.salesianos.inmobilimario.models.PhotoResponse;
import com.triana.salesianos.inmobilimario.models.PhotoUploadResponse;
import com.triana.salesianos.inmobilimario.models.PostResponse;
import com.triana.salesianos.inmobilimario.models.ResponseContainer;
import com.triana.salesianos.inmobilimario.retrofit.generator.AuthType;
import com.triana.salesianos.inmobilimario.retrofit.generator.ServiceGenerator;
import com.triana.salesianos.inmobilimario.retrofit.services.PhotoService;
import com.triana.salesianos.inmobilimario.retrofit.services.PostService;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ImageView picture, ivLeft, ivRight;
    private Context ctx;
    private PostResponse post;
    private int count = 0;
    private MapView mvDetails;
    private PostService service;
    private GoogleMap gMap;
    private ImageButton deletePicture;
    private FloatingActionButton addPicture;
    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    Map options = new HashMap();
    private TextView title, description, price, size, room, zipcode, address, category, city;
    public static final int READ_REQUEST_CODE = 42;
    Uri uriSelected;
    String jwt, idUser;
    private PhotoService servicePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        jwt = UtilToken.getToken(DetailsActivity.this);
        idUser = UtilToken.getToken(getApplicationContext());
        options.put("near", "-6.0071807999999995,37.3803677");
        System.out.println(idUser);
        setSupportActionBar(toolbar);
        checkOwnerPhotos();
        Intent i = getIntent();
        post = (PostResponse) i.getSerializableExtra("post");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadItems();
        setItems();
        if (post.getPhotos().size() == 0) {
            ivLeft.setImageDrawable(null);
            ivRight.setImageDrawable(null);
        } else {
            ivRight.setOnClickListener(v -> changePictureRight());
            ivLeft.setOnClickListener(v -> changePictureLeft());
        }

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        mvDetails = findViewById(R.id.map);
        mvDetails.onCreate(mapViewBundle);
        mvDetails.getMapAsync(this);
        ivRight.setOnClickListener(v -> changePictureRight());
        ivLeft.setOnClickListener(v -> changePictureLeft());

        addPicture.setOnClickListener(v -> {
            performFileSearch();
        });
        deletePicture.setOnClickListener(v -> deletePhoto());
    }

    private void loadItems() {
        ctx = this;

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        price = findViewById(R.id.Price);
        size = findViewById(R.id.size);
        room = findViewById(R.id.rooms);
        address = findViewById(R.id.address);
        zipcode = findViewById(R.id.zipcode);
        category = findViewById(R.id.category);
        city = findViewById(R.id.city);
        mvDetails = findViewById(R.id.map);
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
        picture = findViewById(R.id.ivPicture);
        addPicture = findViewById(R.id.addPicture);
        deletePicture = findViewById(R.id.deletePhoto);

        if (post.getPhotos().size() == 0) {
            Glide.with(this).load("https://bigriverequipment.com/wp-content/uploads/2017/10/no-photo-available.png")
                    .centerCrop()
                    .into(picture);
        } else {
            Glide.with(this).load(post.getPhotos().get(0)).centerCrop().into(picture);
        }

        if (jwt == null) {
            addPicture.setImageDrawable(null);
        }


    }

    public void deletePhoto() {
        servicePicture = ServiceGenerator.createService(PhotoService.class);
        Call<ResponseContainer<PhotoResponse>> callList = servicePicture.getAll();
        callList.enqueue(new Callback<ResponseContainer<PhotoResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<PhotoResponse>> call, Response<ResponseContainer<PhotoResponse>> response) {
                if (response.isSuccessful()) {

                    for (PhotoResponse photo : response.body().getRows()) {
                        if (photo.getImgurlink().equals(post.getPhotos().get(count))) {
                            PhotoService servicePhotoDelete = ServiceGenerator.createService(PhotoService.class, jwt, AuthType.JWT);
                            Call<PhotoResponse> callDelete = servicePhotoDelete.delete(photo.getId());
                            callDelete.enqueue(new Callback<PhotoResponse>() {
                                @Override
                                public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(DetailsActivity.this, "Photo deleted", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(DetailsActivity.this, "no is.Successful DELETE", Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<PhotoResponse> call, Throwable t) {
                                    Toast.makeText(DetailsActivity.this, "Failure DELETE", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }
                }else {
                    Toast.makeText(DetailsActivity.this, "no is.Successful GET", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<PhotoResponse>> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Failure GET", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Log.i("Filechooser URI", "Uri: " + uri.toString());
            }
            uriSelected = uri;
        }

        uploadPhoto();
    }

    public void setItems() {

        title.setText(post.getTitle());
        description.setText(post.getDescription());
        price.setText(String.valueOf(post.getPrice()) + "€");
        address.setText(post.getAddress());
        room.setText(String.valueOf(post.getRooms()));
        size.setText(String.valueOf(post.getSize()) + "/m2");
        city.setText(post.getZipcode() + ", " + post.getCity() + ", " + post.getProvince());
        category.setText(post.getCategoryId().getName());

    }


    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("image/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    public void changePictureRight() {

        Glide
                .with(ctx)
                .load(post.getPhotos().get(count))
                .into(picture);

        count++;
        if (count >= post.getPhotos().size()) {
            count = 0;
        }


    }

    public void changePictureLeft() {

        Glide
                .with(ctx)
                .load(post.getPhotos().get(count))
                .into(picture);
        count--;
        if (count < 0) {
            count = post.getPhotos().size() - 1;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(10);
        String loc = post.getLoc();
        String[] locs = loc.split(",");
        locs[0].trim();
        locs[1].trim();
        float latitud = Float.parseFloat(locs[0]);
        float longitud = Float.parseFloat(locs[1]);


        LatLng position = new LatLng(latitud, longitud);
        googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(post.getAddress())
                .snippet("dam.javazquez.inmoapp")
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location))
        );
        gMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        mvDetails.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvDetails.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mvDetails.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mvDetails.onStop();
    }

    @Override
    protected void onPause() {
        mvDetails.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mvDetails.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mvDetails.onLowMemory();
    }

    public void uploadPhoto() {

        try {
            InputStream inputStream = getContentResolver().openInputStream(uriSelected);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            int cantBytes;
            byte[] buffer = new byte[1024 * 4];

            while ((cantBytes = bufferedInputStream.read(buffer, 0, 1024 * 4)) != -1) {
                baos.write(buffer, 0, cantBytes);
            }


            RequestBody requestFile =
                    RequestBody.create(
                            MediaType.parse(getContentResolver().getType(uriSelected)), baos.toByteArray());


            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("picture", "picture", requestFile);

            RequestBody propertyId = RequestBody.create(MultipartBody.FORM, post.getId());

            PhotoService servicePhoto = ServiceGenerator.createService(PhotoService.class, jwt, AuthType.JWT);
            Call<PhotoUploadResponse> callPhoto = servicePhoto.upload(body, propertyId);
            callPhoto.enqueue(new Callback<PhotoUploadResponse>() {
                @Override
                public void onResponse(Call<PhotoUploadResponse> call, Response<PhotoUploadResponse> response) {

                    if (response.isSuccessful()) {
                        post.getPhotos().add(response.body().getId());
                        Log.d("Uploaded", "Éxito");
                        Log.d("Uploaded", response.body().toString());
                        System.out.println(response.code());

                    } else {
                        Log.e("Upload error", response.errorBody().toString());
                    }

                }

                @Override
                public void onFailure(Call<PhotoUploadResponse> call, Throwable t) {
                    Log.e("Upload error", t.getMessage());
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Trato de comprobar si el inmueble en el que se ha pulsado se encuentra entre los que el usuario logueado
     * ha publicado, para que si lo es, enseñe los botones de añadir y eliminar fotos al inmueble, y si no lo es
     * que no pueda hacerlo
     */
    public void checkOwnerPhotos(){
        service = ServiceGenerator.createService(PostService.class, jwt, AuthType.JWT);
        Call<ResponseContainer<FavsResponse>> callProp = service.getMine();
        callProp.enqueue(new Callback<ResponseContainer<FavsResponse>>() {
            @Override
            public void onResponse(Call<ResponseContainer<FavsResponse>> call, Response<ResponseContainer<FavsResponse>> response) {
                if(response.isSuccessful()){
                    for(FavsResponse propertyMine : response.body().getRows()){
                        System.out.println(propertyMine.getId());
                        System.out.println(post.getId());
                        if(propertyMine.getId().equals(post.getId())){
                            Log.d("ok", "ok");
                        }else{
                            /*addPicture.setImageDrawable(null);
                            deletePhoto.setImageDrawable(null);*/
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<FavsResponse>> call, Throwable t) {

            }
        });
    }


}