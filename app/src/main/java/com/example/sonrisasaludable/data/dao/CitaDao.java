package com.example.sonrisasaludable.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import java.util.List;
import com.example.sonrisasaludable.data.models.CitaConDetalles;


@Dao
public interface CitaDao {

    @Query("SELECT * FROM citas")
    List<CitaEntity> getAll();

    @Query("SELECT * FROM citas WHERE id = :id")
    CitaEntity getById(int id);

    @Query("SELECT * FROM citas WHERE usuario_id = :usuarioId")
    List<CitaEntity> getByPacienteId(int usuarioId);

    @Query("SELECT * FROM citas WHERE doctor_id = :doctorId")
    List<CitaEntity> getByDoctorId(int doctorId);

    @Query("SELECT * FROM citas WHERE estado = :estado")
    List<CitaEntity> getByEstado(String estado);

    @Query("SELECT * FROM citas WHERE usuario_id = :usuarioId AND estado = :estado")
    List<CitaEntity> getByPacienteAndEstado(int usuarioId, String estado);

    @Query("SELECT * FROM citas WHERE doctor_id = :doctorId AND estado = :estado")
    List<CitaEntity> getByDoctorAndEstado(int doctorId, String estado);

    @Query("SELECT * FROM citas WHERE fecha = :fecha")
    List<CitaEntity> getByFecha(String fecha);

    @Query("SELECT * FROM citas WHERE fecha >= :fechaInicio AND fecha <= :fechaFin")
    List<CitaEntity> getByRangoFechas(String fechaInicio, String fechaFin);

    @Query("SELECT * FROM citas WHERE usuario_id = :usuarioId ORDER BY fecha DESC, hora DESC")
    List<CitaEntity> getCitasPacienteOrderByFecha(int usuarioId);

    @Query("SELECT * FROM citas WHERE doctor_id = :doctorId ORDER BY fecha ASC, hora ASC")
    List<CitaEntity> getCitasDoctorOrderByFecha(int doctorId);

    // Query con JOIN para obtener citas con datos completos
    @Query("SELECT c.id, c.usuario_id, c.doctor_id, c.servicio_id, c.fecha, c.hora, c.estado, " +
            "u.nombres as paciente_nombre, u.apellidos as paciente_apellido, " +
            "d.id as doctor_info, s.nombre as servicio_nombre " +
            "FROM citas c " +
            "INNER JOIN usuarios u ON c.usuario_id = u.id " +
            "INNER JOIN doctores d ON c.doctor_id = d.id " +
            "LEFT JOIN servicios s ON c.servicio_id = s.id")
    LiveData<List<CitaConDetalles>> getCitasConDetalles();

    @Query("UPDATE citas SET estado = :nuevoEstado WHERE id = :citaId")
    void updateEstado(int citaId, String nuevoEstado);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CitaEntity cita);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CitaEntity> citas);

    @Update
    void update(CitaEntity cita);

    @Delete
    void delete(CitaEntity cita);

    @Query("DELETE FROM citas")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM citas WHERE doctor_id = :doctorId AND fecha = :fecha AND hora = :hora")
    int countCitasConflicto(int doctorId, String fecha, String hora);
}
