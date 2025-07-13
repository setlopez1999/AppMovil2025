package com.example.sonrisasaludable.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.services.ResenaService;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.models.adapters.ResenasAdapter;
import com.example.sonrisasaludable.utilidades.SessionManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DPerfilFragment extends Fragment {
    private TextView tvNombreDoctor, tvEspecialidad, tvPromedioCalificacion;
    private RecyclerView recyclerResenas;
    private Button btnVerTodasResenas;
    private ResenaService resenaService;
    private ResenasAdapter resenasAdapter;
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
        recyclerResenas = view.findViewById(R.id.recyclerResenas);
        btnVerTodasResenas = view.findViewById(R.id.btnVerTodasResenas);
        
        // Configurar RecyclerView
        recyclerResenas.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerResenas.setNestedScrollingEnabled(false);
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
                var usuario = db.usuarioDao().getById(doctor.getUsuario_id());
                if (usuario != null) {
                    getActivity().runOnUiThread(() -> {
                        tvNombreDoctor.setText(usuario.getNombres() + " " + usuario.getApellidos());
                        tvEspecialidad.setText("Especialidad ID: " + doctor.getEspecialidad_id());
                    });
                }
            }
        });
    }

    private void loadResenas() {
        executor.execute(() -> {
            var stats = resenaService.getEstadisticasDoctor(doctorId);
            var resenas = resenaService.getResenasByDoctor(doctorId);
            
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    tvPromedioCalificacion.setText(String.format("%.1f", stats.promedio));
                    btnVerTodasResenas.setText("Ver Todas las Reseñas (" + stats.totalResenas + ")");
                    
                    // Configurar adapter con las reseñas reales (sin botones de editar/eliminar)
                    if (!resenas.isEmpty()) {
                        resenasAdapter = new ResenasAdapter(resenas, null, null);
                        recyclerResenas.setAdapter(resenasAdapter);
                        // Toast.makeText(getContext(), resenas.size() + " reseñas cargadas", Toast.LENGTH_SHORT).show();
                    } else {
                        // Toast.makeText(getContext(), "No hay reseñas aún", Toast.LENGTH_SHORT).show();
                    }
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