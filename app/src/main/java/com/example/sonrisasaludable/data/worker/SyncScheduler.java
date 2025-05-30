package com.example.sonrisasaludable.data.worker;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class SyncScheduler {

    public static void scheduleSyncWorkers(Context context) {

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest usuarioSyncWork =
                new PeriodicWorkRequest.Builder(UsuarioSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest doctorSyncWork =
                new PeriodicWorkRequest.Builder(DoctorSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest citaSyncWork =
                new PeriodicWorkRequest.Builder(CitaSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest especialidadSyncWork =
                new PeriodicWorkRequest.Builder(EspecialidadSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest reciboSyncWork =
                new PeriodicWorkRequest.Builder(ReciboSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest resenaSyncWork =
                new PeriodicWorkRequest.Builder(ResenaSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest rolSyncWork =
                new PeriodicWorkRequest.Builder(RolSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest horarioDisponibleSyncWork =
                new PeriodicWorkRequest.Builder(HorarioDisponibleSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest servicioSyncWork =
                new PeriodicWorkRequest.Builder(ServicioSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        PeriodicWorkRequest historialClinicoSyncWork =
                new PeriodicWorkRequest.Builder(HistorialClinicoSyncWorker.class, 15, TimeUnit.MINUTES)
                        .setConstraints(constraints)
                        .build();

        WorkManager workManager = WorkManager.getInstance(context);

        workManager.enqueueUniquePeriodicWork(
                "usuario_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                usuarioSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "doctor_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                doctorSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "cita_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                citaSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "especialidad_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                especialidadSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "recibo_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                reciboSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "resena_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                resenaSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "rol_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                rolSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "horario_disponible_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                horarioDisponibleSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "servicio_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                servicioSyncWork);

        workManager.enqueueUniquePeriodicWork(
                "historial_clinico_sync",
                androidx.work.ExistingPeriodicWorkPolicy.KEEP,
                historialClinicoSyncWork);
    }
}
