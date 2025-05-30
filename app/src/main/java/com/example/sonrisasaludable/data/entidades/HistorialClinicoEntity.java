package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "historial_clinico",
        foreignKeys = {
                @ForeignKey(
                        entity = UsuarioEntity.class,
                        parentColumns = "id",
                        childColumns = "paciente_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = DoctorEntity.class,
                        parentColumns = "id",
                        childColumns = "doctor_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = CitaEntity.class,
                        parentColumns = "id",
                        childColumns = "cita_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "paciente_id"),
                @Index(value = "doctor_id"),
                @Index(value = "cita_id")
        }
)
public class HistorialClinicoEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "paciente_id")
    private int pacienteId;

    @ColumnInfo(name = "doctor_id")
    private int doctorId;

    @ColumnInfo(name = "cita_id")
    private int citaId;

    @ColumnInfo(name = "diagnostico")
    private String diagnostico;

    @ColumnInfo(name = "tratamiento")
    private String tratamiento;

    @ColumnInfo(name = "recomendaciones")
    private String recomendaciones;

    @ColumnInfo(name = "fecha")
    private String fecha;

    // Constructor vacío
    public HistorialClinicoEntity() {}

    // Constructor completo
    public HistorialClinicoEntity(int id, int pacienteId, int doctorId, int citaId,
                                  String diagnostico, String tratamiento,
                                  String recomendaciones, String fecha) {
        this.id = id;
        this.pacienteId = pacienteId;
        this.doctorId = doctorId;
        this.citaId = citaId;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.recomendaciones = recomendaciones;
        this.fecha = fecha;
    }

    // Getters y Setters
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
}