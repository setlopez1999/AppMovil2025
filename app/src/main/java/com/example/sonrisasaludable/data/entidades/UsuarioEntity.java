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

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "apellido")
    private String apellido;

    @ColumnInfo(name = "correo")
    private String correo;

    @ColumnInfo(name = "contraseña")  // mejor sin ñ para evitar problemas de codificación
    private String contrasena;

    @ColumnInfo(name = "telefono")
    private String telefono;

    @ColumnInfo(name = "foto_perfil")
    private String fotoPerfil;

    @ColumnInfo(name = "creado_en")
    private String creadoEn;  // Puedes usar String, Date o LocalDateTime, pero con TypeConverters si no es String

    @ColumnInfo(name = "rol_id")
    private int rolId;

    /// ////////////
    @ColumnInfo(name = "dni")
    private String dni;

    @ColumnInfo(name = "direccion")
    private String direccion;

    @ColumnInfo(name = "sexo")
    private String sexo;

    @ColumnInfo(name = "fecha_nacimiento")
    private String fechaNacimiento;  // Usa Date solo si tienes TypeConverter




    // Constructor vacío obligatorio para Room
    public UsuarioEntity() {}

    // Constructor completo
    public UsuarioEntity(int id, String nombre, String apellido, String correo,
                         String contrasena, String telefono, String fotoPerfil,
                         String creadoEn, int rolId,
                         String dni, String direccion, String sexo, String fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.fotoPerfil = fotoPerfil;
        this.creadoEn = creadoEn;
        this.rolId = rolId;
        this.dni = dni;
        this.direccion = direccion;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }

    public String getCreadoEn() { return creadoEn; }
    public void setCreadoEn(String creadoEn) { this.creadoEn = creadoEn; }

    public int getRolId() { return rolId; }
    public void setRolId(int rolId) { this.rolId = rolId; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
