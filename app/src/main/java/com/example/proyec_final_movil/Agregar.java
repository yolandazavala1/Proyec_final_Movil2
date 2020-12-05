package com.example.proyec_final_movil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyec_final_movil.data.DaoNotas;
import com.example.proyec_final_movil.data.DaoTareas;
import com.example.proyec_final_movil.data.Notas;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;


public class Agregar extends AppCompatActivity {
    FloatingActionButton btnTomarfoto;
    ImageView imageView;
    Button Guardar,mos;
    EditText Titulo,decripcion;
    String t,d,st;
    int bust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        //setSupportActionBar(myToolbar);       new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnTomarfoto=findViewById(R.id.fabfoto);
        imageView=findViewById(R.id.imgfoto);
        Guardar=findViewById(R.id.btnGuardar1);
        Titulo=findViewById(R.id.txtTitulo1);
        decripcion=findViewById(R.id.txtDescripcion1);
        mos=findViewById(R.id.btnmostrar);
        Intent intent= getIntent();
        bust= intent.getIntExtra("pos",0);
        Toast.makeText(getApplicationContext(),"Tarea registrada"+ bust,Toast.LENGTH_SHORT).show();
        checar();
        mos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostar();
                checar();
            }
        });
        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bust>=1) {
                 actunoto();
                }else {
                    guardar();
                }

            }
        });
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
    public void mostar(){
    DaoNotas daoNotas=new DaoNotas(getApplication());
        ArrayList<Notas> lis=new ArrayList();
        lis= (ArrayList<Notas>) daoNotas.buscarTodos();
        Toast.makeText(getApplicationContext(),"hay:"+lis.size(),Toast.LENGTH_SHORT).show();
        for (int i=0;i<lis.size();i++){
            Toast.makeText(getApplicationContext(),"hay:"+lis.get(i).toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void checar(){
        if (bust>=1) {
            DaoNotas daoNotas = new DaoNotas(getApplication());
            Titulo.setText(daoNotas.buscarTitulo(bust).getTitulo().toString());
            decripcion.setText(daoNotas.buscarTitulo(bust).getDescripcion().toString());
        }
    }
    public void guardar(){
        t=Titulo.getText().toString();
        d=decripcion.getText().toString();
        Notas n=new Notas(t,d);
        DaoNotas dao=new DaoNotas(getApplicationContext());
        if (dao.insert(n)!=-1){

            Intent intent =new Intent(Agregar.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Campos Incorrectos",Toast.LENGTH_SHORT).show();
        }
    }
    public void actunoto(){
        DaoNotas daoNotas=new DaoNotas(getApplication());
        t=Titulo.getText().toString();
        d=decripcion.getText().toString();
        if ( daoNotas.actualizarnota(bust,t,d)){
             Intent intent =new Intent(Agregar.this,MainActivity.class);
             startActivity(intent);
             finish();
        }else{
            Toast.makeText(getApplicationContext(),"Campos Incorrectos",Toast.LENGTH_SHORT).show();
        }

    }

}