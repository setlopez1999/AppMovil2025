package com.example.sonrisasaludable.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.SedeEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class SedeSyncWorker extends Worker {

    private final AppDatabase db;

    public SedeSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<List<SedeEntity>> response = RetrofitClient.getApiService().getSedes().execute();
            if (response.isSuccessful() && response.body() != null) {
                List<SedeEntity> sedes = response.body();
                db.sedeDao().deleteAll();
                db.sedeDao().insertAll(sedes);
                return Result.success();
            } else {
                return Result.retry();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.retry();
        }
    }
}
