package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.HistorialClinicoEntity;
import com.example.sonrisasaludable.data.models.HistorialConDetalles;
import java.util.List;

@Dao
public interface HistorialClinicoDao {

    @Query("SELECT * FROM historial_clinico")
    List<HistorialClinicoEntity> getAll();

    @Query("SELECT * FROM historial_clinico WHERE id = :id")
    HistorialClinicoEntity getById(int id);

    @Query("SELECT * FROM historial_clinico WHERE paciente_id = :pacienteId")
    List<HistorialClinicoEntity> getByPacienteId(int pacienteId);

    @Query("SELECT * FROM historial_clinico WHERE doctor_id = :doctorId")
    List<HistorialClinicoEntity> getByDoctorId(int doctorId);

    @Query("SELECT * FROM historial_clinico WHERE cita_id = :citaId")
    HistorialClinicoEntity getByCitaId(int citaId);

    @Query("SELECT * FROM historial_clinico WHERE paciente_id = :pacienteId AND doctor_id = :doctorId")
    List<HistorialClinicoEntity> getByPacienteAndDoctor(int pacienteId, int doctorId);

    @Query("SELECT * FROM historial_clinico WHERE paciente_id = :pacienteId ORDER BY fecha DESC")
    List<HistorialClinicoEntity> getHistorialPacienteOrderByFecha(int pacienteId);

    @Query("SELECT * FROM historial_clinico WHERE diagnostico LIKE :busqueda OR tratamiento LIKE :busqueda")
    List<HistorialClinicoEntity> searchByDiagnosticoOrTratamiento(String busqueda);

    // Query con JOIN para obtener historial con datos completos
    @Query("SELECT " +
            "h.id, " +
            "h.paciente_id AS pacienteId, " +
            "h.doctor_id AS doctorId, " +
            "h.cita_id AS citaId, " +
            "h.diagnostico, " +
            "h.tratamiento, " +
            "h.recomendaciones, " +
            "h.fecha, " +
            "u.nombres AS pacienteNombre, " +
            "u.apellidos AS pacienteApellido, " +
            "d.id AS doctorInfo, " +
            "c.fecha AS citaFecha " +
            "FROM historial_clinico h " +
            "INNER JOIN usuarios u ON h.paciente_id = u.id " +
            "INNER JOIN doctores d ON h.doctor_id = d.id " +
            "INNER JOIN citas c ON h.cita_id = c.id")
    List<HistorialConDetalles> getHistorialConDetalles();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HistorialClinicoEntity historial);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HistorialClinicoEntity> historiales);

    @Update
    void update(HistorialClinicoEntity historial);

    @Delete
    void delete(HistorialClinicoEntity historial);

    @Query("DELETE FROM historial_clinico")
    void deleteAll();
}