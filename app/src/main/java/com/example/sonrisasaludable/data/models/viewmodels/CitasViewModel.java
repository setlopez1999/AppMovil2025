package com.example.sonrisasaludable.data.models.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.sonrisasaludable.data.entidades.*;
import com.example.sonrisasaludable.data.models.CitaConDetalles;
import com.example.sonrisasaludable.data.models.DoctorConUsuario;
import com.example.sonrisasaludable.data.repository.CitaRepository;

import java.util.List;

public class CitasViewModel extends ViewModel {

    private final CitaRepository citaRepository;

    private final LiveData<List<CitaConDetalles>> citas;
    private final LiveData<List<UsuarioEntity>> usuarios;
    private final LiveData<List<DoctorConUsuario>> doctoresConUsuario; // 👈 aquí
    private final LiveData<List<ServicioEntity>> servicios;
    private final LiveData<List<SedeEntity>> sedes;

    public CitasViewModel(
            CitaRepository repository,
            LiveData<List<UsuarioEntity>> usuarios,
            LiveData<List<DoctorConUsuario>> doctoresConUsuario, // 👈 aquí
            LiveData<List<ServicioEntity>> servicios,
            LiveData<List<SedeEntity>> sedes
    ) {
        this.citaRepository = repository;

        this.citas = citaRepository.getCitaConDetalles();
        this.usuarios = usuarios;
        this.doctoresConUsuario = doctoresConUsuario;
        this.servicios = servicios;
        this.sedes = sedes;
    }

    // --- PARA LISTAR ---
    public LiveData<List<CitaConDetalles>> getCitas() {
        return citas;
    }

    public LiveData<List<UsuarioEntity>> getUsuarios() {
        return usuarios;
    }

    public LiveData<List<DoctorConUsuario>> getDoctoresConUsuario() {
        return doctoresConUsuario;
    }

    public LiveData<List<ServicioEntity>> getServicios() {
        return servicios;
    }

    public LiveData<List<SedeEntity>> getSedes() {
        return sedes;
    }

    // --- PARA INSERTAR ---
    public void insertarCita(CitaEntity cita) {
        citaRepository.insertCita(cita);
    }

    public LiveData<Boolean> getSyncingStatus() {
        return citaRepository.getSyncingStatus();
    }

    public void sincronizarCitasDesdeApi() {
        citaRepository.sincronizarCitasDesdeApi();
    }

    public void insertarCitaConSincronizacion(CitaEntity cita, Context context) {
        citaRepository.insertarCitaConSincronizacion(cita,context);
    }
}
