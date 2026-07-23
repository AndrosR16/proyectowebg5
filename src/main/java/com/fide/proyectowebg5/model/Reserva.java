package com.fide.proyectowebg5.model;

import java.time.LocalDate;

public class Reserva {

    private Long idReserva;
    private Long idUsuario;
    private Long idHorario;
    private LocalDate fechaReserva;
    private Long idEstado;

    /*
     * Campos adicionales para mostrar información descriptiva
     * en la lista de reservas.
     */
    private String nombreUsuario;
    private String descripcionHorario;
    private String nombreEstado;

    public Reserva() {
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDate getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDate fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDescripcionHorario() {
        return descripcionHorario;
    }

    public void setDescripcionHorario(String descripcionHorario) {
        this.descripcionHorario = descripcionHorario;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}