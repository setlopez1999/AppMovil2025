package com.example.sonrisasaludable.data.models;

public class RegisterRequest {
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String clave;
    private String telefono;
    private String direccion;
    private String fechaNacimiento;
    private String sexo;

    public RegisterRequest(String dni, String nombres, String apellidos, String correo, String clave,
                           String telefono, String direccion, String fechaNacimiento, String sexo) {
        this.dni = dni;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.clave = clave;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
    }

    // Getters
    public String getDni() { return dni; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getCorreo() { return correo; }
    public String getClave() { return clave; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getSexo() { return sexo; }

    // Setters
    public void setDni(String dni) { this.dni = dni; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setClave(String clave) { this.clave = clave; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setSexo(String sexo) { this.sexo = sexo; }
}
