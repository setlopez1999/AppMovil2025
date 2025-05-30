package com.example.sonrisasaludable.data.models;

import androidx.annotation.NonNull;

public class HistorialConDetalles {

    private int id;
    private int pacienteId;
    private int doctorId;
    private int citaId;
    private String diagnostico;
    private String tratamiento;
    private String recomendaciones;
    private String fecha;

    private String pacienteNombre;
    private String pacienteApellido;
    private int doctorInfo;
    private String citaFecha;

    public HistorialConDetalles() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public int getCitaId() { return citaId; }
    public void setCitaId(int citaId) { this.citaId = citaId; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }

    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }

    public String getPacienteApellido() { return pacienteApellido; }
    public void setPacienteApellido(String pacienteApellido) { this.pacienteApellido = pacienteApellido; }

    public int getDoctorInfo() { return doctorInfo; }
    public void setDoctorInfo(int doctorInfo) { this.doctorInfo = doctorInfo; }

    public String getCitaFecha() { return citaFecha; }
    public void setCitaFecha(String citaFecha) { this.citaFecha = citaFecha; }
}
