package com.example.sonrisasaludable.data.models.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.sonrisasaludable.data.models.DoctorConUsuario;
import com.example.sonrisasaludable.data.repository.DoctorRepository;
import java.util.List;

public class DoctorViewModel extends ViewModel {

    private final DoctorRepository doctorRepository;
    private final LiveData<List<DoctorConUsuario>> doctoresConUsuario;

    public DoctorViewModel(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
        this.doctoresConUsuario = doctorRepository.getDoctoresConUsuario();
    }

    public LiveData<List<DoctorConUsuario>> getDoctoresConUsuario() {
        return doctoresConUsuario;
    }

}
