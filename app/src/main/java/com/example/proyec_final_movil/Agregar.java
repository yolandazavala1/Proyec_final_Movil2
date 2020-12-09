package com.example.proyec_final_movil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyec_final_movil.data.AdaptadorFoto;
import com.example.proyec_final_movil.data.Adaptadoraudio;
import com.example.proyec_final_movil.data.Adaptadorlista;
import com.example.proyec_final_movil.data.Adaptadorvideo;
import com.example.proyec_final_movil.data.DaoNotas;
import com.example.proyec_final_movil.data.DaoNotasMedia;
import com.example.proyec_final_movil.data.DaoTareas;
import com.example.proyec_final_movil.data.Notas;
import com.example.proyec_final_movil.data.NotasMedia;
import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Agregar extends AppCompatActivity {
    FloatingActionButton btnTomarfoto,btntomarvideo,btnGrabaraudio;
    VideoView videoView;
    Button Guardar,mos;
    EditText Titulo,decripcion;
    String t,d,st;
    int bust;
    RecyclerView listafoto,listavideos,listaaudios;
    MediaRecorder grabacion=null;
    String uriaudio=null;
    private boolean permissionToRecordAccepted = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btnTomarfoto=findViewById(R.id.fabfoto);
        btntomarvideo=findViewById(R.id.fabvideo);
        btnGrabaraudio=findViewById(R.id.fabaudio);
        listafoto=findViewById(R.id.refotos);
        listavideos=findViewById(R.id.revideos);
        listaaudios=findViewById(R.id.reaudio);
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
                mostar();
                //checar();
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
        btntomarvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            abrirvideo();
            }
        });
        btnGrabaraudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             grabar(v);
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();

    }
    ArrayList<NotasMedia>lista3=new ArrayList<>();
    ArrayList<NotasMedia>lista1=new ArrayList<NotasMedia>();
    ArrayList<NotasMedia>lista2=new ArrayList<NotasMedia>();

    static final int REQUEST_VIDEO=2;
    private void abrirvideo(){
        Intent takevideo=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takevideo.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try {
                photoFile = createVideoFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri VideoURI = FileProvider.getUriForFile(this,
                        "com.example.proyec_final_movil.FileProvider",
                        photoFile);
                takevideo.putExtra(MediaStore.EXTRA_OUTPUT, VideoURI);
                startActivityForResult(takevideo,REQUEST_VIDEO);
            }
            NotasMedia notasMedia=new  NotasMedia(0,2,videopath);
            lista2.add(notasMedia);
        }
    }
    static final int REQUEST_TAKE_PHOTO = 1;
    private void abrircamara(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.proyec_final_movil.FileProvider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
            NotasMedia notasMedia=new  NotasMedia(0,1,currentPhotoPath);
            lista1.add(notasMedia);

        }
    }

    protected void onActivityResult(int recues,int resulcode,Intent data) {
        super.onActivityResult(recues, resulcode, data);
        if (recues == 1 && resulcode == RESULT_OK) {
            listafoto.setLayoutManager(new GridLayoutManager(this,2));
            AdaptadorFoto ada=new AdaptadorFoto(lista1);
            listafoto.setAdapter(ada);

        }else if (recues==REQUEST_VIDEO&& resulcode==RESULT_OK){
            listavideos.setLayoutManager(new GridLayoutManager(this,2));
            Adaptadorvideo ada1=new Adaptadorvideo(lista2);
            listavideos.setAdapter(ada1);
        }


    }
    String currentPhotoPath,videopath,audiopath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private File createVideoFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String videoFileName = "MP4_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        File vid = File.createTempFile(
                videoFileName,  /* prefix */
                ".mp4",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        videopath = vid.getAbsolutePath();
        return vid;
    }
    private String createaudioFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String audioFileName = "3GP_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File aud = File.createTempFile(
                audioFileName,  /* prefix */
                ".egp",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        audiopath = aud.getAbsolutePath();
        return audiopath;
    }


    public void mostar(){
        DaoNotas notas= new DaoNotas(getApplication());
        ultimo=notas.buscarutimanota().getId();
        Log.d("", "ultimo: "+ultimo);
        Log.d("", "fotos: "+lista1.size());
        Log.d("", "direcion video: "+lista2.get(0).toString());

    }
    int total;
    public void checar(){
        if (bust>=1) {
            DaoNotas daoNotas = new DaoNotas(getApplication());
            Titulo.setText(daoNotas.buscarTitulo(bust).getTitulo().toString());
            decripcion.setText(daoNotas.buscarTitulo(bust).getDescripcion().toString());
            DaoNotasMedia daoNotasMedia=new DaoNotasMedia(getApplication());
            total=daoNotasMedia.buscartodamedia(bust).size();
            if (total>0){
                lista1.addAll(daoNotasMedia.buscarMedia(1,bust));
                listafoto.setLayoutManager(new GridLayoutManager(this,2));
                AdaptadorFoto ada=new AdaptadorFoto(lista1);
                listafoto.setAdapter(ada);
                lista2.addAll(daoNotasMedia.buscarMedia(2,bust));
                Log.d("", "direcion video recuperado: "+lista2.get(0).getUri()+"-------------------------------------------------");
                listavideos.setLayoutManager(new GridLayoutManager(this,2));
                Adaptadorvideo ada1=new Adaptadorvideo(lista2);
                listavideos.setAdapter(ada1);
                lista3.addAll(daoNotasMedia.buscarMedia(3,bust));
                listaaudios.setLayoutManager(new LinearLayoutManager(getApplication()));
                Adaptadoraudio ada3=new Adaptadoraudio(lista3);
                listaaudios.setAdapter(ada3);

            }
        }
    }

    public void guardar(){
        t=Titulo.getText().toString();
        d=decripcion.getText().toString();
        Notas n=new Notas(t,d);
        DaoNotas dao=new DaoNotas(getApplicationContext());
        if (dao.insert(n)!=-1){
            Intent intent =new Intent(Agregar.this,MainActivity.class);
            if(lista1.size()>0|lista2.size()>0|lista3.size()>0){
              guardarmultimedia();
                startActivity(intent);
            }else {
                startActivity(intent);
            }
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Campos Incorrectos",Toast.LENGTH_SHORT).show();
        }

    }
    int ultimo;
    public void guardarmultimedia(){
        DaoNotas notas= new DaoNotas(getApplication());
        DaoNotasMedia daoNotasMedia=new DaoNotasMedia(getApplication());
        ultimo=notas.buscarutimanota().getId();
         for (int i=0;i<lista1.size();i++){
             NotasMedia notasMedia=new NotasMedia();
             notasMedia.setTipo(1);
             notasMedia.setId_nota(ultimo);
             notasMedia.setUri(lista1.get(i).getUri());
             if(daoNotasMedia.insert(notasMedia)!=-1) {
                 Log.d("", "fotos: " + lista1.size());
             }
         }
        for (int r=0;r<lista2.size();r++){
            NotasMedia notasMedia=new NotasMedia();
            notasMedia.setTipo(2);
            notasMedia.setId_nota(ultimo);
            notasMedia.setUri(lista2.get(r).getUri());
            Log.d("", "videos guardados antes: " + lista2.get(r).getUri()+"-----------------------------------------------------");

            if(daoNotasMedia.insert(notasMedia)!=-1) {
                Log.d("", "videos guardados: " + lista2.get(0).getUri()+"-----------------------------------------------------");
            }
        }
        for (int i=0;i<lista3.size();i++){
            NotasMedia notasMedia=new NotasMedia();
            notasMedia.setTipo(3);
            notasMedia.setId_nota(ultimo);
            notasMedia.setUri(lista3.get(i).getUri());
            if(daoNotasMedia.insert(notasMedia)!=-1) {
                Log.d("", "audios: " + lista3.size());
            }
        }

    }
    public void actualizarmultimedia(){
        DaoNotas notas= new DaoNotas(getApplication());
        DaoNotasMedia daoNotasMedia=new DaoNotasMedia(getApplication());
       daoNotasMedia.eliminarnita(bust);
        //ultimo=notas.buscarutimanota().getId();
        for (int i=0;i<lista1.size();i++){
            NotasMedia notasMedia=new NotasMedia();
            notasMedia.setTipo(1);
            notasMedia.setId_nota(bust);
            notasMedia.setUri(lista1.get(i).getUri());
            if(daoNotasMedia.insert(notasMedia)!=-1) {
                Log.d("", "fotos: " + lista1.size());
            }
        }
        for (int r=0;r<lista2.size();r++){
            NotasMedia notasMedia=new NotasMedia();
            notasMedia.setTipo(2);
            notasMedia.setId_nota(bust);
            notasMedia.setUri(lista2.get(r).getUri());
            Log.d("", "videos guardados antes: " + lista2.get(r).getUri()+"-----------------------------------------------------");

            if(daoNotasMedia.insert(notasMedia)!=-1) {
                Log.d("", "videos guardados: " + lista2.get(0).getUri()+"-----------------------------------------------------");
            }
        }
        for (int i=0;i<lista3.size();i++){
            NotasMedia notasMedia=new NotasMedia();
            notasMedia.setTipo(3);
            notasMedia.setId_nota(bust);
            notasMedia.setUri(lista3.get(i).getUri());
            if(daoNotasMedia.insert(notasMedia)!=-1) {
                Log.d("", "audios: " + lista3.size());
            }
        }
    }
    public void actunoto(){
        DaoNotas daoNotas=new DaoNotas(getApplication());
        t=Titulo.getText().toString();
        d=decripcion.getText().toString();
        if ( daoNotas.actualizarnota(bust,t,d)){
             Intent intent =new Intent(Agregar.this,MainActivity.class);
            if(lista1.size()>0|lista2.size()>0|lista3.size()>0){
               // actualizarmultimedia();
                startActivity(intent);
            }else {
                startActivity(intent);
            }

             finish();

        }else{
            Toast.makeText(getApplicationContext(),"Campos Incorrectos",Toast.LENGTH_SHORT).show();
        }
    }
    static final int REQUEST_TAKE_AUDIO = 3;
    public void grabar(View V){
        if(grabacion==null){
            grabacion=new MediaRecorder();
            uriaudio = getExternalCacheDir().getAbsolutePath();
            uriaudio += "/audiorecordtest.3gp";
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            try {
                uriaudio=createaudioFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
                grabacion.setOutputFile(uriaudio);
            try {
                grabacion.prepare();

            } catch (IOException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"no grabando",Toast.LENGTH_SHORT).show();
            }
            btnGrabaraudio.setImageResource(R.drawable.ic_mic_re);
            Toast.makeText(getApplicationContext(),"Grabando",Toast.LENGTH_SHORT).show();
            grabacion.start();

        }else if (grabacion!=null){
            btnGrabaraudio.setImageResource(R.drawable.ic_mic);
            grabacion.stop();
            grabacion.release();
            grabacion=null;
            NotasMedia notasMedia= null;
                notasMedia = new NotasMedia(0,3,uriaudio);
            lista3.add(notasMedia);
            listaaudios.setLayoutManager(new LinearLayoutManager(getApplication()));
            Adaptadoraudio ada3=new Adaptadoraudio(lista3);
            listaaudios.setAdapter(ada3);

        }

    }

}