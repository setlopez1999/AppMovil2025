package com.example.sonrisasaludable.data.models.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.data.models.CitaConDetalles;
import com.example.sonrisasaludable.data.repository.CitaRepository;
import com.example.sonrisasaludable.data.repository.ServicioRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CitasViewModel extends ViewModel {
    private CitaRepository citasRepository;
    private LiveData<List<CitaConDetalles>> citas;

    //temporal
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private ServicioRepository servicioRepository;
    public CitasViewModel(CitaRepository citasRepository) {
        this.citasRepository = citasRepository;
        this.citas = citasRepository.getCitaConDetalles();
    }

    public LiveData<List<CitaConDetalles>> getCitas() {
        return citas;
    }

    public void citasdeprueba() {
        executor.execute(() -> {
            // Insertar citas
            List<CitaEntity> prueba = new ArrayList<>();
            prueba.add(new CitaEntity(
                    1, 2, 3, 1,
                    "2025-06-20", "10:00", "pendiente",
                    "Juan", "XD"
            ));

            prueba.add(new CitaEntity(
                    2, 3, 4, 2,
                    "2025-06-22", "15:30", "confirmada",
                    "Juan", "XD"
            ));

            prueba.add(new CitaEntity(
                    3, 4, 2, 3,
                    "2025-06-25", "09:00", "cancelada",
                    "Juan", "XD"
            ));

            citasRepository.insertAllCitas(prueba);
        });
    }

}
