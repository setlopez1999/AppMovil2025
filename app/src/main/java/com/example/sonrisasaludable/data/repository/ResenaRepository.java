package com.example.sonrisasaludable.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sonrisasaludable.data.dao.ResenaDao;
import com.example.sonrisasaludable.data.entidades.ResenaEntity;
import com.example.sonrisasaludable.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResenaRepository {

    private final ResenaDao resenaDao;
    private final ApiService apiService; // opcional, si usas Retrofit

    public ResenaRepository(ResenaDao resenaDao, ApiService apiService) {
        this.resenaDao = resenaDao;
        this.apiService = apiService;
    }

    // --- ACCESO LOCAL (Room) ---

    public List<ResenaEntity> getAllLocal() {
        return resenaDao.getAll();
    }

    public ResenaEntity getByIdLocal(int id) {
        return resenaDao.getById(id);
    }

    public ResenaEntity getByCitaIdLocal(int citaId) {
        return resenaDao.getByCitaId(citaId);
    }

    public List<ResenaEntity> getByDoctorIdLocal(int doctorId) {
        return resenaDao.getByDoctorId(doctorId);
    }

    public List<ResenaEntity> getByPacienteIdLocal(int pacienteId) {
        return resenaDao.getByPacienteId(pacienteId);
    }

    public List<ResenaEntity> getByCalificacionLocal(int calificacion) {
        return resenaDao.getByCalificacion(calificacion);
    }

    public List<ResenaEntity> getByMinCalificacionLocal(int minCalificacion) {
        return resenaDao.getByMinCalificacion(minCalificacion);
    }

    public Double getPromedioCalificacionDoctorLocal(int doctorId) {
        return resenaDao.getPromedioCalificacionDoctor(doctorId);
    }

    public List<ResenaEntity> getAllOrderByFechaLocal() {
        return resenaDao.getAllOrderByFecha();
    }

    public void insertLocal(ResenaEntity resena) {
        resenaDao.insert(resena);
    }

    public void insertAllLocal(List<ResenaEntity> resenas) {
        resenaDao.insertAll(resenas);
    }

    public void updateLocal(ResenaEntity resena) {
        resenaDao.update(resena);
    }

    public void deleteLocal(ResenaEntity resena) {
        resenaDao.delete(resena);
    }

    public void deleteAllLocal() {
        resenaDao.deleteAll();
    }

    // --- ACCESO REMOTO (Retrofit) opcional ---

    public LiveData<List<ResenaEntity>> getAllRemoto() {
        MutableLiveData<List<ResenaEntity>> data = new MutableLiveData<>();

        apiService.getResenas().enqueue(new Callback<List<ResenaEntity>>() {
            @Override
            public void onResponse(Call<List<ResenaEntity>> call, Response<List<ResenaEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    Log.e("ResenaRepoRemote", "Error en respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ResenaEntity>> call, Throwable t) {
                Log.e("ResenaRepoRemote", "Fallo en llamada: " + t.getMessage());
            }
        });

        return data;
    }
}
