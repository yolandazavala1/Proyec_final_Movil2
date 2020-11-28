package com.example.proyec_final_movil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.clans.fab.FloatingActionButton;


public class Agregar extends AppCompatActivity {
    FloatingActionButton btnTomarfoto;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        //setSupportActionBar(myToolbar);       new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnTomarfoto=findViewById(R.id.fabfoto);
        imageView=findViewById(R.id.imgfoto);
        btnTomarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            abrircamara();
            }
        });
    }
    private void abrircamara(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
         startActivityForResult(intent,1);
        }
    }
    protected void onActivityResult(int recues,int resulcode,Intent data) {
        super.onActivityResult(recues, resulcode, data);
        if (recues == 1 && resulcode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);
        }
    }
}