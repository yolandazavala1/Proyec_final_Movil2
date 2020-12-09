package com.example.proyec_final_movil.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "agenda3";
    private static final int DATABASE_VERSION = 1;
    Context context;

    public BD(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    //Creacion de tabla Notas
    public static final  String[] COLUMNS_NOTAS={"_id_nota","titulo","descripcion"};
    public static final String DATABASE_TABLE_NOTAS ="notas" ;
    private static final String SCRIPT_TABLE_NOTAS=
            "create table notas("+
                    "id_nota integer primary key autoincrement,"+
                    "titulo text not null,"+
                    "descripcion text);";
    //Creacion de tabla tareas
    public static final String[] COLUMNS_TABLETAREAS ={"id_tarea","titulo","descripcion","hora","minuto","dia","mes","ano","estatus"};
    public static final String DATABASE_NAME_TABLE = "tareas";
    private static final String SCRIPT_TABLE_TAREAS=
            "create table tareas("+
            "id_tarea integer primary key autoincrement,"+
            "titulo text not null,"+
            "descripcion text,"+
            "hora integer not null,"+
            "minuto integer not null,"+
            "dia integer not null,"+
            "mes integer not null,"+
            "ano ineteger not null,"+
            "estatus integer);";
    public  static final String[]C_MEDIAT={"id_tm","id_tarea","tipo","uri"};
    public static final String N_MEDIAT="mediat";
    private static final String S_MEDIAT =
            "create table mediat("+
            "id_tm integer primary key autoincrement, "+
            "id_tarea integer not null," + "tipo integer not null," +
            "uri varchar not null);";
    public  static final String[]C_MEDIAN={"id_nm","id_nota","tipo","uri"};
    public static final String N_MEDIAN="median";
    private static final String S_MEDIAN=
            "create table median("+
            "id_nm integer primary key autoincrement,"+
            "id_nota integer not null,"+
            "tipo integer not null,"+
            "uri varchar not null);";
    public static final String[]COLUMNS_ALERTAS = {"_id","id_Tarea","titulo","descripcion","fechaAlerta","horaAlerta"};
    public static final String TABLE_ALERTAS_NAME="alertas";
    private  final String TABLE_ALERTAS = "create table alertas ("+
           "_id integer primary key autoincrement, "+
           "id_tarea integer not null," +
            "titulo varchar(100) not null,"+
            "descripcion text not null,"+
            "fechaAlerta varchar(12),"+
            "horaAlerta varchar(12));";



    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(SCRIPT_TABLE_TAREAS);
      db.execSQL(S_MEDIAT);
      db.execSQL(SCRIPT_TABLE_NOTAS);
      //db.execSQL(TABLE_NOTAS_MEDIA);
      db.execSQL(S_MEDIAN);
      db.execSQL(TABLE_ALERTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
