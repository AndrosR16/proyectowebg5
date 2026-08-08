package com.fide.proyectowebg5.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Usuario {

    private Long idUsuario;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio.")
    @Size(max = 100, message = "El apellido paterno no puede superar los 100 caracteres.")
    private String apellidoP;

    @NotBlank(message = "El apellido materno es obligatorio.")
    @Size(max = 100, message = "El apellido materno no puede superar los 100 caracteres.")
    private String apellidoM;

    @Size(max = 255, message = "La contraseña no puede superar los 255 caracteres.")
    private String contrasena;

    @NotBlank(message = "Debe seleccionar un rol.")
    @Size(max = 50, message = "El rol no puede superar los 50 caracteres.")
    private String rol;

    @NotNull(message = "Debe seleccionar un estado.")
    private Long idEstado;

    private String nombreEstado;

    @NotBlank(message = "El nombre de usuario es obligatorio.")
    @Size(max = 100, message = "El nombre de usuario no puede superar los 100 caracteres.")
    private String username;

    public Usuario() {
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombreCompleto() {

        String nombreCompleto = nombre + " " + apellidoP;

        if (apellidoM != null && !apellidoM.isBlank()) {
            nombreCompleto += " " + apellidoM;
        }

        return nombreCompleto;
    }
}