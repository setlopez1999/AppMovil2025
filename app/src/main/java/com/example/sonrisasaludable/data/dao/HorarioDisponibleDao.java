package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.HorarioDisponibleEntity;
import java.util.List;

@Dao
public interface HorarioDisponibleDao {

    @Query("SELECT * FROM horarios_disponibles")
    List<HorarioDisponibleEntity> getAll();

    @Query("SELECT * FROM horarios_disponibles WHERE id = :id")
    HorarioDisponibleEntity getById(int id);

    @Query("SELECT * FROM horarios_disponibles WHERE doctor_id = :doctorId")
    List<HorarioDisponibleEntity> getByDoctorId(int doctorId);

    @Query("SELECT * FROM horarios_disponibles WHERE doctor_id = :doctorId AND fecha = :fecha")
    List<HorarioDisponibleEntity> getByDoctorAndFecha(int doctorId, String fecha);

    @Query("SELECT * FROM horarios_disponibles WHERE doctor_id = :doctorId AND disponible = 1")
    List<HorarioDisponibleEntity> getDisponiblesByDoctor(int doctorId);

    @Query("SELECT * FROM horarios_disponibles WHERE fecha = :fecha AND disponible = 1")
    List<HorarioDisponibleEntity> getDisponiblesByFecha(String fecha);

    @Query("SELECT * FROM horarios_disponibles WHERE fecha >= :fechaInicio AND fecha <= :fechaFin")
    List<HorarioDisponibleEntity> getByRangoFechas(String fechaInicio, String fechaFin);

    @Query("UPDATE horarios_disponibles SET disponible = 0 WHERE id = :id")
    void marcarComoOcupado(int id);

    @Query("UPDATE horarios_disponibles SET disponible = 1 WHERE id = :id")
    void marcarComoDisponible(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HorarioDisponibleEntity horario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<HorarioDisponibleEntity> horarios);

    @Update
    void update(HorarioDisponibleEntity horario);

    @Delete
    void delete(HorarioDisponibleEntity horario);

    @Query("DELETE FROM horarios_disponibles")
    void deleteAll();
}