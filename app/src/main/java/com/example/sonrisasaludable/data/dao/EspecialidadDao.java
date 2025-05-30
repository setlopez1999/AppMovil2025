package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.EspecialidadEntity;
import java.util.List;

@Dao
public interface EspecialidadDao {

    @Query("SELECT * FROM especialidades")
    List<EspecialidadEntity> getAll();

    @Query("SELECT * FROM especialidades WHERE id = :id")
    EspecialidadEntity getById(int id);

    @Query("SELECT * FROM especialidades WHERE nombre = :nombre")
    EspecialidadEntity getByNombre(String nombre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EspecialidadEntity especialidad);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<EspecialidadEntity> especialidades);

    @Update
    void update(EspecialidadEntity especialidad);

    @Delete
    void delete(EspecialidadEntity especialidad);

    @Query("DELETE FROM especialidades")
    void deleteAll();
}