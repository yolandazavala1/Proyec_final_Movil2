package com.example.proyec_final_movil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proyec_final_movil.data.AdaptadorTareas;
import com.example.proyec_final_movil.data.DaoTareas;
import com.example.proyec_final_movil.data.Tareas;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class AgregarTareas extends AppCompatActivity implements View.OnClickListener {
   TextView txtHora,txtFecha;
   Button btnHora,btnFecha,btnGuardar;
   int h,m,d,me,a,c,h1,m1,d1,me1,a1;
   EditText txttitulo,txtdescricion;
   String titulo,descrjicion;
   CheckBox c1;
   int chec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        checar();
        btnGuardar=(Button)findViewById(R.id.btnGuadar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (chec>=1){
                   actualizar();
               }else{
                 guardar();
               }

            }
        });
    }
    public void guardar(){
        if (c1.isChecked()) {
            c = 1;
        } else {
            c = 0;
        }
        titulo=txttitulo.getText().toString();
        descrjicion=txtdescricion.getText().toString();

        Tareas ta = new Tareas(titulo, descrjicion, h1, m1, d1, me1, a1, c);
        DaoTareas daoTareas = new DaoTareas(getApplicationContext());
        if (daoTareas.insert(ta) != -1) {
            Toast.makeText(getApplicationContext(),"Tarea registrada",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(AgregarTareas.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Campos Incorrectos",Toast.LENGTH_SHORT).show();
        }
    }
    public void checar(){
        if (chec>=1) {
            DaoTareas daoTareas=new DaoTareas(getApplication());
            txttitulo.setText(daoTareas.buscarid(chec).getTitulo());
            txtdescricion.setText(daoTareas.buscarid(chec).getDescricion());
            txtHora.setText(daoTareas.buscarid(chec).getHora()+":"+daoTareas.buscarid(chec).getMinuto());
            txtFecha.setText(daoTareas.buscarid(chec).getDia()+"/"+daoTareas.buscarid(chec).getMes()+"/"+daoTareas.buscarid(chec).getAno());
            if (daoTareas.buscarid(chec).getStatus()==1){
                c1.setChecked(true);
            }else{
                c1.setChecked(false);
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

    @Override
    public void onClick(View v) {
        if (v==btnFecha){
            final Calendar c=Calendar.getInstance();
            d=c.get(Calendar.DAY_OF_MONTH);
            me=c.get(Calendar.MONTH);
            a=c.get(Calendar.YEAR);
            DatePickerDialog da=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtFecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    h1=dayOfMonth;
                    m1=month+1;
                    a1=year;
                }
            },d,me,a);
            da.show();
        }
        if (v==btnHora){
           final Calendar c=Calendar.getInstance();
           h=c.get(Calendar.HOUR_OF_DAY);
           m=c.get(Calendar.MINUTE);
           TimePickerDialog ti=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
               @Override
               public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                 txtHora.setText(hourOfDay+":"+minute);
                 h1=hourOfDay;
                 m1=minute;
               }
           },h,m,false);
           ti.show();
        }
    }
}