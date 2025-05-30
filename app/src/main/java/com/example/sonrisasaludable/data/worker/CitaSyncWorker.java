package com.example.sonrisasaludable.data.worker;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.util.List;

import retrofit2.Response;

public class CitaSyncWorker extends Worker {

    public CitaSyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<List<CitaEntity>> response = RetrofitClient
                    .getApiService()
                    .getCitas()
                    .execute();

            if (response.isSuccessful() && response.body() != null) {
                List<CitaEntity> citas = response.body();

                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.citaDao().insertAll(citas);

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
