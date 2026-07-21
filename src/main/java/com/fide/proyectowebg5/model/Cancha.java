package com.fide.proyectowebg5.model;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Cancha {

    private Long idCancha;

    @NotBlank(message = "El nombre de la cancha es obligatorio.")
    @Size(
            max = 100,
            message = "El nombre no puede superar los 100 caracteres."
    )
    private String nombreCancha;

    @NotNull(message = "La capacidad es obligatoria.")
    @Min(
            value = 1,
            message = "La capacidad debe ser mayor que cero."
    )
    private Integer capacidad;

    @NotNull(message = "El precio por hora es obligatorio.")
    @DecimalMin(
            value = "0.01",
            message = "El precio por hora debe ser mayor que cero."
    )
    private BigDecimal precioHora;

    @NotNull(message = "Debe seleccionar una superficie.")
    private Long idSuperficie;

    private String superficie;

    @NotNull(message = "Debe seleccionar un estado.")
    private Long idEstado;

    private String estado;

    public Cancha() {
    }

    public Long getIdCancha() {
        return idCancha;
    }

    public void setIdCancha(Long idCancha) {
        this.idCancha = idCancha;
    }

    public String getNombreCancha() {
        return nombreCancha;
    }

    public void setNombreCancha(String nombreCancha) {
        this.nombreCancha = nombreCancha;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public BigDecimal getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(BigDecimal precioHora) {
        this.precioHora = precioHora;
    }

    public Long getIdSuperficie() {
        return idSuperficie;
    }

    public void setIdSuperficie(Long idSuperficie) {
        this.idSuperficie = idSuperficie;
    }

    public String getSuperficie() {
        return superficie;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
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