package com.example.proyec_final_movil.data;

import java.io.Serializable;

public class TareasMedia implements Serializable {
    private int id;
    private int id_tarea;
    private int tipo;
    private String uri;

    public TareasMedia(int id_tarea, int tipo, String uri) {
        this.id_tarea = id_tarea;
        this.tipo = tipo;
        this.uri = uri;
    }

    public TareasMedia() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_tarea() {
        return id_tarea;
    }

    public void setId_tarea(int id_tarea) {
        this.id_tarea = id_tarea;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
