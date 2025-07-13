package com.example.sonrisasaludable.data.models;

public class ResenaConDetalles {
    private int resenaId;
    private int citaId;
    private int calificacion;
    private String comentario;
    private String fecha;
    private String nombrePaciente;
    private String nombreDoctor;
    private String servicioNombre;

    public ResenaConDetalles(int resenaId, int citaId, int calificacion, String comentario, 
                           String fecha, String nombrePaciente, String nombreDoctor, String servicioNombre) {
        this.resenaId = resenaId;
        this.citaId = citaId;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = fecha;
        this.nombrePaciente = nombrePaciente;
        this.nombreDoctor = nombreDoctor;
        this.servicioNombre = servicioNombre;
    }

    // Getters y Setters
    public int getResenaId() { return resenaId; }
    public void setResenaId(int resenaId) { this.resenaId = resenaId; }

    public int getCitaId() { return citaId; }
    public void setCitaId(int citaId) { this.citaId = citaId; }

    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getNombreDoctor() { return nombreDoctor; }
    public void setNombreDoctor(String nombreDoctor) { this.nombreDoctor = nombreDoctor; }

    public String getServicioNombre() { return servicioNombre; }
    public void setServicioNombre(String servicioNombre) { this.servicioNombre = servicioNombre; }
}