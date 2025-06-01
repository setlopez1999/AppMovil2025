package com.example.sonrisasaludable.data.models;

public class UsuarioResponse {
    private int id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String clave;
    private String telefono;
    private String direccion;
    private String fechanacimiento;
    private String sexo;
    private String foto_perfil;
    private String creado_en;
    private int rol_id;

    // Getters y Setters
    public int getId() { return id; }
    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getClave() { return clave; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getFechanacimiento() { return fechanacimiento; }
    public String getSexo() { return sexo; }
    public String getFoto_perfil() { return foto_perfil; }
    public String getCreado_en() { return creado_en; }
    public int getRol_id() { return rol_id; }
}
