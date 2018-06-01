package com.example.android.flickrPhoto.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.flickrPhoto.R;
import com.example.android.flickrPhoto.fragment.PhotoFragment;

public class PhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        retrievePhoto();
    }

    //retrieve photo by using photo fragment
    private void retrievePhoto() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = new PhotoFragment();
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
