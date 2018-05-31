package com.example.android.flickrPhoto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.android.flickrPhoto.R;

public class MainActivity extends AppCompatActivity {

    public Button btn_ShowPublicPhoto;
    public void init(){
        btn_ShowPublicPhoto = (Button)findViewById(R.id.btn_ShowPublicPhoto);
        btn_ShowPublicPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
            startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
}