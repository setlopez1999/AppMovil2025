package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.ResenaEntity;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.data.models.ResenaConDetalles;
import java.util.List;

@Dao
public interface ResenaDao {

    @Query("SELECT * FROM reseñas")
    List<ResenaEntity> getAll();

    @Query("SELECT * FROM reseñas WHERE id = :id")
    ResenaEntity getById(int id);

    @Query("SELECT * FROM reseñas WHERE cita_id = :citaId")
    ResenaEntity getByCitaId(int citaId);

    @Query("SELECT r.* FROM reseñas r " +
            "INNER JOIN citas c ON r.cita_id = c.id " +
            "WHERE c.doctor_id = :doctorId")
    List<ResenaEntity> getByDoctorId(int doctorId);

    @Query("SELECT r.* FROM reseñas r " +
            "INNER JOIN citas c ON r.cita_id = c.id " +
            "WHERE c.usuario_id = :pacienteId")
    List<ResenaEntity> getByPacienteId(int pacienteId);

    @Query("SELECT * FROM reseñas WHERE calificacion = :calificacion")
    List<ResenaEntity> getByCalificacion(int calificacion);

    @Query("SELECT * FROM reseñas WHERE calificacion >= :minCalificacion")
    List<ResenaEntity> getByMinCalificacion(int minCalificacion);

    @Query("SELECT AVG(calificacion) FROM reseñas r " +
            "INNER JOIN citas c ON r.cita_id = c.id " +
            "WHERE c.doctor_id = :doctorId")
    Double getPromedioCalificacionDoctor(int doctorId);

    @Query("SELECT * FROM reseñas ORDER BY fecha DESC")
    List<ResenaEntity> getAllOrderByFecha();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResenaEntity resena);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ResenaEntity> resenas);

    @Update
    void update(ResenaEntity resena);

    @Delete
    void delete(ResenaEntity resena);

    @Query("DELETE FROM reseñas")
    void deleteAll();

    // Nuevas consultas para funcionalidades core
    @Query("SELECT r.id as resenaId, r.cita_id as citaId, r.calificacion, r.comentario, r.fecha, " +
            "u.nombres || ' ' || u.apellidos as nombrePaciente, " +
            "ud.nombres || ' ' || ud.apellidos as nombreDoctor, s.nombre as servicioNombre " +
            "FROM reseñas r " +
            "INNER JOIN citas c ON r.cita_id = c.id " +
            "INNER JOIN usuarios u ON c.usuario_id = u.id " +
            "INNER JOIN doctores d ON c.doctor_id = d.id " +
            "INNER JOIN usuarios ud ON d.usuario_id = ud.id " +
            "LEFT JOIN servicios s ON c.servicio_id = s.id " +
            "WHERE c.doctor_id = :doctorId ORDER BY r.fecha DESC")
    List<ResenaConDetalles> getResenasByDoctorWithDetails(int doctorId);

    @Query("SELECT COUNT(*) FROM reseñas r " +
            "INNER JOIN citas c ON r.cita_id = c.id " +
            "WHERE c.doctor_id = :doctorId")
    int getCountResenasByDoctor(int doctorId);

    @Query("SELECT * FROM citas WHERE usuario_id = :pacienteId AND estado = 'Completada' " +
            "AND id NOT IN (SELECT cita_id FROM reseñas)")
    List<CitaEntity> getCitasCompletadasSinResena(int pacienteId);

    @Query("SELECT COUNT(*) > 0 FROM reseñas r " +
            "INNER JOIN citas c ON r.cita_id = c.id " +
            "WHERE c.id = :citaId AND c.usuario_id = :pacienteId")
    boolean puedeEditarResena(int citaId, int pacienteId);
}