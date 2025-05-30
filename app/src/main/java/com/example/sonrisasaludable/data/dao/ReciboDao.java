package com.example.sonrisasaludable.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.sonrisasaludable.data.entidades.ReciboEntity;
import java.util.List;

@Dao
public interface ReciboDao {

    @Query("SELECT * FROM recibos")
    List<ReciboEntity> getAll();

    @Query("SELECT * FROM recibos WHERE id = :id")
    ReciboEntity getById(int id);

    @Query("SELECT * FROM recibos WHERE cita_id = :citaId")
    ReciboEntity getByCitaId(int citaId);

    @Query("SELECT * FROM recibos WHERE paciente_id = :pacienteId")
    List<ReciboEntity> getByPacienteId(int pacienteId);

    @Query("SELECT * FROM recibos WHERE estado = :estado")
    List<ReciboEntity> getByEstado(String estado);

    @Query("SELECT * FROM recibos WHERE paciente_id = :pacienteId AND estado = :estado")
    List<ReciboEntity> getByPacienteAndEstado(int pacienteId, String estado);

    @Query("SELECT * FROM recibos WHERE metodo_pago = :metodoPago")
    List<ReciboEntity> getByMetodoPago(String metodoPago);

    @Query("SELECT SUM(monto) FROM recibos WHERE paciente_id = :pacienteId AND estado = 'pagado'")
    Double getTotalPagadoByPaciente(int pacienteId);

    @Query("SELECT SUM(monto) FROM recibos WHERE estado = 'pendiente'")
    Double getTotalPendiente();

    @Query("SELECT * FROM recibos ORDER BY fecha_pago DESC")
    List<ReciboEntity> getAllOrderByFechaPago();

    @Query("UPDATE recibos SET estado = :nuevoEstado, fecha_pago = :fechaPago WHERE id = :reciboId")
    void updateEstadoYFecha(int reciboId, String nuevoEstado, String fechaPago);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ReciboEntity recibo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ReciboEntity> recibos);

    @Update
    void update(ReciboEntity recibo);

    @Delete
    void delete(ReciboEntity recibo);

    @Query("DELETE FROM recibos")
    void deleteAll();
}