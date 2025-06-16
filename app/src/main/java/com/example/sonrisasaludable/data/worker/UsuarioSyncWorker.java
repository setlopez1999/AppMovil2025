package com.example.sonrisasaludable.data.worker;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.dao.UsuarioDao;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import com.example.sonrisasaludable.data.network.ApiService;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.repository.UsuarioRepository;

import java.util.List;

import retrofit2.Response;

public class UsuarioSyncWorker extends Worker {

    public UsuarioSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    public static void doSync(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        ApiService api = RetrofitClient.getApiService();
        UsuarioRepository repo = new UsuarioRepository(db.usuarioDao(), api);
        repo.getAllUsuarios(); // o como lo tengas implementado
    }


    @NonNull
    @Override
    public Result doWork() {
        try {
            // Llamada a la API
            Response<List<UsuarioEntity>> response = RetrofitClient
                    .getApiService()
                    .getUsuarios()
                    .execute();

            if (response.isSuccessful() && response.body() != null) {
                List<UsuarioEntity> usuarios = response.body();
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                UsuarioDao usuarioDao = db.usuarioDao();

                boolean huboError = false;

                for (UsuarioEntity usuario : usuarios) {
                    try {
                        Log.d("UsuarioSyncWorker", "Insertando usuario ID: " + usuario.getId()
                                + " con idRol: " + usuario.getRol_id());

                        usuarioDao.insert(usuario); // Inserta uno por uno

                    } catch (SQLiteConstraintException e) {
                        Log.e("UsuarioSyncWorker", "Error en usuario ID: " + usuario.getId()
                                + ", rolId: " + usuario.getRol_id() + " → " + e.getMessage());
                        huboError = true;
                    }
                }

                if (huboError) {
                    return Result.failure(); // O puedes usar Result.success() si quieres ignorar los errores individuales
                } else {
                    return Result.success();
                }

            } else {
                return Result.retry(); // Reintentar si hubo error de red o respuesta vacía
            }

        } catch (Exception e) {
            Log.e("UsuarioSyncWorker", "Excepción general: " + e.getMessage());
            return Result.retry();
        }
    }


}
