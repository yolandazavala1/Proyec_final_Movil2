package com.example.proyec_final_movil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoNotasMedia {
    Context context;
    BD bd;
    SQLiteDatabase ad;
    public DaoNotasMedia(Context ctx){
        this.context=ctx;
        bd=new BD(ctx);
        ad=bd.getWritableDatabase();
    }

    public long insert(NotasMedia notasMedia){
        ContentValues cv=new ContentValues();
        cv.put(BD.C_MEDIAN[1],notasMedia.getId_nota());
        cv.put(BD.C_MEDIAN[2],notasMedia.getTipo());
        cv.put(BD.C_MEDIAN[3],notasMedia.getUri());
        return ad.insert(BD.N_MEDIAN,null,cv);
    }
    public List<NotasMedia> buscarMedia(int tipo,int id_nota) {
        List<NotasMedia> notesArrayList = new ArrayList<NotasMedia>();
        String selectQuery = "SELECT * FROM median WHERE tipo = '"+tipo+"' AND id_nota = '"+id_nota+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                NotasMedia notasMedia = new NotasMedia();
                notasMedia.setId(c.getInt(c.getColumnIndex("id_nm")));
                notasMedia.setId_nota(c.getInt(c.getColumnIndex("id_nota")));
                notasMedia.setTipo(c.getInt(c.getColumnIndex("tipo")));
                notasMedia.setUri(c.getString(c.getColumnIndex("uri")));
                notesArrayList.add(notasMedia);
            } while (c.moveToNext());
        }
        return notesArrayList;
    }
    public List<NotasMedia> buscartodamedia(int id_nota) {
        List<NotasMedia> notesArrayList = new ArrayList<NotasMedia>();
        String selectQuery = "SELECT * FROM median Where id_nota='"+id_nota+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                NotasMedia notasMedia = new NotasMedia();
                notasMedia.setId(c.getInt(c.getColumnIndex("id_nm")));
                notasMedia.setId_nota(c.getInt(c.getColumnIndex("id_nota")));
                notasMedia.setTipo(c.getInt(c.getColumnIndex("tipo")));
                notasMedia.setUri(c.getString(c.getColumnIndex("uri")));
                notesArrayList.add(notasMedia);
            } while (c.moveToNext());
        }
        return notesArrayList;
    }
    public boolean eliminarnita(int id){
        String selectQuery = "DELETE FROM median WHERE id_nota = '"+id+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        return true;
    }

}
