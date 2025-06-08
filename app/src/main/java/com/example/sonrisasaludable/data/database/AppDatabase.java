package com.example.sonrisasaludable.data.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.sonrisasaludable.data.dao.UsuarioDao;
import com.example.sonrisasaludable.data.dao.ResenaDao;
import com.example.sonrisasaludable.data.dao.CitaDao;
import com.example.sonrisasaludable.data.dao.DoctorDao;
import com.example.sonrisasaludable.data.dao.RolDao;
import com.example.sonrisasaludable.data.dao.EspecialidadDao;
import com.example.sonrisasaludable.data.dao.HorarioDisponibleDao;
import com.example.sonrisasaludable.data.dao.ReciboDao;
import com.example.sonrisasaludable.data.dao.ServicioDao;
import com.example.sonrisasaludable.data.dao.HistorialClinicoDao;

import com.example.sonrisasaludable.data.entidades.UsuarioEntity;
import com.example.sonrisasaludable.data.entidades.ResenaEntity;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.data.entidades.DoctorEntity;
import com.example.sonrisasaludable.data.entidades.RolEntity;
import com.example.sonrisasaludable.data.entidades.EspecialidadEntity;
import com.example.sonrisasaludable.data.entidades.HorarioDisponibleEntity;
import com.example.sonrisasaludable.data.entidades.ReciboEntity;
import com.example.sonrisasaludable.data.entidades.ServicioEntity;
import com.example.sonrisasaludable.data.entidades.HistorialClinicoEntity;

@Database(
        entities = {
                UsuarioEntity.class,
                ResenaEntity.class,
                CitaEntity.class,
                DoctorEntity.class,
                RolEntity.class,
                EspecialidadEntity.class,
                HorarioDisponibleEntity.class,
                ReciboEntity.class,
                ServicioEntity.class,
                HistorialClinicoEntity.class
        },
        version = 2,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract UsuarioDao usuarioDao();
    public abstract ResenaDao resenaDao();
    public abstract CitaDao citaDao();
    public abstract DoctorDao doctorDao();
    public abstract RolDao rolDao();
    public abstract EspecialidadDao especialidadDao();
    public abstract HorarioDisponibleDao horarioDisponibleDao();
    public abstract ReciboDao reciboDao();
    public abstract ServicioDao servicioDao();
    public abstract HistorialClinicoDao historialClinicoDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "app_database"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
