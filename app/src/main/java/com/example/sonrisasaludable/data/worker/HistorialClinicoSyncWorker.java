package com.example.sonrisasaludable.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.HistorialClinicoEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.util.List;

import retrofit2.Response;

public class HistorialClinicoSyncWorker extends Worker {

    public HistorialClinicoSyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<List<HistorialClinicoEntity>> response = RetrofitClient
                    .getApiService()
                    .getHistorialClinico()
                    .execute();

            if (response.isSuccessful() && response.body() != null) {
                List<HistorialClinicoEntity> historiales = response.body();

                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.historialClinicoDao().insertAll(historiales);

                return Result.success();
            } else {
                return Result.retry();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Result.retry();
        }
    }
}
