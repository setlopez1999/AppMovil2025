package com.example.sonrisasaludable.data.worker;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.DoctorEntity;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.util.List;

import retrofit2.Response;

public class DoctorSyncWorker extends Worker {

    public DoctorSyncWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            Response<List<DoctorEntity>> response = RetrofitClient
                    .getApiService()
                    .getDoctores()
                    .execute();

            if (response.isSuccessful() && response.body() != null) {
                List<DoctorEntity> doctores = response.body();

                AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                db.doctorDao().insertAll(doctores);

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
