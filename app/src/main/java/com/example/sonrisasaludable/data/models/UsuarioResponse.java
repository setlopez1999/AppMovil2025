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

    public void setRol_id(int rol_id) {
        this.rol_id = rol_id;
    }

    public void setCreado_en(String creado_en) {
        this.creado_en = creado_en;
    }

    public void setFoto_perfil(String foto_perfil) {
        this.foto_perfil = foto_perfil;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setId(int id) {
        this.id = id;
    }
}
