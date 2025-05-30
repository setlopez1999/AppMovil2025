package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "reseñas",
        foreignKeys = {
                @ForeignKey(
                        entity = CitaEntity.class,
                        parentColumns = "id",
                        childColumns = "cita_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index(value = "cita_id")}
)
public class ResenaEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "cita_id")
    private int citaId;

    @ColumnInfo(name = "calificacion")
    private int calificacion;  // Entre 1 y 5

    @ColumnInfo(name = "comentario")
    private String comentario;

    @ColumnInfo(name = "fecha")
    private String fecha;

    // Constructor vacío
    public ResenaEntity() {}

    // Constructor completo
    public ResenaEntity(int id, int citaId, int calificacion, String comentario, String fecha) {
        this.id = id;
        this.citaId = citaId;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCitaId() { return citaId; }
    public void setCitaId(int citaId) { this.citaId = citaId; }

    public int getCalificacion() { return calificacion; }
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}