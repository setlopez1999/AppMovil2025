package com.example.sonrisasaludable.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sonrisasaludable.data.dao.ReciboDao;
import com.example.sonrisasaludable.data.entidades.ReciboEntity;
import com.example.sonrisasaludable.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReciboRepository {

    private final ReciboDao reciboDao;
    private final ApiService apiService; // opcional

    public ReciboRepository(ReciboDao reciboDao, ApiService apiService) {
        this.reciboDao = reciboDao;
        this.apiService = apiService;
    }

    // --- LOCAL (Room) ---

    public List<ReciboEntity> getAllLocal() {
        return reciboDao.getAll();
    }

    public ReciboEntity getByIdLocal(int id) {
        return reciboDao.getById(id);
    }

    public ReciboEntity getByCitaIdLocal(int citaId) {
        return reciboDao.getByCitaId(citaId);
    }

    public List<ReciboEntity> getByPacienteIdLocal(int pacienteId) {
        return reciboDao.getByPacienteId(pacienteId);
    }

    public List<ReciboEntity> getByEstadoLocal(String estado) {
        return reciboDao.getByEstado(estado);
    }

    public List<ReciboEntity> getByPacienteAndEstadoLocal(int pacienteId, String estado) {
        return reciboDao.getByPacienteAndEstado(pacienteId, estado);
    }

    public List<ReciboEntity> getByMetodoPagoLocal(String metodoPago) {
        return reciboDao.getByMetodoPago(metodoPago);
    }

    public Double getTotalPagadoByPacienteLocal(int pacienteId) {
        return reciboDao.getTotalPagadoByPaciente(pacienteId);
    }

    public Double getTotalPendienteLocal() {
        return reciboDao.getTotalPendiente();
    }

    public List<ReciboEntity> getAllOrderByFechaPagoLocal() {
        return reciboDao.getAllOrderByFechaPago();
    }

    public void updateEstadoYFechaLocal(int reciboId, String nuevoEstado, String fechaPago) {
        reciboDao.updateEstadoYFecha(reciboId, nuevoEstado, fechaPago);
    }

    public void insertLocal(ReciboEntity recibo) {
        reciboDao.insert(recibo);
    }

    public void insertAllLocal(List<ReciboEntity> recibos) {
        reciboDao.insertAll(recibos);
    }

    public void updateLocal(ReciboEntity recibo) {
        reciboDao.update(recibo);
    }

    public void deleteLocal(ReciboEntity recibo) {
        reciboDao.delete(recibo);
    }

    public void deleteAllLocal() {
        reciboDao.deleteAll();
    }

    // --- REMOTO (Retrofit) opcional ---

    public LiveData<List<ReciboEntity>> getAllRemoto() {
        MutableLiveData<List<ReciboEntity>> data = new MutableLiveData<>();

        apiService.getRecibos().enqueue(new Callback<List<ReciboEntity>>() {
            @Override
            public void onResponse(Call<List<ReciboEntity>> call, Response<List<ReciboEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    Log.e("ReciboRepoRemote", "Error en respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ReciboEntity>> call, Throwable t) {
                Log.e("ReciboRepoRemote", "Fallo en llamada: " + t.getMessage());
            }
        });

        return data;
    }
}
