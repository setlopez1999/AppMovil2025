// LoginResponse.java
package com.example.sonrisasaludable.data.models;

public class LoginResponse {
    private String token;
    private String rol;

    public String getToken() { return token; }
    public String getRol() { return rol; }

    public void setToken(String token) { this.token = token; }
    public void setRol(String rol) { this.rol = rol; }
}
