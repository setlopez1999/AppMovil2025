package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "roles",
        indices = {@Index(value = "nombre", unique = true)}
)
public class RolEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    // Constructor vacío requerido por Room
    public RolEntity() {}

    // Constructor completo
    public RolEntity(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Constructor sin ID (para inserción con autoGenerate si fuera necesario)
    public RolEntity(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "RolEntity{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}