package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "doctores",
        foreignKeys = {
                @ForeignKey(
                        entity = UsuarioEntity.class,
                        parentColumns = "id",
                        childColumns = "usuario_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = EspecialidadEntity.class,
                        parentColumns = "id",
                        childColumns = "especialidad_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "usuario_id", unique = true),
                @Index(value = "especialidad_id")
        }
)
public class DoctorEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "usuario_id")
    private int usuario_id;

    @ColumnInfo(name = "especialidad_id")
    private int especialidad_id;
    @ColumnInfo(name = "descripcion")
    private String descripcion;

    @ColumnInfo(name = "reputacion")
    private double reputacion;

    // Constructor vacío
    public DoctorEntity() {}

    // Constructor completo
    public DoctorEntity(int id, int usuario_id, int especialidad_id,
                        String descripcion, double reputacion) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.especialidad_id = especialidad_id;
        this.descripcion = descripcion;
        this.reputacion = reputacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuario_id() { return usuario_id; }
    public void setUsuario_id(int usuario_id) { this.usuario_id = usuario_id; }

    public int getEspecialidad_id() { return especialidad_id; }
    public void setEspecialidad_id(int especialidad_id) { this.especialidad_id = especialidad_id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getReputacion() { return reputacion; }
    public void setReputacion(double reputacion) { this.reputacion = reputacion; }

}