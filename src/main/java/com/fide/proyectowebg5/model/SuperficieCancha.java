package com.fide.proyectowebg5.model;

public class SuperficieCancha {

    private Long idSuperficie;
    private String descripcion;
    private Long idEstado;

    public SuperficieCancha() {
    }

    public Long getIdSuperficie() {
        return idSuperficie;
    }

    public void setIdSuperficie(Long idSuperficie) {
        this.idSuperficie = idSuperficie;
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