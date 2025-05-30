package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import java.util.List;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuarios")
    List<UsuarioEntity> getAll();

    @Query("SELECT * FROM usuarios WHERE id = :id")
    UsuarioEntity getById(int id);

    @Query("SELECT * FROM usuarios WHERE correo = :correo")
    UsuarioEntity getByCorreo(String correo);

    @Query("SELECT * FROM usuarios WHERE rol_id = :rolId")
    List<UsuarioEntity> getByRol(int rolId);

    @Query("SELECT * FROM usuarios WHERE correo = :correo AND contraseña = :password")
    UsuarioEntity login(String correo, String password);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UsuarioEntity usuario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UsuarioEntity> usuarios);

    @Update
    void update(UsuarioEntity usuario);

    @Delete
    void delete(UsuarioEntity usuario);

    @Query("DELETE FROM usuarios")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM usuarios WHERE correo = :correo")
    int countByCorreo(String correo);
}