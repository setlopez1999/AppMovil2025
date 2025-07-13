package com.example.sonrisasaludable.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sonrisasaludable.data.entidades.SedeEntity;

import java.util.List;

@Dao
public interface SedeDao {

    @Query("SELECT * FROM sedes")
    List<SedeEntity> getAll();

    @Query("SELECT * FROM sedes WHERE id = :id")
    SedeEntity getById(int id);

    @Query("SELECT * FROM sedes WHERE nombre LIKE :nombre")
    List<SedeEntity> searchByNombre(String nombre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SedeEntity sede);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SedeEntity> sedes);

    @Update
    void update(SedeEntity sede);

    @Delete
    void delete(SedeEntity sede);

    @Query("DELETE FROM sedes")
    void deleteAll();

    @Query("SELECT * FROM sedes")
    LiveData<List<SedeEntity>> getAllSedes();

}
