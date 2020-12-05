package com.example.proyec_final_movil.data;

import java.io.Serializable;

public class Tareas implements Serializable {
private int id;
private String titulo;
private String descricion;
private int hora;
private int minuto;
private int dia;
private int mes;
private  int ano;
private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tareas(String titulo, String descricion, int hora, int minuto, int dia, int mes, int ano, int status) {
        this.titulo = titulo;
        this.descricion = descricion;
        this.hora = hora;
        this.minuto = minuto;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.status = status;
    }

    public Tareas() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricion() {
        return descricion;
    }

    public void setDescricion(String descricion) {
        this.descricion = descricion;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return titulo;
    }
}
