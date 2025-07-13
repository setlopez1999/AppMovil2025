package com.example.sonrisasaludable.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sonrisasaludable.data.dao.SedeDao;
import com.example.sonrisasaludable.data.entidades.SedeEntity;
import com.example.sonrisasaludable.data.network.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SedeRepository {

    private final SedeDao sedeDao;
    private final ApiService apiService;
    private final ExecutorService executor;
    private final MutableLiveData<Boolean> isSyncing = new MutableLiveData<>(false);

    public SedeRepository(SedeDao sedeDao, ApiService apiService) {
        this.sedeDao = sedeDao;
        this.apiService = apiService;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<Boolean> getSyncingStatus() {
        return isSyncing;
    }

    public LiveData<List<SedeEntity>> getAllSedes() {
        MutableLiveData<List<SedeEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(sedeDao.getAll()));
        return data;
    }

    public LiveData<SedeEntity> getSedeById(int id) {
        MutableLiveData<SedeEntity> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(sedeDao.getById(id)));
        return data;
    }

    public void insertSede(SedeEntity sede) {
        executor.execute(() -> sedeDao.insert(sede));
    }

    public void insertAllSedes(List<SedeEntity> sedes) {
        executor.execute(() -> sedeDao.insertAll(sedes));
    }

    public void updateSede(SedeEntity sede) {
        executor.execute(() -> sedeDao.update(sede));
    }

    public void deleteSede(SedeEntity sede) {
        executor.execute(() -> sedeDao.delete(sede));
    }

    public void deleteAllSedes() {
        executor.execute(() -> sedeDao.deleteAll());
    }

    public void sincronizarSedesDesdeApi() {
        isSyncing.postValue(true);
        apiService.getSedes().enqueue(new Callback<List<SedeEntity>>() {
            @Override
            public void onResponse(Call<List<SedeEntity>> call, Response<List<SedeEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> {
                        sedeDao.deleteAll();
                        sedeDao.insertAll(response.body());
                        isSyncing.postValue(false);
                    });
                } else {
                    Log.e("SedeRepository", "Error sincronizando sedes: " + response.code());
                    isSyncing.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<SedeEntity>> call, Throwable t) {
                Log.e("SedeRepository", "Fallo en API: " + t.getMessage());
                isSyncing.postValue(false);
            }
        });
    }
}
