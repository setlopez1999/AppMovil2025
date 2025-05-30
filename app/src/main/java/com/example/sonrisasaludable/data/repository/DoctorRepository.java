package com.example.sonrisasaludable.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sonrisasaludable.data.dao.DoctorDao;
import com.example.sonrisasaludable.data.entidades.DoctorEntity;
import com.example.sonrisasaludable.data.network.ApiService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRepository {

    private final DoctorDao doctorDao;
    private final ApiService apiService;
    private final ExecutorService executor;
    private final MutableLiveData<Boolean> isSyncing = new MutableLiveData<>(false);

    public DoctorRepository(DoctorDao doctorDao, ApiService apiService) {
        this.doctorDao = doctorDao;
        this.apiService = apiService;
        this.executor = Executors.newSingleThreadExecutor();
    }

    // Estado de sincronización para la UI
    public LiveData<Boolean> getSyncingStatus() {
        return isSyncing;
    }

    // CRUD + consultas con LiveData
    public LiveData<List<DoctorEntity>> getAllDoctores() {
        MutableLiveData<List<DoctorEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(doctorDao.getAll()));
        return data;
    }

    public LiveData<DoctorEntity> getDoctorById(int id) {
        MutableLiveData<DoctorEntity> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(doctorDao.getById(id)));
        return data;
    }

    public LiveData<List<DoctorEntity>> getDoctorsByEspecialidad(int especialidadId) {
        MutableLiveData<List<DoctorEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(doctorDao.getByEspecialidad(especialidadId)));
        return data;
    }

    public LiveData<List<DoctorEntity>> getDoctorsByMinReputacion(double minReputacion) {
        MutableLiveData<List<DoctorEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(doctorDao.getByMinReputacion(minReputacion)));
        return data;
    }

    // Insert, update, delete CRUD en background
    public void insertDoctor(DoctorEntity doctor) {
        executor.execute(() -> doctorDao.insert(doctor));
    }

    public void insertAllDoctores(List<DoctorEntity> doctores) {
        executor.execute(() -> doctorDao.insertAll(doctores));
    }

    public void updateDoctor(DoctorEntity doctor) {
        executor.execute(() -> doctorDao.update(doctor));
    }

    public void deleteDoctor(DoctorEntity doctor) {
        executor.execute(() -> doctorDao.delete(doctor));
    }

    public void deleteAllDoctores() {
        executor.execute(() -> doctorDao.deleteAll());
    }

    // Sincronización con API remota
    public void sincronizarDoctoresDesdeApi() {
        isSyncing.postValue(true);
        apiService.getDoctores().enqueue(new Callback<List<DoctorEntity>>() {
            @Override
            public void onResponse(Call<List<DoctorEntity>> call, Response<List<DoctorEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> {
                        doctorDao.deleteAll();
                        doctorDao.insertAll(response.body());
                        isSyncing.postValue(false);
                    });
                } else {
                    Log.e("DoctorRepository", "Error sincronizando doctores: " + response.code());
                    isSyncing.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<DoctorEntity>> call, Throwable t) {
                Log.e("DoctorRepository", "Fallo en API: " + t.getMessage());
                isSyncing.postValue(false);
            }
        });
    }
}
