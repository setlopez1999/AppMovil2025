package com.example.sonrisasaludable.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sonrisasaludable.data.dao.CitaDao;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.data.models.CitaConDetalles;
import com.example.sonrisasaludable.data.network.ApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitaRepository {

    private final CitaDao citaDao;
    private final ApiService apiService;
    private final ExecutorService executor;
    private final MutableLiveData<Boolean> isSyncing = new MutableLiveData<>(false);

    public CitaRepository(CitaDao citaDao, ApiService apiService) {
        this.citaDao = citaDao;
        this.apiService = apiService;
        this.executor = Executors.newSingleThreadExecutor();

    }

    public LiveData<Boolean> getSyncingStatus() {
        return isSyncing;
    }

    public LiveData<List<CitaEntity>> getAllCitas() {
        MutableLiveData<List<CitaEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(citaDao.getAll()));
        return data;
    }

    public LiveData<CitaEntity> getCitaById(int id) {
        MutableLiveData<CitaEntity> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(citaDao.getById(id)));
        return data;
    }

    public void insertCita(CitaEntity cita) {
        executor.execute(() -> citaDao.insert(cita));
    }

    public void insertAllCitas(List<CitaEntity> citas) {
        executor.execute(() -> citaDao.insertAll(citas));
    }

    public void updateCita(CitaEntity cita) {
        executor.execute(() -> citaDao.update(cita));
    }

    public void deleteCita(CitaEntity cita) {
        executor.execute(() -> citaDao.delete(cita));
    }

    public void deleteAllCitas() {
        executor.execute(citaDao::deleteAll);
    }

    public LiveData<List<CitaConDetalles>> getCitaConDetalles(){
        return citaDao.getCitasConDetalles();
    }
    public void sincronizarCitasDesdeApi() {
        isSyncing.postValue(true);
        apiService.getCitas(2).enqueue(new Callback<List<CitaEntity>>() {
            @Override
            public void onResponse(Call<List<CitaEntity>> call, Response<List<CitaEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> {
                        citaDao.deleteAll();
                        citaDao.insertAll(response.body());
                        isSyncing.postValue(false);
                    });
                } else {
                    Log.e("CitaRepository", "Error sincronizando citas: " + response.code());
                    isSyncing.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<CitaEntity>> call, Throwable t) {
                Log.e("CitaRepository", "Fallo en API: " + t.getMessage());
                isSyncing.postValue(false);
            }
        });
    }
}
