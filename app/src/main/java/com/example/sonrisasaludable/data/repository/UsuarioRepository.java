package com.example.sonrisasaludable.data.repository;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.sonrisasaludable.data.dao.UsuarioDao;
import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import com.example.sonrisasaludable.data.network.ApiService;
import java.util.List;
import java.util.concurrent.Executors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepository {

    private final UsuarioDao usuarioDao;
    private final ApiService apiService;
    private final MutableLiveData<Boolean> isSyncing = new MutableLiveData<>(false);

    public UsuarioRepository(UsuarioDao usuarioDao, ApiService apiService) {
        this.usuarioDao = usuarioDao;
        this.apiService = apiService;
    }

    public LiveData<Boolean> getSyncingStatus() {
        return isSyncing;
    }

    public LiveData<List<UsuarioEntity>> getAllUsuarios() {
        MutableLiveData<List<UsuarioEntity>> usuariosLiveData = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            usuariosLiveData.postValue(usuarioDao.getAll());
        });
        return usuariosLiveData;
    }

    public LiveData<UsuarioEntity> getUsuariobyId(int id){
        MutableLiveData<UsuarioEntity> usuarioEncontrado = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            usuarioEncontrado.postValue(usuarioDao.getById(id));
        });
        return usuarioEncontrado;
    }
    public void insertUsuario(UsuarioEntity usuario) {
        Executors.newSingleThreadExecutor().execute(() -> {
            usuarioDao.insert(usuario);
        });
    }

    public void insertAllUsuarios(List<UsuarioEntity> usuarios) {
        Executors.newSingleThreadExecutor().execute(() -> {
            usuarioDao.insertAll(usuarios);
        });
    }

    public LiveData<UsuarioEntity> getUsuarioPorCorreo(String correo) {
        MutableLiveData<UsuarioEntity> usuarioLiveData = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            usuarioLiveData.postValue(usuarioDao.getByCorreo(correo));
        });
        return usuarioLiveData;
    }

    public LiveData<UsuarioEntity> getUsuarioPorDni(String dni) {
        MutableLiveData<UsuarioEntity> usuarioLiveData = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            usuarioLiveData.postValue(usuarioDao.getByDni(dni));
        });
        return usuarioLiveData;
    }

    public void sincronizarUsuariosDesdeApi() {
        isSyncing.postValue(true);
        apiService.getUsuarios().enqueue(new Callback<List<UsuarioEntity>>() {
            @Override
            public void onResponse(Call<List<UsuarioEntity>> call, Response<List<UsuarioEntity>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        usuarioDao.deleteAll();
                        usuarioDao.insertAll(response.body());
                        isSyncing.postValue(false);
                    });
                } else {
                    Log.e("UsuarioRepo", "Error en sincronización: " + response.code());
                    isSyncing.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioEntity>> call, Throwable t) {
                Log.e("UsuarioRepo", "Fallo en API: " + t.getMessage());
                isSyncing.postValue(false);
            }
        });
    }
}
