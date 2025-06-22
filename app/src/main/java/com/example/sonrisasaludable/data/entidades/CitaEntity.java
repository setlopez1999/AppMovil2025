package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "citas",
        foreignKeys = {
                @ForeignKey(
                        entity = UsuarioEntity.class,
                        parentColumns = "id",
                        childColumns = "usuario_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = DoctorEntity.class,
                        parentColumns = "id",
                        childColumns = "doctor_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = ServicioEntity.class,
                        parentColumns = "id",
                        childColumns = "servicio_id",
                        onDelete = ForeignKey.SET_NULL
                )
        },
        indices = {
                @Index(value = "usuario_id"),
                @Index(value = "doctor_id"),
                @Index(value = "servicio_id")
        }
)
public class CitaEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "usuario_id")
    private int usuario_id;  // paciente

    @ColumnInfo(name = "doctor_id")
    private int doctor_id;

    @ColumnInfo(name = "servicio_id")
    private Integer servicio_id;  // Puede ser null

    @ColumnInfo(name = "fecha")
    private String fecha;  // Formato: "YYYY-MM-DD"

    @ColumnInfo(name = "hora")
    private String hora;  // Formato: "HH:MM"

    @ColumnInfo(name = "estado")
    private String estado;  // 'Pendiente', 'Confirmada', 'Cancelada', 'Completada'

    @ColumnInfo(name = "nota")
    private String nota;

    @ColumnInfo(name = "creado_en")
    private String creado_en;

    // Constructor vacío
    public CitaEntity() {}

    // Constructor completo
    public CitaEntity(int id, int usuario_id, int doctor_id, Integer servicio_id,
                      String fecha, String hora, String estado, String nota, String creado_en) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.doctor_id = doctor_id;
        this.servicio_id = servicio_id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.nota = nota;
        this.creado_en = creado_en;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuario_id() { return usuario_id; }
    public void setUsuario_id(int usuario_id) { this.usuario_id = usuario_id; }

    public int getDoctor_id() { return doctor_id; }
    public void setDoctor_id(int doctor_id) { this.doctor_id = doctor_id; }

    public Integer getServicio_id() { return servicio_id; }
    public void setServicio_id(Integer servicio_id) { this.servicio_id = servicio_id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public String getCreado_en() { return creado_en; }
    public void setCreado_en(String creado_en) { this.creado_en = creado_en; }
}