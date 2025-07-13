package com.example.sonrisasaludable.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.ResenaEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ResenaSyncWorker extends Worker {

    private final AppDatabase db;

    public ResenaSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<List<ResenaEntity>> response = RetrofitClient.getApiService().getResenas().execute();

            if (response.isSuccessful() && response.body() != null) {
                List<ResenaEntity> resenas = response.body();

                if (resenas.isEmpty()) {
                    // nada que sincronizar
                    return Result.success();
                }

                // validamos claves foráneas antes de borrar e insertar
                boolean clavesExisten = verificarClavesForaneas(resenas);

                if (!clavesExisten) {
                    // las claves necesarias aún no están, reintentamos después
                    return Result.retry();
                }

                db.resenaDao().deleteAll();
                db.resenaDao().insertAll(resenas);

                return Result.success();
            } else {
                // error del servidor o datos inválidos
                return Result.retry();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.failure();
        }
    }

    /**
     * Verifica que las claves foráneas necesarias existan antes de insertar las reseñas.
     */
    private boolean verificarClavesForaneas(List<ResenaEntity> resenas) {
        for (ResenaEntity resena : resenas) {
            boolean citaExiste = db.citaDao().existeCita(resena.getCitaId());
            if (!citaExiste) {
                return false;
            }
        }
        return true;
    }
}
