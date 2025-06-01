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
import com.example.sonrisasaludable.data.models.UsuarioResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Part;

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

    @Multipart
    @POST("auth/register")
    Call<UsuarioResponse> registrarUsuarioConImagen(
            @Part("dni") RequestBody dni,
            @Part("nombres") RequestBody nombres,
            @Part("apellidos") RequestBody apellidos,
            @Part("correo") RequestBody correo,
            @Part("clave") RequestBody clave,
            @Part("telefono") RequestBody telefono,
            @Part("direccion") RequestBody direccion,
            @Part("fechaNacimiento") RequestBody fechaNacimiento,
            @Part("sexo") RequestBody sexo,
            @Part MultipartBody.Part imagen
    );

}