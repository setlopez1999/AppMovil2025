package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.ServicioEntity;
import java.util.List;

@Dao
public interface ServicioDao {

    @Query("SELECT * FROM servicios")
    List<ServicioEntity> getAll();

    @Query("SELECT * FROM servicios WHERE id = :id")
    ServicioEntity getById(int id);

    @Query("SELECT * FROM servicios WHERE nombre LIKE :nombre")
    List<ServicioEntity> searchByNombre(String nombre);

    @Query("SELECT * FROM servicios WHERE precio BETWEEN :minPrecio AND :maxPrecio")
    List<ServicioEntity> getByRangoPrecio(double minPrecio, double maxPrecio);

    @Query("SELECT * FROM servicios ORDER BY precio ASC")
    List<ServicioEntity> getAllOrderByPrecio();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ServicioEntity servicio);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ServicioEntity> servicios);

    @Update
    void update(ServicioEntity servicio);

    @Delete
    void delete(ServicioEntity servicio);

    @Query("DELETE FROM servicios")
    void deleteAll();
}