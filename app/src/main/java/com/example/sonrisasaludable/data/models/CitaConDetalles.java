package com.example.sonrisasaludable.data.models;

import java.io.Serializable;

public class CitaConDetalles implements Serializable {
    public int id;
    public int usuario_id;
    public int doctor_id;
    public int servicio_id;
    public String fecha;
    public String hora;
    public String estado;

    public String paciente_nombre;
    public String paciente_apellido;
    public int doctor_info;
    public String servicio_nombre;

    public CitaConDetalles(){
    }
    public CitaConDetalles(int id, int usuario_id, int doctor_id,
                           int servicio_id, String fecha, String hora,
                           String estado, String paciente_nombre, String paciente_apellido,
                           int doctor_info, String servicio_nombre) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.doctor_id = doctor_id;
        this.servicio_id = servicio_id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.paciente_nombre = paciente_nombre;
        this.paciente_apellido = paciente_apellido;
        this.doctor_info = doctor_info;
        this.servicio_nombre = servicio_nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getServicio_id() {
        return servicio_id;
    }

    public void setServicio_id(int servicio_id) {
        this.servicio_id = servicio_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPaciente_nombre() {
        return paciente_nombre;
    }

    public void setPaciente_nombre(String paciente_nombre) {
        this.paciente_nombre = paciente_nombre;
    }

    public String getPaciente_apellido() {
        return paciente_apellido;
    }

    public void setPaciente_apellido(String paciente_apellido) {
        this.paciente_apellido = paciente_apellido;
    }

    public int getDoctor_info() {
        return doctor_info;
    }

    public void setDoctor_info(int doctor_info) {
        this.doctor_info = doctor_info;
    }

    public String getServicio_nombre() {
        return servicio_nombre;
    }

    public void setServicio_nombre(String servicio_nombre) {
        this.servicio_nombre = servicio_nombre;
    }
}
