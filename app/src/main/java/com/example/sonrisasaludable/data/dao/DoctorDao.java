package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.DoctorEntity;
import java.util.List;
import com.example.sonrisasaludable.data.models.DoctorConUsuario;
@Dao
public interface DoctorDao {

    @Query("SELECT * FROM doctores")
    List<DoctorEntity> getAll();

    @Query("SELECT * FROM doctores WHERE id = :id")
    DoctorEntity getById(int id);

    @Query("SELECT * FROM doctores WHERE usuario_id = :usuarioId")
    DoctorEntity getByUsuarioId(int usuarioId);

    @Query("SELECT * FROM doctores WHERE especialidad_id = :especialidadId")
    List<DoctorEntity> getByEspecialidad(int especialidadId);

    @Query("SELECT * FROM doctores ORDER BY reputacion DESC")
    List<DoctorEntity> getAllOrderByReputacion();

    @Query("SELECT * FROM doctores WHERE reputacion >= :minReputacion")
    List<DoctorEntity> getByMinReputacion(double minReputacion);

    // Query con JOIN para obtener doctores con datos del usuario

    @Query("SELECT d.id, d.usuario_id AS usuarioId, d.especialidad_id AS especialidadId, d.descripcion, d.reputacion, " +
            "u.nombre, u.apellido, u.foto_perfil AS fotoPerfil " +
            "FROM doctores d " +
            "INNER JOIN usuarios u ON d.usuario_id = u.id")
    List<DoctorConUsuario> getDoctoresConUsuario();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DoctorEntity doctor);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<DoctorEntity> doctores);

    @Update
    void update(DoctorEntity doctor);

    @Delete
    void delete(DoctorEntity doctor);

    @Query("DELETE FROM doctores")
    void deleteAll();
}