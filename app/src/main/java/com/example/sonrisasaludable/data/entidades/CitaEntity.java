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
    private int usuarioId;  // paciente

    @ColumnInfo(name = "doctor_id")
    private int doctorId;

    @ColumnInfo(name = "servicio_id")
    private Integer servicioId;  // Puede ser null

    @ColumnInfo(name = "fecha")
    private String fecha;  // Formato: "YYYY-MM-DD"

    @ColumnInfo(name = "hora")
    private String hora;  // Formato: "HH:MM"

    @ColumnInfo(name = "estado")
    private String estado;  // 'Pendiente', 'Confirmada', 'Cancelada', 'Completada'

    @ColumnInfo(name = "nota")
    private String nota;

    @ColumnInfo(name = "creado_en")
    private String creadoEn;

    // Constructor vacío
    public CitaEntity() {}

    // Constructor completo
    public CitaEntity(int id, int usuarioId, int doctorId, Integer servicioId,
                      String fecha, String hora, String estado, String nota, String creadoEn) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.doctorId = doctorId;
        this.servicioId = servicioId;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.nota = nota;
        this.creadoEn = creadoEn;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public Integer getServicioId() { return servicioId; }
    public void setServicioId(Integer servicioId) { this.servicioId = servicioId; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNota() { return nota; }
    public void setNota(String nota) { this.nota = nota; }

    public String getCreadoEn() { return creadoEn; }
    public void setCreadoEn(String creadoEn) { this.creadoEn = creadoEn; }
}