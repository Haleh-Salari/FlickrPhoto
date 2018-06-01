package com.example.android.flickrPhoto.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.android.flickrPhoto.R;

public class MainActivity extends AppCompatActivity {

    public Button btn_ShowPublicPhotos;
    public Button btn_ShowPrivatePhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        btn_ShowPublicPhotos = (Button)findViewById(R.id.btn_ShowPublicPhotos);
        btn_ShowPublicPhotos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                defineNextIntent("showPublicPhotos");
            }
        });

        btn_ShowPrivatePhotos = (Button)findViewById(R.id.btn_ShowPrivatePhotos);
        btn_ShowPrivatePhotos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                defineNextIntent("showPrivatePhotos");
            }
        });
    }

    //define the next intent and pass the show options to it.
    private void defineNextIntent (String showOptions){
        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
        intent.putExtra("showOptions", showOptions);
        startActivity(intent);
    }

}