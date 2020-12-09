package com.example.proyec_final_movil.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DaoAlertas {
    Context context;
    BD bd;
    SQLiteDatabase ad;
    public DaoAlertas(Context ctx){
        this.context=ctx;
        bd=new BD(ctx);
        ad=bd.getWritableDatabase();
    }
    public long insert(Alerta c){
        ContentValues cv = new ContentValues();
        cv.put(BD.COLUMNS_ALERTAS[1],c.getId_tarea());
        cv.put(BD.COLUMNS_ALERTAS[2],c.getTituloAlerta());
        cv.put(BD.COLUMNS_ALERTAS[3],c.getDescripcionAlerta());
        cv.put(BD.COLUMNS_ALERTAS[4],c.getFechaAlerta());
        cv.put(BD.COLUMNS_ALERTAS[5],c.getHoraAlerta());
        return ad.insert(BD.TABLE_ALERTAS_NAME,null,cv);
    }
    public List<Alerta> buscarTodos() {

        List<Alerta> notesArrayList = new ArrayList<Alerta>();
        String selectQuery = "SELECT * FROM alertas";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Alerta notas = new Alerta();
                notas.setId_alerta(c.getInt(c.getColumnIndex("_id")));
                notas.setId_tarea(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setTituloAlerta(c.getString(c.getColumnIndex("titulo")));
                notas.setDescripcionAlerta(c.getString(c.getColumnIndex("descripcion")));
                notas.setFechaAlerta(c.getString(c.getColumnIndex("fechaAlerta")));
                notas.setHoraAlerta(c.getString(c.getColumnIndex("horaAlerta")));
                notesArrayList.add(notas);
            } while (c.moveToNext());

        }

        return notesArrayList;
    }
    public List<Alerta> buscarTodosDeFecha(String iden) {
        List<Alerta> notesArrayList = new ArrayList<Alerta>();
        String selectQuery = "SELECT * FROM alertas WHERE fechaAlerta = '"+iden+"'";
        Log.d("", selectQuery);
        SQLiteDatabase db = this.ad;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {

                Alerta notas = new Alerta();
                notas.setId_alerta(c.getInt(c.getColumnIndex("_id")));
                notas.setId_tarea(c.getInt(c.getColumnIndex("id_Tarea")));
                notas.setTituloAlerta(c.getString(c.getColumnIndex("titulo")));
                notas.setDescripcionAlerta(c.getString(c.getColumnIndex("descripcion")));
                notas.setFechaAlerta(c.getString(c.getColumnIndex("fechaAlerta")));
                notas.setHoraAlerta(c.getString(c.getColumnIndex("horaAlerta")));

                notesArrayList.add(notas);

            } while (c.moveToNext());
        }

        return notesArrayList;

    }
}
