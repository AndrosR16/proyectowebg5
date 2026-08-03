package com.fide.proyectowebg5.model;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Equipo {

    private Long idEquipo;

    private String nombreUsuario;

    @NotNull(message = "Debe seleccionar un usuario.")
    private Long idUsuario;

    @NotBlank(message = "El nombre del equipo es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    private String nombreEquipo;

    @NotNull(message = "La fecha de creación es obligatoria.")
    private LocalDate fechaCreacion;

    @NotNull(message = "Debe seleccionar un estado.")
    private Long idEstado;

    private String estado;

    public Equipo() {
    }

    public Long getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Long idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

public String getNombreUsuario() {
    return nombreUsuario;
}

public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
}