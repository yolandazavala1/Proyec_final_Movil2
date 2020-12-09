package com.example.proyec_final_movil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.WorkManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proyec_final_movil.Alarma.WorkManagernoti;
import com.example.proyec_final_movil.data.AdaptadorFoto;
import com.example.proyec_final_movil.data.AdaptadorTareas;
import com.example.proyec_final_movil.data.AdaptadorVideo1;
import com.example.proyec_final_movil.data.Adaptadoraudio1;
import com.example.proyec_final_movil.data.Adaptadorfoto1;
import com.example.proyec_final_movil.data.Adaptadorvideo;
import com.example.proyec_final_movil.data.DaoTareas;
import com.example.proyec_final_movil.data.DaoTareasedia;
import com.example.proyec_final_movil.data.NotasMedia;
import com.example.proyec_final_movil.data.Tareas;
import com.example.proyec_final_movil.data.TareasMedia;
import com.github.clans.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.UUID;

public class AgregarTareas extends AppCompatActivity implements View.OnClickListener {
    private final static String CHANEL_ID ="NOTIFICACION";
    private static final int NOTIFICATION_ID = 0;
    FloatingActionButton btnTomarfoto1,btntomarvideo1,btnGrabaraudio1,menu;
   TextView txtHora,txtFecha;
   Button btnHora,btnFecha,btnGuardar;
   int h,m,d,me,a,c,h1,m1,d1,me1,a1;
   EditText txttitulo,txtdescricion;
   String titulo,descrjicion;
   CheckBox c1;
   int chec;
    RecyclerView listafoto1,listavideos1,listaaudios1;
    MediaRecorder grabacion=null;
    String uriaudio=null;
    private boolean permissionToRecordAccepted = false;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listafoto1=findViewById(R.id.refotos1);
        listavideos1=findViewById(R.id.revideos1);
        listaaudios1=findViewById(R.id.reaudios1);
        setContentView(R.layout.activity_agregar_tareas);
        txtHora=(TextView)findViewById(R.id.txthora);
        btnHora=(Button)findViewById(R.id.btnhora);
        txtFecha=(TextView)findViewById(R.id.txtfecha);
        btnFecha=(Button)findViewById(R.id.btnfecha);
        btnHora.setOnClickListener(this);
        btnFecha.setOnClickListener(this);
        txttitulo=(EditText)findViewById(R.id.txttitulo);
        txtdescricion=(EditText)findViewById(R.id.txtdescripcion);
        c1=(CheckBox)findViewById(R.id.checkBox);
        Intent intent=getIntent();
        chec=intent.getIntExtra("pos",0);
        Toast.makeText(getApplicationContext(),"chec"+ chec,Toast.LENGTH_SHORT).show();
        btnTomarfoto1=findViewById(R.id.fabfotos);
        btntomarvideo1=findViewById(R.id.fabvideos);
        btnGrabaraudio1=findViewById(R.id.fabaudios);
        listafoto1=findViewById(R.id.refotos1);
        listavideos1=findViewById(R.id.revideos1);
        listaaudios1=findViewById(R.id.reaudios1);
        btnGuardar=(Button)findViewById(R.id.btnGuadar);
        checar();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (chec>=1){
                   actualizar();
               }else{
                 guardar();

               }
               String tag = generateKey();
               Long AlertTime = calendar.getTimeInMillis() - System.currentTimeMillis();
               int random = (int)(Math.random()*50+1);
               Data data = GuardarDate("¡Hey!", "Recuerda terminar tu tarea: "+txttitulo.getText(),random);
               WorkManagernoti.GuardarNoti(AlertTime,data,tag);
            }
        });
        btnTomarfoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            abrircamara();
            }
        });
       btntomarvideo1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               abrirvideo();
           }
       });
       btnGrabaraudio1.setOnClickListener(new View.OnClickListener() {
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
    ArrayList<TareasMedia> lista3=new ArrayList<>();
    ArrayList<TareasMedia>lista1=new ArrayList<>();
    ArrayList<TareasMedia>lista2=new ArrayList<>();
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
            TareasMedia tareasMedia=new TareasMedia(0,2,videopath);
            lista2.add(tareasMedia);
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
            TareasMedia tareasMedia=new TareasMedia(0,1,currentPhotoPath);
            lista1.add(tareasMedia);

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
            btnGrabaraudio1.setImageResource(R.drawable.ic_mic_re);
            Toast.makeText(getApplicationContext(),"Grabando",Toast.LENGTH_SHORT).show();
            grabacion.start();

        }else if (grabacion!=null){
            btnGrabaraudio1.setImageResource(R.drawable.ic_mic);
            grabacion.stop();
            grabacion.release();
            grabacion=null;
            TareasMedia tareasMedia = new TareasMedia(0,3,uriaudio);
            lista3.add(tareasMedia);
            listaaudios1.setLayoutManager(new LinearLayoutManager(getApplication()));
            Adaptadoraudio1 ada3=new Adaptadoraudio1(lista3);
            listaaudios1.setAdapter(ada3);

        }

    }
    protected void onActivityResult(int recues,int resulcode,Intent data) {
        super.onActivityResult(recues, resulcode, data);
        if (recues == 1 && resulcode == RESULT_OK) {
            listafoto1.setLayoutManager(new GridLayoutManager(this,2));
            Adaptadorfoto1 ada=new Adaptadorfoto1(lista1);
            listafoto1.setAdapter(ada);

        }else if (recues==REQUEST_VIDEO&& resulcode==RESULT_OK){
            listavideos1.setLayoutManager(new GridLayoutManager(this,2));
            AdaptadorVideo1 ada1=new AdaptadorVideo1(lista2);
            listavideos1.setAdapter(ada1);
        }

    }
    public void guardar(){
        if (c1.isChecked()) {
            c = 1;
        } else {
            c = 0;
        }
        titulo=txttitulo.getText().toString();
        descrjicion=txtdescricion.getText().toString();

        Tareas ta = new Tareas(titulo, descrjicion, hora, min, dia, mes, anio, c);
        DaoTareas daoTareas = new DaoTareas(getApplicationContext());
        if (daoTareas.insert(ta) != -1) {
            Toast.makeText(getApplicationContext(),"Tarea registrada",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(AgregarTareas.this,MainActivity.class);
            if(lista1.size()>0|lista2.size()>0|lista3.size()>0){
                guardarmultimedia();
                startActivity(intent);
            }else {
                startActivity(intent);
            }
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Campos Incorrectos",Toast.LENGTH_SHORT).show();
        }
    }
    int total;
    public void checar(){
        if (chec>=1) {
            DaoTareas daoTareas=new DaoTareas(getApplication());
            txttitulo.setText(daoTareas.buscarid(chec).getTitulo());
            txtdescricion.setText(daoTareas.buscarid(chec).getDescricion());
            h1=daoTareas.buscarid(chec).getHora();
            m1=daoTareas.buscarid(chec).getMinuto();
            d1=daoTareas.buscarid(chec).getDia();
            me1=daoTareas.buscarid(chec).getMes();
            a1=daoTareas.buscarid(chec).getAno();
            txtHora.setText(daoTareas.buscarid(chec).getHora()+":"+daoTareas.buscarid(chec).getMinuto());
            txtFecha.setText(daoTareas.buscarid(chec).getDia()+"/"+daoTareas.buscarid(chec).getMes()+"/"+daoTareas.buscarid(chec).getAno());
            if (daoTareas.buscarid(chec).getStatus()==1){
                c1.setChecked(true);
            }else{
                c1.setChecked(false);
            }
            DaoTareasedia daoTareasedia=new DaoTareasedia(getApplication());
            total=daoTareasedia.buscartodamedia(chec).size();
            if (total>0){
                //fotos
                lista1.addAll(daoTareasedia.buscarMedia(1,chec));
                listafoto1.setLayoutManager(new GridLayoutManager(this,2));
                Adaptadorfoto1 ada=new Adaptadorfoto1(lista1);
                listafoto1.setAdapter(ada);
                //video
                lista2.addAll(daoTareasedia.buscarMedia(2,chec));
                Log.d("", "direcion video recuperado: "+lista2.get(0).getUri()+"-------------------------------------------------");
                listavideos1.setLayoutManager(new GridLayoutManager(this,2));
                AdaptadorVideo1 ada1=new AdaptadorVideo1(lista2);
                listavideos1.setAdapter(ada1);
                //audio
                lista3.addAll(daoTareasedia.buscarMedia(3,chec));
                listaaudios1.setLayoutManager(new LinearLayoutManager(getApplication()));
                Adaptadoraudio1 ada3=new Adaptadoraudio1(lista3);
                listaaudios1.setAdapter(ada3);
            }
        }
    }
    int ultimo;
    public void guardarmultimedia(){
        DaoTareas tareas= new DaoTareas(getApplication());
        DaoTareasedia daoTareasedia=new DaoTareasedia(getApplication());
        ultimo=tareas.buscarutimatarea().getId();
        for (int i=0;i<lista1.size();i++){
            TareasMedia tareasMedia=new TareasMedia();
            tareasMedia.setTipo(1);
            tareasMedia.setId_tarea(ultimo);
            tareasMedia.setUri(lista1.get(i).getUri());
            if(daoTareasedia.insert(tareasMedia)!=-1) {
                Log.d("", "fotos: " + lista1.size());
            }
        }
        for (int r=0;r<lista2.size();r++){
            TareasMedia notasMedia=new TareasMedia();
            notasMedia.setTipo(2);
            notasMedia.setId_tarea(ultimo);
            notasMedia.setUri(lista2.get(r).getUri());
            Log.d("", "videos guardados antes: " + lista2.get(r).getUri()+"-----------------------------------------------------");

            if(daoTareasedia.insert(notasMedia)!=-1) {
                Log.d("", "videos guardados: " + lista2.get(0).getUri()+"-----------------------------------------------------");
            }
        }
        for (int i=0;i<lista3.size();i++){
            TareasMedia notasMedia=new TareasMedia();
            notasMedia.setTipo(3);
            notasMedia.setId_tarea(ultimo);
            notasMedia.setUri(lista3.get(i).getUri());
            if(daoTareasedia.insert(notasMedia)!=-1) {
                Log.d("", "audios: " + lista3.size());
            }
        }

    }
    public void actualizar(){
        DaoTareas daoTareas = new DaoTareas(getApplicationContext());
        if (c1.isChecked()) {
            c = 1;
        } else {
            c = 0;
        }
        titulo=txttitulo.getText().toString();
        descrjicion=txtdescricion.getText().toString();
        if (daoTareas.actualizartarea(chec,titulo,descrjicion,h1,m1,d1,me1,a1,c)){
            Intent intent =new Intent(AgregarTareas.this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(getApplicationContext(),"Campos Incorrectos",Toast.LENGTH_SHORT).show();
        }

    }
    Calendar actual=Calendar.getInstance();
    Calendar calendar=Calendar.getInstance();
    private int min,hora, dia, mes, anio;
    @Override
    public void onClick(View v) { //Este metodo reacciona al evento click del boton fecha y hora
        if (v==btnFecha){

            dia=actual.get(Calendar.DAY_OF_MONTH);
            mes=actual.get(Calendar.MONTH);
            anio=actual.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog=new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int y, int m, int d) {
                    calendar.set(calendar.DAY_OF_MONTH,d);
                    calendar.set(calendar.MONTH,m);
                    calendar.set(calendar.YEAR,y);
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    String strDate = format.format(calendar.getTime());

                    txtFecha.setText(strDate);
                }
            },anio,mes,dia);
            datePickerDialog.show();
        }
        if (v==btnHora){
            hora=actual.get(Calendar.HOUR_OF_DAY);
            m=actual.get(Calendar.MINUTE);
            TimePickerDialog ti=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int h, int m) {
                    calendar.set(Calendar.HOUR_OF_DAY,h);
                    calendar.set(Calendar.MINUTE,m);
                    txtHora.setText(String.format("%02d:%02d",h,m));
                }
            },hora,min,false);
            ti.show();
        }

    }
    String  currentPhotoPath,videopath,audiopath;
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
    public void guar(){
      String tag=generate();
    }
    private String generate(){
        return UUID.randomUUID().toString();
    }
    @SuppressLint("RestrictedApi")
    private Data Guardardta(String ti, String des, int id_noti){
     return new Data.Builder()
             .put("titulo",ti).put("descrpcion",des).put("id_noti",id_noti).build();
    }
    public void createnotificacio(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext(),CHANEL_ID);
        builder.setSmallIcon(R.drawable.ic_round_event_note_24);
        builder.setContentTitle("Notificacion");
        builder.setContentText("Recordatorio Tarea");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setVibrate(new long[]{1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat no=NotificationManagerCompat.from(getApplicationContext());
        no.notify(NOTIFICATION_ID,builder.build());

    }
    private void Eliminarnoti(String tag){
        WorkManager.getInstance(this).cancelAllWorkByTag(tag);
        Toast.makeText(AgregarTareas.this, "Alarma eliminada", Toast.LENGTH_SHORT).show();
    }
    private String generateKey(){
        return UUID.randomUUID().toString();
    }
    private Data GuardarDate(String titulo, String detalle, int id_noti){
        return new Data.Builder()
                .putString("titulo",titulo)
                .putString("detalle",detalle)
                .putInt("id_noti",id_noti).build();
    }
}