package com.example.sonrisasaludable.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.util.List;

import retrofit2.Response;

public class UsuarioSyncWorker extends Worker {

    public UsuarioSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
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

                // Insertar en Room
                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.usuarioDao().insertAll(usuarios); // Asegúrate que insertAll esté definido

                return Result.success();
            } else {
                return Result.retry(); // Reintentar si hubo error de red o respuesta vacía
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry(); // Reintentar en caso de excepción
        }
    }
}
