package com.example.sonrisasaludable.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.DetalleDentistaActivity;
import com.example.sonrisasaludable.data.models.adapters.DoctorConUsuarioAdapter;
import com.example.sonrisasaludable.data.models.viewmodels.DoctorViewModel;
import com.example.sonrisasaludable.data.models.viewmodels.DoctorViewModelFactory;


public class DentistasFragment extends Fragment {

    private RecyclerView recyclerView;
    private DoctorConUsuarioAdapter adapter;
    private DoctorViewModel doctorViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dentistas, container, false);

        recyclerView = root.findViewById(R.id.recyclerDoctores);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setHasFixedSize(true);


        doctorViewModel = new ViewModelProvider(
                this,
                new DoctorViewModelFactory(requireContext())
        ).get(DoctorViewModel.class);

        // Observar la lista combinada de Doctor + Usuario
        doctorViewModel.getDoctoresConUsuario().observe(getViewLifecycleOwner(), doctores -> {
            if (doctores != null && !doctores.isEmpty()) {
                adapter = new DoctorConUsuarioAdapter(requireContext(), doctores, doctor -> {

                    // ESTO abre a la nueva actividad detalle dentista
                    Intent intent = new Intent(requireContext(), DetalleDentistaActivity.class);
                    intent.putExtra("doctor", doctor); // Asegúrate que DoctorConUsuario implementa Serializable
                    startActivity(intent);
                });

                recyclerView.setAdapter(adapter);
            }
        });

        return root;
    }
}
