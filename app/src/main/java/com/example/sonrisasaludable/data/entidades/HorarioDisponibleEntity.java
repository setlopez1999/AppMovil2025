package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "horarios_disponibles",
        foreignKeys = {
                @ForeignKey(
                        entity = DoctorEntity.class,
                        parentColumns = "id",
                        childColumns = "doctor_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index(value = "doctor_id")}
)
public class HorarioDisponibleEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "doctor_id")
    private int doctorId;

    @ColumnInfo(name = "fecha")
    private String fecha;  // Formato: "YYYY-MM-DD"

    @ColumnInfo(name = "hora_inicio")
    private String horaInicio;  // Formato: "HH:MM"

    @ColumnInfo(name = "hora_fin")
    private String horaFin;  // Formato: "HH:MM"

    @ColumnInfo(name = "disponible")
    private boolean disponible;

    // Constructor vacío
    public HorarioDisponibleEntity() {}

    // Constructor completo
    public HorarioDisponibleEntity(int id, int doctorId, String fecha,
                                   String horaInicio, String horaFin, boolean disponible) {
        this.id = id;
        this.doctorId = doctorId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}