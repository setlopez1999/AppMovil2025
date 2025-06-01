// LoginRequest.java
package com.example.sonrisasaludable.data.models;

public class LoginRequest {
    private String correo;
    private String clave;

    public LoginRequest(String correo, String contrasena) {
        this.correo = correo;
        this.clave = contrasena;
    }

    // getters y setters si usas Gson o Retrofit los necesita
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }
}