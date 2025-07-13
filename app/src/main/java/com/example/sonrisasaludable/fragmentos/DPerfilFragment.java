package com.example.sonrisasaludable.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.services.ResenaService;
import com.example.sonrisasaludable.utilidades.SessionManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DPerfilFragment extends Fragment {
    private TextView tvNombreDoctor, tvEspecialidad, tvPromedioCalificacion;
    private LinearLayout resenasContainer;
    private Button btnVerTodasResenas;
    private ResenaService resenaService;
    private ExecutorService executor;
    private int doctorId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_perfil, container, false);
        
        initViews(view);
        initServices();
        loadDoctorData();
        loadResenas();
        
        return view;
    }

    private void initViews(View view) {
        tvNombreDoctor = view.findViewById(R.id.tvNombreDoctor);
        tvEspecialidad = view.findViewById(R.id.tvEspecialidad);
        tvPromedioCalificacion = view.findViewById(R.id.tvPromedioCalificacion);
        btnVerTodasResenas = view.findViewById(R.id.btnVerTodasResenas);
    }

    private void initServices() {
        AppDatabase db = AppDatabase.getInstance(getContext());
        resenaService = new ResenaService(db.resenaDao(), db.citaDao());
        executor = Executors.newSingleThreadExecutor();
        
        SessionManager session = SessionManager.getInstance(getContext());
        doctorId = session.getDoctorId();
    }

    private void loadDoctorData() {
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(getContext());
            var doctor = db.doctorDao().getById(doctorId);
            
            if (doctor != null && getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    tvNombreDoctor.setText(doctor.getNombres() + " " + doctor.getApellidos());
                    tvEspecialidad.setText("Especialidad ID: " + doctor.getEspecialidadId());
                });
            }
        });
    }

    private void loadResenas() {
        executor.execute(() -> {
            var stats = resenaService.getEstadisticasDoctor(doctorId);
            
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    tvPromedioCalificacion.setText(String.format("%.1f", stats.promedio));
                    btnVerTodasResenas.setText("Ver Todas las Reseñas (" + stats.totalResenas + ")");
                });
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }
}