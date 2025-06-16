package com.example.sonrisasaludable.data.entidades;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "usuarios",
        foreignKeys = {
                @ForeignKey(
                        entity = RolEntity.class,
                        parentColumns = "id",
                        childColumns = "rol_id",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "correo", unique = true),
                @Index(value = "rol_id")
        }
)
public class UsuarioEntity {

    @PrimaryKey(autoGenerate = false)  // Si quieres que se genere automático pon true
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "nombres")
    private String nombres;

    @ColumnInfo(name = "apellidos")
    private String apellidos;

    @ColumnInfo(name = "correo")
    private String correo;

    @ColumnInfo(name = "clave")  // mejor sin ñ para evitar problemas de codificación
    private String clave;

    @ColumnInfo(name = "telefono")
    private String telefono;

    @ColumnInfo(name = "foto_perfil")
    private String foto_perfil;

    @ColumnInfo(name = "creado_en")
    private String creado_en;  // Puedes usar String, Date o LocalDateTime, pero con TypeConverters si no es String

    @ColumnInfo(name = "rol_id")
    private int rol_id;

    /// ////////////
    @ColumnInfo(name = "dni")
    private String dni;

    @ColumnInfo(name = "direccion")
    private String direccion;

    @ColumnInfo(name = "sexo")
    private String sexo;

    @ColumnInfo(name = "fechanacimiento")
    private String fechanacimiento;  // Usa Date solo si tienes TypeConverter




    // Constructor vacío obligatorio para Room
    public UsuarioEntity() {}

    // Constructor completo
    public UsuarioEntity(int id, String nombre, String apellido, String correo,
                         String contrasena, String telefono, String foto_perfil,
                         String creado_en, int rol_id,
                         String dni, String direccion, String sexo, String fechanacimiento) {
        this.id = id;
        this.nombres = nombre;
        this.apellidos = apellido;
        this.correo = correo;
        this.clave = contrasena;
        this.telefono = telefono;
        this.foto_perfil = foto_perfil;
        this.creado_en = creado_en;
        this.rol_id = rol_id;
        this.dni = dni;
        this.direccion = direccion;
        this.sexo = sexo;
        this.fechanacimiento = fechanacimiento;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombre) { this.nombres = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellido) { this.apellidos = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFoto_perfil() { return foto_perfil; }
    public void setFoto_perfil(String foto_perfil) { this.foto_perfil = foto_perfil; }

    public String getCreado_en() { return creado_en; }
    public void setCreado_en(String creado_en) { this.creado_en = creado_en; }

    public int getRol_id() { return rol_id; }
    public void setRol_id(int rol_id) { this.rol_id = rol_id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getFechanacimiento() { return fechanacimiento; }
    public void setFechanacimiento(String fechanacimiento) { this.fechanacimiento = fechanacimiento; }
}
