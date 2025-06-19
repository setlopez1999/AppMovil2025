package com.example.sonrisasaludable.data.models.factory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sonrisasaludable.data.dao.CitaDao;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.models.viewmodels.CitasViewModel;
import com.example.sonrisasaludable.data.network.ApiService;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.repository.CitaRepository;

public class CitasViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public CitasViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CitasViewModel.class)) {
            AppDatabase db = AppDatabase.getInstance(context);
            CitaDao citaDao = db.citaDao();
            ApiService apiService = RetrofitClient.getApiService();
            CitaRepository repository = new CitaRepository(citaDao, apiService);
            return (T) new CitasViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
