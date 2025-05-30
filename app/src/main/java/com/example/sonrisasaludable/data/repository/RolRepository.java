package com.example.sonrisasaludable.data.repository;

import android.util.Log;

import com.example.sonrisasaludable.data.dao.RolDao;
import com.example.sonrisasaludable.data.entidades.RolEntity;
import com.example.sonrisasaludable.data.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RolRepository {

    private final RolDao rolDao;
    private final ApiService apiService;

    public RolRepository(RolDao rolDao, ApiService apiService) {
        this.rolDao = rolDao;
        this.apiService = apiService;
    }

    public List<RolEntity> getAllLocal() {
        return rolDao.getAll();
    }

    public RolEntity getByIdLocal(int id) {
        return rolDao.getById(id);
    }

    public RolEntity getByNombreLocal(String nombre) {
        return rolDao.getByNombre(nombre);
    }

    public void insertLocal(RolEntity rol) {
        rolDao.insert(rol);
    }

    public void insertAllLocal(List<RolEntity> roles) {
        rolDao.insertAll(roles);
    }

    public void updateLocal(RolEntity rol) {
        rolDao.update(rol);
    }

    public void deleteLocal(RolEntity rol) {
        rolDao.delete(rol);
    }

    public void deleteAllLocal() {
        rolDao.deleteAll();
    }

    public void syncFromApi() {
        apiService.getRoles().enqueue(new Callback<List<RolEntity>>() {
            @Override
            public void onResponse(Call<List<RolEntity>> call, Response<List<RolEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rolDao.deleteAll();
                    rolDao.insertAll(response.body());
                } else {
                    Log.e("RolRepository", "Error al sincronizar roles: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RolEntity>> call, Throwable t) {
                Log.e("RolRepository", "Fallo de red al sincronizar roles", t);
            }
        });
    }
}
