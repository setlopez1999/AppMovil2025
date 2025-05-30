package com.example.sonrisasaludable.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.ReciboEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ReciboSyncWorker extends Worker {

    private AppDatabase db;

    public ReciboSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<List<ReciboEntity>> response = RetrofitClient.getApiService().getRecibos().execute();
            if (response.isSuccessful() && response.body() != null) {
                List<ReciboEntity> recibos = response.body();
                db.reciboDao().deleteAll();
                db.reciboDao().insertAll(recibos);
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
