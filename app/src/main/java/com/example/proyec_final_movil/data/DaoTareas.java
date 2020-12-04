package com.example.proyec_final_movil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoTareas {
    Context context;
    BD bd;
    SQLiteDatabase ad;
    public DaoTareas(Context ctx){
        this.context=ctx;
        bd=new BD(ctx);
        ad=bd.getWritableDatabase();
    }
    public long insert(Tareas tareas){
        ContentValues cv=new ContentValues();
        //cv.put(BD.COLUMNS_NOTAS[0],notas.getId_nota());
        cv.put(BD.COLUMNS_TABLETAREAS[1],tareas.getTitulo());
        cv.put(BD.COLUMNS_TABLETAREAS[2],tareas.getDescricion());
        cv.put(BD.COLUMNS_TABLETAREAS[3],tareas.getHora());
        cv.put(BD.COLUMNS_TABLETAREAS[4],tareas.getMinuto());
        cv.put(BD.COLUMNS_TABLETAREAS[5],tareas.getDia());
        cv.put(BD.COLUMNS_TABLETAREAS[6],tareas.getMes());
        cv.put(BD.COLUMNS_TABLETAREAS[7],tareas.getAno());
        cv.put(BD.COLUMNS_TABLETAREAS[8],tareas.getStatus());
        return ad.insert(BD.DATABASE_NAME_TABLE,null,cv);
    }
    public List<Tareas> buscarTodos1() {
        List<Tareas> notesArrayList = new ArrayList<Tareas>();
        String selectQuery = "SELECT * FROM tareas";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {

                Tareas tareas=new Tareas();
                tareas.setTitulo(c.getString(c.getColumnIndex("titulo")));
                tareas.setDescricion(c.getString(c.getColumnIndex("descripcion")));
                tareas.setHora(c.getInt(c.getColumnIndex("hora")));
                tareas.setMinuto(c.getInt(c.getColumnIndex("minuto")));
                tareas.setDia(c.getInt(c.getColumnIndex("dia")));
                tareas.setMes(c.getInt(c.getColumnIndex("mes")));
                tareas.setAno(c.getInt(c.getColumnIndex("ano")));
                tareas.setStatus(c.getInt(c.getColumnIndex("estatus")));
                notesArrayList.add(tareas);
            } while (c.moveToNext());
        }
        return notesArrayList;
    }


}
