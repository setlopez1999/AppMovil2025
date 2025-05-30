package com.example.sonrisasaludable.data.models;

public class DoctorConUsuario {
    // Campos de DoctorEntity (con sus nombres exactos)
    public int id;
    public int usuarioId;
    public int especialidadId;
    public String descripcion;
    public double reputacion;

    // Campos de UsuarioEntity que quieres traer
    public String nombre;
    public String apellido;
    public String fotoPerfil;
}
