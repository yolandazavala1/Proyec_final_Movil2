package com.example.proyec_final_movil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoNotas {
    Context context;
    BD bd;
    SQLiteDatabase ad;
    public DaoNotas(Context ctx){
        this.context=ctx;
        bd=new BD(ctx);
        ad=bd.getWritableDatabase();
    }

    public long insert(Notas notas){
        ContentValues cv=new ContentValues();
        //cv.put(BD.COLUMNS_NOTAS[0],notas.getId_nota());
        cv.put(BD.COLUMNS_NOTAS[1],notas.getTitulo());
        cv.put(BD.COLUMNS_NOTAS[2],notas.getDescripcion());
        return ad.insert(BD.DATABASE_TABLE_NOTAS,null,cv);
    }
    public List<Notas> buscarTodos() {
        List<Notas> notesArrayList = new ArrayList<Notas>();
        String selectQuery = "SELECT * FROM notas";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Notas notas = new Notas();
                notas.setId(c.getInt(c.getColumnIndex("id_nota")));
                notas.setTitulo(c.getString(c.getColumnIndex("titulo")));
                notas.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
                notesArrayList.add(notas);
            } while (c.moveToNext());
        }
        return notesArrayList;
    }
    public Notas buscarTitulo(int id) {
      // Cursor c=ad.query(BD.DATABASE_TABLE_NOTAS,BD.COLUMNS_NOTAS,"id_nota=?",new String[]{id+""},null,null,null);
        Notas notas=new Notas();
        String selectQuery = "SELECT * FROM notas WHERE id_nota = '"+id+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()){
            Notas notas1=new Notas();
            notas1.setTitulo(c.getString(1));
            notas1.setDescripcion(c.getString(2));
            notas=notas1;
        }
        return notas;
    }
    public Boolean actualizarnota(int id,String t,String d){
        String selectQuery = "UPDATE notas SET titulo='"+t+"',descripcion='"+d+"' WHERE id_nota = '"+id+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        return true;
    }
    public boolean eliminarnita(int id){
        String selectQuery = "DELETE FROM notas WHERE id_nota = '"+id+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        return true;
    }
}
