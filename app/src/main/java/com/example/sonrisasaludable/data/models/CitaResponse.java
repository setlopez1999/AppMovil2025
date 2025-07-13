package com.example.sonrisasaludable.data.models;

import com.google.gson.annotations.SerializedName;

public class CitaResponse {

    @SerializedName("cita_id")
    private int citaId;

    @SerializedName("mensaje")
    private String mensaje;

    public CitaResponse(int citaId, String mensaje) {
        this.citaId = citaId;
        this.mensaje = mensaje;
    }

    public int getCitaId() {
        return citaId;
    }

    public void setCitaId(int citaId) {
        this.citaId = citaId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
