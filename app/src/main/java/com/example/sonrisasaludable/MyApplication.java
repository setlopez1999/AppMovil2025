package com.example.sonrisasaludable;

import android.app.Application;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.repository.*;
import com.example.sonrisasaludable.data.worker.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {

    private static MyApplication instance;
    private AppDatabase database;

    // Repositorios globales (opcional, según tu patrón)
    private UsuarioRepository usuarioRepository;
    private DoctorRepository doctorRepository;
    private CitaRepository citaRepository;
    private EspecialidadRepository especialidadRepository;
    private HistorialClinicoRepository historialClinicoRepository;
    private HorarioDisponibleRepository horarioDisponibleRepository;
    private ReciboRepository reciboRepository;
    private ResenaRepository resenaRepository;
    private RolRepository rolRepository;
    private ServicioRepository servicioRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Inicializar la base de datos Room
        database = AppDatabase.getInstance(this);

        usuarioRepository = new UsuarioRepository(database.usuarioDao(), RetrofitClient.getApiService());
        doctorRepository = new DoctorRepository(database.doctorDao(), RetrofitClient.getApiService());
        citaRepository = new CitaRepository(database.citaDao(), RetrofitClient.getApiService());

        resenaRepository = new ResenaRepository(database.resenaDao(), RetrofitClient.getApiService());
        reciboRepository = new ReciboRepository(database.reciboDao(), RetrofitClient.getApiService());
        rolRepository = new RolRepository(database.rolDao(),RetrofitClient.getApiService());
        servicioRepository = new ServicioRepository(database.servicioDao(),RetrofitClient.getApiService());

        especialidadRepository = new EspecialidadRepository(database.especialidadDao(), RetrofitClient.getApiService());
        historialClinicoRepository = new HistorialClinicoRepository(database.historialClinicoDao(), RetrofitClient.getApiService());
        horarioDisponibleRepository = new HorarioDisponibleRepository(database.horarioDisponibleDao(), RetrofitClient.getApiService());

        // Programar sincronización periódica con WorkManager para cada worker
        scheduleSyncWorkers();
        llamarInmediatamente();
    }

    public void llamarInmediatamente() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest rolSync = new OneTimeWorkRequest.Builder(RolSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest especialidadSync = new OneTimeWorkRequest.Builder(EspecialidadSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest servicioSync = new OneTimeWorkRequest.Builder(ServicioSyncWorker.class).setConstraints(constraints).build();

        OneTimeWorkRequest usuarioSync = new OneTimeWorkRequest.Builder(UsuarioSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest doctorSync = new OneTimeWorkRequest.Builder(DoctorSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest citaSync = new OneTimeWorkRequest.Builder(CitaSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest resenaSync = new OneTimeWorkRequest.Builder(ResenaSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest reciboSync = new OneTimeWorkRequest.Builder(ReciboSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest historialSync = new OneTimeWorkRequest.Builder(HistorialClinicoSyncWorker.class).setConstraints(constraints).build();
        OneTimeWorkRequest horarioSync = new OneTimeWorkRequest.Builder(HorarioDisponibleSyncWorker.class).setConstraints(constraints).build();

        WorkManager workManager = WorkManager.getInstance(this);

        workManager
                .beginWith(rolSync)
                .then(usuarioSync)
                .then(especialidadSync)
                .then(servicioSync)

                .then(doctorSync)
                .then(citaSync)
                .then(resenaSync)
                .then(reciboSync)
                .then(historialSync)
                .then(horarioSync)
                .enqueue();
    }



    private void scheduleSyncWorkers() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED) // sincroniza sólo con internet
                .build();
        int intervalo = 1;

        // Ejemplo para UsuarioSyncWorker - repite para todos tus workers
        PeriodicWorkRequest usuarioWorkRequest = new PeriodicWorkRequest.Builder(
                UsuarioSyncWorker.class,
                intervalo, TimeUnit.HOURS)  // cada 6 horas, ajusta según tu necesidad
                .setConstraints(constraints)
                .build();

        // Similar para DoctorSyncWorker
        PeriodicWorkRequest doctorWorkRequest = new PeriodicWorkRequest.Builder(
                DoctorSyncWorker.class,
                intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Cita
        PeriodicWorkRequest citaWorkRequest = new PeriodicWorkRequest.Builder(
                CitaSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Resena
        PeriodicWorkRequest resenaWorkRequest = new PeriodicWorkRequest.Builder(
                ResenaSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Recibo
        PeriodicWorkRequest reciboWorkRequest = new PeriodicWorkRequest.Builder(
                ReciboSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Rol
        PeriodicWorkRequest rolWorkRequest = new PeriodicWorkRequest.Builder(
                RolSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Servicio
        PeriodicWorkRequest servicioWorkRequest = new PeriodicWorkRequest.Builder(
                ServicioSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Especialidad
        PeriodicWorkRequest especialidadWorkRequest = new PeriodicWorkRequest.Builder(
                EspecialidadSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Historial Clínico
        PeriodicWorkRequest historialWorkRequest = new PeriodicWorkRequest.Builder(
                HistorialClinicoSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Horario Disponible
        PeriodicWorkRequest horarioWorkRequest = new PeriodicWorkRequest.Builder(
                HorarioDisponibleSyncWorker.class, intervalo, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        // Encolar todos los workers
        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueueUniquePeriodicWork("UsuarioSync", ExistingPeriodicWorkPolicy.KEEP, usuarioWorkRequest);
        workManager.enqueueUniquePeriodicWork("DoctorSync", ExistingPeriodicWorkPolicy.KEEP, doctorWorkRequest);
        workManager.enqueueUniquePeriodicWork("CitaSync", ExistingPeriodicWorkPolicy.KEEP, citaWorkRequest);
        workManager.enqueueUniquePeriodicWork("ResenaSync", ExistingPeriodicWorkPolicy.KEEP, resenaWorkRequest);
        workManager.enqueueUniquePeriodicWork("ReciboSync", ExistingPeriodicWorkPolicy.KEEP, reciboWorkRequest);
        workManager.enqueueUniquePeriodicWork("RolSync", ExistingPeriodicWorkPolicy.KEEP, rolWorkRequest);
        workManager.enqueueUniquePeriodicWork("ServicioSync", ExistingPeriodicWorkPolicy.KEEP, servicioWorkRequest);
        workManager.enqueueUniquePeriodicWork("EspecialidadSync", ExistingPeriodicWorkPolicy.KEEP, especialidadWorkRequest);
        workManager.enqueueUniquePeriodicWork("HistorialSync", ExistingPeriodicWorkPolicy.KEEP, historialWorkRequest);
        workManager.enqueueUniquePeriodicWork("HorarioSync", ExistingPeriodicWorkPolicy.KEEP, horarioWorkRequest);




        

    }

    public static MyApplication getInstance() {
        return instance;
    }

    // Getters para repositorios si quieres acceder desde otras partes
    public UsuarioRepository getUsuarioRepository() {
        return usuarioRepository;
    }
    public DoctorRepository getDoctorRepository() {
        return doctorRepository;
    }
    public CitaRepository getCitaRepository() {
        return citaRepository;
    }
    public ResenaRepository getResenaRepository() {
        return resenaRepository;
    }
    public ReciboRepository getReciboRepository() {
        return reciboRepository;
    }
    public RolRepository getRolRepository() {
        return rolRepository;
    }
    public ServicioRepository getServicioRepository() {
        return servicioRepository;
    }
    public EspecialidadRepository getEspecialidadRepository() {
        return especialidadRepository;
    }
    public HistorialClinicoRepository getHistorialClinicoRepository() {
        return historialClinicoRepository;
    }
    public HorarioDisponibleRepository getHorarioDisponibleRepository() {
        return horarioDisponibleRepository;
    }




}
