package com.example.sonrisasaludable.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sonrisasaludable.data.dao.ServicioDao;
import com.example.sonrisasaludable.data.entidades.ServicioEntity;
import com.example.sonrisasaludable.data.network.ApiService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioRepository {

    private final ServicioDao servicioDao;
    private final ApiService apiService;
    private final ExecutorService executor;
    private final MutableLiveData<Boolean> isSyncing = new MutableLiveData<>(false);

    public ServicioRepository(ServicioDao servicioDao, ApiService apiService) {
        this.servicioDao = servicioDao;
        this.apiService = apiService;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<Boolean> getSyncingStatus() {
        return isSyncing;
    }

    public LiveData<List<ServicioEntity>> getAllServicios() {
        MutableLiveData<List<ServicioEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(servicioDao.getAll()));
        return data;
    }

    public LiveData<ServicioEntity> getServicioById(int id) {
        MutableLiveData<ServicioEntity> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(servicioDao.getById(id)));
        return data;
    }

    public void insertServicio(ServicioEntity servicio) {
        executor.execute(() -> servicioDao.insert(servicio));
    }

    public void insertAllServicios(List<ServicioEntity> servicios) {
        executor.execute(() -> servicioDao.insertAll(servicios));
    }

    public void updateServicio(ServicioEntity servicio) {
        executor.execute(() -> servicioDao.update(servicio));
    }

    public void deleteServicio(ServicioEntity servicio) {
        executor.execute(() -> servicioDao.delete(servicio));
    }

    public void deleteAllServicios() {
        executor.execute(() -> servicioDao.deleteAll());
    }

    public void sincronizarServiciosDesdeApi() {
        isSyncing.postValue(true);
        apiService.getServicios().enqueue(new Callback<List<ServicioEntity>>() {
            @Override
            public void onResponse(Call<List<ServicioEntity>> call, Response<List<ServicioEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> {
                        servicioDao.deleteAll();
                        servicioDao.insertAll(response.body());
                        isSyncing.postValue(false);
                    });
                } else {
                    Log.e("ServicioRepository", "Error sincronizando servicios: " + response.code());
                    isSyncing.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<ServicioEntity>> call, Throwable t) {
                Log.e("ServicioRepository", "Fallo en API: " + t.getMessage());
                isSyncing.postValue(false);
            }
        });
    }
}
