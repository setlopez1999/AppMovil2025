package com.example.sonrisasaludable.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sonrisasaludable.data.dao.HorarioDisponibleDao;
import com.example.sonrisasaludable.data.entidades.HorarioDisponibleEntity;
import com.example.sonrisasaludable.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioDisponibleRepository {

    private final HorarioDisponibleDao horarioDao;
    private final ApiService apiService;

    public HorarioDisponibleRepository(HorarioDisponibleDao horarioDao, ApiService apiService) {
        this.horarioDao = horarioDao;
        this.apiService = apiService;
    }

    // --- LOCAL (Room) ---
    public List<HorarioDisponibleEntity> getAllLocal() {
        return horarioDao.getAll();
    }

    public HorarioDisponibleEntity getByIdLocal(int id) {
        return horarioDao.getById(id);
    }

    public List<HorarioDisponibleEntity> getByDoctorIdLocal(int doctorId) {
        return horarioDao.getByDoctorId(doctorId);
    }

    public List<HorarioDisponibleEntity> getByDoctorAndFechaLocal(int doctorId, String fecha) {
        return horarioDao.getByDoctorAndFecha(doctorId, fecha);
    }

    public List<HorarioDisponibleEntity> getDisponiblesByDoctorLocal(int doctorId) {
        return horarioDao.getDisponiblesByDoctor(doctorId);
    }

    public List<HorarioDisponibleEntity> getDisponiblesByFechaLocal(String fecha) {
        return horarioDao.getDisponiblesByFecha(fecha);
    }

    public List<HorarioDisponibleEntity> getByRangoFechasLocal(String inicio, String fin) {
        return horarioDao.getByRangoFechas(inicio, fin);
    }

    public void marcarComoOcupado(int id) {
        horarioDao.marcarComoOcupado(id);
    }

    public void marcarComoDisponible(int id) {
        horarioDao.marcarComoDisponible(id);
    }

    public void insertLocal(HorarioDisponibleEntity horario) {
        horarioDao.insert(horario);
    }

    public void insertAllLocal(List<HorarioDisponibleEntity> horarios) {
        horarioDao.insertAll(horarios);
    }

    public void updateLocal(HorarioDisponibleEntity horario) {
        horarioDao.update(horario);
    }

    public void deleteLocal(HorarioDisponibleEntity horario) {
        horarioDao.delete(horario);
    }

    public void deleteAllLocal() {
        horarioDao.deleteAll();
    }

    // --- REMOTO (Retrofit) ---
    public LiveData<List<HorarioDisponibleEntity>> getAllRemoto() {
        MutableLiveData<List<HorarioDisponibleEntity>> data = new MutableLiveData<>();

        apiService.getHorariosDisponibles().enqueue(new Callback<List<HorarioDisponibleEntity>>() {
            @Override
            public void onResponse(Call<List<HorarioDisponibleEntity>> call, Response<List<HorarioDisponibleEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body());
                } else {
                    Log.e("RepoRemote", "Error en respuesta: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<HorarioDisponibleEntity>> call, Throwable t) {
                Log.e("RepoRemote", "Fallo en llamada: " + t.getMessage());
            }
        });

        return data;
    }
}
