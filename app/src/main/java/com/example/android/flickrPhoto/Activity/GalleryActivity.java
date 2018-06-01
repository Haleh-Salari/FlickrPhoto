package com.example.android.flickrPhoto.Activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.flickrPhoto.fragment.GalleryFragment;
import com.example.android.flickrPhoto.R;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        refreshGallery();
    }

    //clear the gallery and fill it depends on show options with public or private photo
    private void refreshGallery(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.gallery_fragment);
        if (fragment != null) {
            ((GalleryFragment) fragment).onStop();

            Intent intent = this.getIntent();
            ((GalleryFragment) fragment).fragmentShowOptions = intent.getStringExtra("showOptions");

            ((GalleryFragment) fragment).refresh();
        }
    }

}