package com.example.sonrisasaludable.data.worker;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.RolEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class RolSyncWorker extends Worker {

    private AppDatabase db;

    public RolSyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<List<RolEntity>> response = RetrofitClient.getApiService().getRoles().execute();
            if (response.isSuccessful() && response.body() != null) {
                List<RolEntity> roles = response.body();
                db.rolDao().insertAll(roles);
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
