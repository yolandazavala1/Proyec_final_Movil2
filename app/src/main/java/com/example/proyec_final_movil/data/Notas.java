package com.example.proyec_final_movil.data;

import java.io.Serializable;

public class Notas implements Serializable {
private int id;
private String titulo;
private String descripcion;

    public Notas(String titulo, String descripcion) {

        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Notas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString(){
        return titulo;
    }

}
