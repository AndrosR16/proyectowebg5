package com.fide.proyectowebg5.model;

public class Posicion {

    private Long idPosicion;

    private String descripcion;

    private Long idEstado;


    public Posicion() {
    }


    public Long getIdPosicion() {
        return idPosicion;
    }


    public void setIdPosicion(Long idPosicion) {
        this.idPosicion = idPosicion;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Long getIdEstado() {
        return idEstado;
    }


    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

}