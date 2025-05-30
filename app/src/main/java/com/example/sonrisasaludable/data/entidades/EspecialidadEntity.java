package com.example.sonrisasaludable.data.entidades;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "especialidades",
        indices = {@Index(value = "nombre", unique = true)}
)
public class EspecialidadEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    // Constructor vacío
    public EspecialidadEntity() {}

    // Constructor completo
    public EspecialidadEntity(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}