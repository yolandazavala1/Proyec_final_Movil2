package com.example.proyec_final_movil.data;

import java.io.Serializable;

public class Alerta implements Serializable {
    private int id_alerta;
    private int id_tarea;
    private String tituloAlerta;
    private String descripcionAlerta;
    private String fechaAlerta;
    private String horaAlerta;

    public Alerta(int id_alerta, int id_tarea, String tituloAlerta, String descripcionAlerta, String fechaAlerta, String horaAlerta) {
        this.id_alerta = id_alerta;
        this.id_tarea = id_tarea;
        this.tituloAlerta = tituloAlerta;
        this.descripcionAlerta = descripcionAlerta;
        this.fechaAlerta = fechaAlerta;
        this.horaAlerta = horaAlerta;
    }

    public Alerta() {

    }

    public int getId_alerta() {
        return id_alerta;
    }

    public void setId_alerta(int id_alerta) {
        this.id_alerta = id_alerta;
    }

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public String getTituloAlerta() {
        return tituloAlerta;
    }

    public void setTituloAlerta(String tituloAlerta) {
        this.tituloAlerta = tituloAlerta;
    }

    public String getDescripcionAlerta() {
        return descripcionAlerta;
    }

    public void setDescripcionAlerta(String descripcionAlerta) {
        this.descripcionAlerta = descripcionAlerta;
    }

    public String getFechaAlerta() {
        return fechaAlerta;
    }

    public void setFechaAlerta(String fechaAlerta) {
        this.fechaAlerta = fechaAlerta;
    }

    public String getHoraAlerta() {
        return horaAlerta;
    }

    public void setHoraAlerta(String horaAlerta) {
        this.horaAlerta = horaAlerta;
    }

    @Override
    public String toString() {

        return "\nID_Alerta:" + this.id_alerta + "\nID_Tarea:" + this.id_tarea + "\nTitulo:" + this.tituloAlerta + "\nDescripcion:" + this.descripcionAlerta + "\nFecha:" + this.fechaAlerta + "\nHora:" + this.horaAlerta + "\n";

    }
}
