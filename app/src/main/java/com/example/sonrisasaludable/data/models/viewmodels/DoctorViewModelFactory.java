package com.example.sonrisasaludable.data.models.viewmodels;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sonrisasaludable.data.network.ApiService;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.dao.DoctorDao;
import com.example.sonrisasaludable.data.repository.DoctorRepository;

public class DoctorViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public DoctorViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DoctorViewModel.class)) {
            AppDatabase db = AppDatabase.getInstance(context);
            DoctorDao doctorDao = db.doctorDao();
            ApiService apiService = RetrofitClient.getApiService();
            DoctorRepository repository = new DoctorRepository(doctorDao, apiService);
            return (T) new DoctorViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
