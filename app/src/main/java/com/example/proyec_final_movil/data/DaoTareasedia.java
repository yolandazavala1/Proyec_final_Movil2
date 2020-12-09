package com.example.proyec_final_movil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoTareasedia {
    Context context;
    BD bd;
    SQLiteDatabase ad;
    public DaoTareasedia(Context ctx){
        this.context=ctx;
        bd=new BD(ctx);
        ad=bd.getWritableDatabase();
    }
    public long insert(TareasMedia tareasMedia){
        ContentValues cv=new ContentValues();
        cv.put(BD.C_MEDIAT[1],tareasMedia.getId_tarea());
        cv.put(BD.C_MEDIAT[2],tareasMedia.getTipo());
        cv.put(BD.C_MEDIAT[3],tareasMedia.getUri());
        return ad.insert(BD.N_MEDIAT,null,cv);
    }
    public List<TareasMedia> buscarMedia(int tipo, int id_tarea) {
        List<TareasMedia> notesArrayList = new ArrayList<TareasMedia>();
        String selectQuery = "SELECT * FROM mediat WHERE tipo = '"+tipo+"' AND id_tarea = '"+id_tarea+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                TareasMedia tareasMedia=new TareasMedia();
                tareasMedia.setId(c.getInt(c.getColumnIndex("id_tm")));
                tareasMedia.setId_tarea(c.getInt(c.getColumnIndex("id_tarea")));
                tareasMedia.setTipo(c.getInt(c.getColumnIndex("tipo")));
                tareasMedia.setUri(c.getString(c.getColumnIndex("uri")));
                notesArrayList.add(tareasMedia);
            } while (c.moveToNext());
        }
        return notesArrayList;
    }
    public List<TareasMedia> buscartodamedia(int id_tarea) {
        List<TareasMedia> notesArrayList = new ArrayList<TareasMedia>();
        String selectQuery = "SELECT * FROM mediat Where id_tarea='"+id_tarea+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                TareasMedia tareasMedia=new TareasMedia();
                tareasMedia.setId(c.getInt(c.getColumnIndex("id_tm")));
                tareasMedia.setId_tarea(c.getInt(c.getColumnIndex("id_tarea")));
                tareasMedia.setTipo(c.getInt(c.getColumnIndex("tipo")));
                tareasMedia.setUri(c.getString(c.getColumnIndex("uri")));
                notesArrayList.add(tareasMedia);
            } while (c.moveToNext());
        }
        return notesArrayList;
    }
}
