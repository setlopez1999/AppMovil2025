package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "recibos",
        foreignKeys = {
                @ForeignKey(
                        entity = CitaEntity.class,
                        parentColumns = "id",
                        childColumns = "cita_id",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = UsuarioEntity.class,
                        parentColumns = "id",
                        childColumns = "paciente_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "cita_id"),
                @Index(value = "paciente_id")
        }
)
public class ReciboEntity {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "cita_id")
    private int citaId;

    @ColumnInfo(name = "paciente_id")
    private int pacienteId;

    @ColumnInfo(name = "monto")
    private double monto;

    @ColumnInfo(name = "metodo_pago")
    private String metodoPago;  // tarjeta, yape, plin, etc.

    @ColumnInfo(name = "estado")
    private String estado;  // 'pendiente', 'pagado', 'fallido'

    @ColumnInfo(name = "fecha_pago")
    private String fechaPago;

    // Constructor vacío
    public ReciboEntity() {}

    // Constructor completo
    public ReciboEntity(int id, int citaId, int pacienteId, double monto,
                        String metodoPago, String estado, String fechaPago) {
        this.id = id;
        this.citaId = citaId;
        this.pacienteId = pacienteId;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.fechaPago = fechaPago;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCitaId() { return citaId; }
    public void setCitaId(int citaId) { this.citaId = citaId; }

    public int getPacienteId() { return pacienteId; }
    public void setPacienteId(int pacienteId) { this.pacienteId = pacienteId; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFechaPago() { return fechaPago; }
    public void setFechaPago(String fechaPago) { this.fechaPago = fechaPago; }
}