package com.triana.salesianos.inmobilimario.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.triana.salesianos.inmobilimario.R;
import com.triana.salesianos.inmobilimario.UtilToken;
import com.triana.salesianos.inmobilimario.fragments.PostFragment;
import com.triana.salesianos.inmobilimario.fragments.PostInteractionListener;
import com.triana.salesianos.inmobilimario.fragments.ProfileFragment;
import com.triana.salesianos.inmobilimario.fragments.ProfileInteractionListener;

public class MainActivity extends AppCompatActivity implements PostInteractionListener, ProfileInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment f = null;

            switch (item.getItemId()) {
                case R.id.navigation_posts:
                    f = new PostFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, f)
                            .commit();
                    return true;
                case R.id.navigation_map:

                    return true;
                case R.id.navigation_profile:
                    if(UtilToken.getToken(MainActivity.this) == null) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }else{
                       f = new ProfileFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, f)
                                .commit();
                    }
                    return true;
            }

            if (f != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, f)
                        .commit();
                return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, new PostFragment())
                .commit();

    }

    @Override
    public void deletePost(String id) {

    }

    @Override
    public void logOut(String id) {

    }

    @Override
    public void favourites() {

    }

    @Override
    public void myPosts() {

    }
}
