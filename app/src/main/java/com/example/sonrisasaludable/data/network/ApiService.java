package com.example.sonrisasaludable.data.network;

import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import com.example.sonrisasaludable.data.entidades.ResenaEntity;
import com.example.sonrisasaludable.data.entidades.DoctorEntity;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.data.entidades.ReciboEntity;
import com.example.sonrisasaludable.data.entidades.ServicioEntity;
import com.example.sonrisasaludable.data.entidades.EspecialidadEntity;
import com.example.sonrisasaludable.data.entidades.HorarioDisponibleEntity;
import com.example.sonrisasaludable.data.entidades.HistorialClinicoEntity;
import com.example.sonrisasaludable.data.entidades.RolEntity;
import com.example.sonrisasaludable.data.models.LoginRequest;
import com.example.sonrisasaludable.data.models.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface ApiService {

    @GET("usuarios")
    Call<List<UsuarioEntity>> getUsuarios();

    @GET("resenas")
    Call<List<ResenaEntity>> getResenas();

    @GET("doctores")
    Call<List<DoctorEntity>> getDoctores();

    @GET("citas")
    Call<List<CitaEntity>> getCitas();

    @GET("recibos")
    Call<List<ReciboEntity>> getRecibos();

    @GET("servicios")
    Call<List<ServicioEntity>> getServicios();

    @GET("especialidades")
    Call<List<EspecialidadEntity>> getEspecialidades();

    @GET("horarios-disponibles")
    Call<List<HorarioDisponibleEntity>> getHorariosDisponibles();

    @GET("historial-clinico")
    Call<List<HistorialClinicoEntity>> getHistorialClinico();

    @GET("roles")
    Call<List<RolEntity>> getRoles();

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);


}