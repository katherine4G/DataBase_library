package com.example.model;

public class CAR {
    private int id;
    private String marca;
    private String modelo;
    private int anio;

    public CAR() {}

    public CAR(int id, String marca, String modelo, int año) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = año;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAño() {
        return anio;
    }

    public void setAño(int año) {
        this.anio = año;
    }
}