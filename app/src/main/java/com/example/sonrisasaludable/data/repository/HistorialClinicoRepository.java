package com.example.sonrisasaludable.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sonrisasaludable.data.dao.HistorialClinicoDao;
import com.example.sonrisasaludable.data.entidades.HistorialClinicoEntity;
import com.example.sonrisasaludable.data.network.ApiService;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistorialClinicoRepository {

    private final HistorialClinicoDao historialDao;
    private final ApiService apiService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final MutableLiveData<Boolean> isSyncing = new MutableLiveData<>(false);

    public HistorialClinicoRepository(HistorialClinicoDao historialDao, ApiService apiService) {
        this.historialDao = historialDao;
        this.apiService = apiService;
    }

    public LiveData<Boolean> getSyncingStatus() {
        return isSyncing;
    }

    // 🔁 Sincronizar con la API
    public void sincronizarHistorialDesdeApi() {
        isSyncing.postValue(true);
        apiService.getHistorialClinico().enqueue(new Callback<List<HistorialClinicoEntity>>() {
            @Override
            public void onResponse(Call<List<HistorialClinicoEntity>> call, Response<List<HistorialClinicoEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    executor.execute(() -> {
                        historialDao.deleteAll();
                        historialDao.insertAll(response.body());
                        isSyncing.postValue(false);
                    });
                } else {
                    Log.e("HistorialRepo", "Error al sincronizar: " + response.code());
                    isSyncing.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<HistorialClinicoEntity>> call, Throwable t) {
                Log.e("HistorialRepo", "Fallo API: " + t.getMessage());
                isSyncing.postValue(false);
            }
        });
    }

    // 🔽 Consultas locales
    public LiveData<List<HistorialClinicoEntity>> getAllHistoriales() {
        MutableLiveData<List<HistorialClinicoEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.getAll()));
        return data;
    }

    public LiveData<HistorialClinicoEntity> getHistorialById(int id) {
        MutableLiveData<HistorialClinicoEntity> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.getById(id)));
        return data;
    }

    public LiveData<List<HistorialClinicoEntity>> getByPacienteId(int pacienteId) {
        MutableLiveData<List<HistorialClinicoEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.getByPacienteId(pacienteId)));
        return data;
    }

    public LiveData<List<HistorialClinicoEntity>> getByDoctorId(int doctorId) {
        MutableLiveData<List<HistorialClinicoEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.getByDoctorId(doctorId)));
        return data;
    }

    public LiveData<HistorialClinicoEntity> getByCitaId(int citaId) {
        MutableLiveData<HistorialClinicoEntity> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.getByCitaId(citaId)));
        return data;
    }

    public LiveData<List<HistorialClinicoEntity>> getByPacienteAndDoctor(int pacienteId, int doctorId) {
        MutableLiveData<List<HistorialClinicoEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.getByPacienteAndDoctor(pacienteId, doctorId)));
        return data;
    }

    public LiveData<List<HistorialClinicoEntity>> getHistorialPacienteOrderByFecha(int pacienteId) {
        MutableLiveData<List<HistorialClinicoEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.getHistorialPacienteOrderByFecha(pacienteId)));
        return data;
    }

    public LiveData<List<HistorialClinicoEntity>> searchHistorial(String termino) {
        MutableLiveData<List<HistorialClinicoEntity>> data = new MutableLiveData<>();
        executor.execute(() -> data.postValue(historialDao.searchByDiagnosticoOrTratamiento("%" + termino + "%")));
        return data;
    }

    // 🧩 Insertar, actualizar y eliminar
    public void insert(HistorialClinicoEntity historial) {
        executor.execute(() -> historialDao.insert(historial));
    }

    public void insertAll(List<HistorialClinicoEntity> lista) {
        executor.execute(() -> historialDao.insertAll(lista));
    }

    public void update(HistorialClinicoEntity historial) {
        executor.execute(() -> historialDao.update(historial));
    }

    public void delete(HistorialClinicoEntity historial) {
        executor.execute(() -> historialDao.delete(historial));
    }

    public void deleteAll() {
        executor.execute(historialDao::deleteAll);
    }
}
