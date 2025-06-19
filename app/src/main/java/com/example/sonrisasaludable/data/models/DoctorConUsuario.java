package com.example.sonrisasaludable.data.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class DoctorConUsuario implements Serializable {
    public int id;
    public int usuarioId;
    public int especialidadId;
    public String descripcion;
    public double reputacion;
    public String nombres;
    public String apellidos;
    public String fotoPerfil;
    public String nombreEspecialidad;

    public DoctorConUsuario() {
        // Constructor vacío requerido por Room si es necesario
    }

    public DoctorConUsuario(int id, int usuarioId, int especialidadId,
                            String descripcion, double reputacion, String nombres,
                            String apellidos, String fotoPerfil, String nombreEspecialidad) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.especialidadId = especialidadId;
        this.descripcion = descripcion;
        this.reputacion = reputacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fotoPerfil = fotoPerfil;
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getEspecialidadId() {
        return especialidadId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getReputacion() {
        return reputacion;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }
    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    @NonNull
    @Override
    public String toString() {
        return getNombreCompleto() + " (ID: " + id + ")";
    }
}
