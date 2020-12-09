package com.example.proyec_final_movil.data;

import java.io.Serializable;

public class NotasMedia implements Serializable {
    private int id;
    private int id_nota;
    private int tipo;
    private String uri;

    public NotasMedia(int id_nota, int tipo, String uri) {
        this.id_nota = id_nota;
        this.tipo = tipo;
        this.uri = uri;
    }

    public NotasMedia() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_nota() {
        return id_nota;
    }

    public void setId_nota(int id_nota) {
        this.id_nota = id_nota;
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
