package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.RolEntity;
import java.util.List;

@Dao
public interface RolDao {

    @Query("SELECT * FROM roles")
    List<RolEntity> getAll();

    @Query("SELECT * FROM roles WHERE id = :id")
    RolEntity getById(int id);

    @Query("SELECT * FROM roles WHERE nombre = :nombre")
    RolEntity getByNombre(String nombre);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(RolEntity rol);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RolEntity> roles);

    @Update
    void update(RolEntity rol);

    @Delete
    void delete(RolEntity rol);

    @Query("DELETE FROM roles")
    void deleteAll();
}