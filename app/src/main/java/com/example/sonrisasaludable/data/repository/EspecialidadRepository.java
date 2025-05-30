package com.example.sonrisasaludable.data.repository;

import com.example.sonrisasaludable.data.dao.EspecialidadDao;
import com.example.sonrisasaludable.data.entidades.EspecialidadEntity;
import com.example.sonrisasaludable.data.network.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;

public class EspecialidadRepository {

    private final EspecialidadDao especialidadDao;
    private final ApiService apiService;
    private final ExecutorService executorService;

    public EspecialidadRepository(EspecialidadDao especialidadDao, ApiService apiService) {
        this.especialidadDao = especialidadDao;
        this.apiService = apiService;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    // Métodos Room
    public List<EspecialidadEntity> getAll() {
        return especialidadDao.getAll();
    }

    public EspecialidadEntity getById(int id) {
        return especialidadDao.getById(id);
    }

    public void insert(final EspecialidadEntity especialidad) {
        executorService.execute(() -> especialidadDao.insert(especialidad));
    }

    public void insertAll(final List<EspecialidadEntity> especialidades) {
        executorService.execute(() -> especialidadDao.insertAll(especialidades));
    }

    public void update(final EspecialidadEntity especialidad) {
        executorService.execute(() -> especialidadDao.update(especialidad));
    }

    public void delete(final EspecialidadEntity especialidad) {
        executorService.execute(() -> especialidadDao.delete(especialidad));
    }

    public void deleteAll() {
        executorService.execute(especialidadDao::deleteAll);
    }

    // Método API (Retrofit)
    public Call<List<EspecialidadEntity>> fetchEspecialidadesFromApi() {
        return apiService.getEspecialidades();
    }
}
