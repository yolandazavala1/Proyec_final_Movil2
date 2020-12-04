package com.example.proyec_final_movil.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BD extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "agenda1";
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
    private static final String TABLE_TAREAS_MEDIA =
            "create table Tmedia ("+
            "id_tm integer primary key autoincrement, "+
            "id_tarea integer not null," +
            "dirUri varchar not null,"+
            "descripcion varchar(100));";

    private static final String TABLE_NOTAS_MEDIA =
            "create table Nmedia ("+
            "id_nm integer primary key autoincrement, "+
            "id_nota integer not null," +
            "dirUri varchar not null,"+
            "descripcion varchar(100));";



    @Override
    public void onCreate(SQLiteDatabase db) {
      db.execSQL(SCRIPT_TABLE_TAREAS);
      db.execSQL(TABLE_TAREAS_MEDIA);
      db.execSQL(SCRIPT_TABLE_NOTAS);
      db.execSQL(TABLE_NOTAS_MEDIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
