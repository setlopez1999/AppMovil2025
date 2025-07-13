package com.example.sonrisasaludable.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.DetalleCitaActivity;
import com.example.sonrisasaludable.actividades.ReciboActivity;
import com.example.sonrisasaludable.data.models.adapters.CitasAdapter;
import com.example.sonrisasaludable.data.models.factory.CitasViewModelFactory;
import com.example.sonrisasaludable.data.models.viewmodels.CitasViewModel;
import com.example.sonrisasaludable.utilidades.SessionManager;

import java.util.ArrayList;


public class HistorialFragment extends Fragment {

    private CitasViewModel viewModel;
    private RecyclerView recyclerView;
    private CitasAdapter adapter;


    public HistorialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar la vista del fragmento
        View vista = inflater.inflate(R.layout.fragment_historial, container, false);




        recyclerView = vista.findViewById(R.id.recyclerCitas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CitasAdapter(
                requireContext(),
                new ArrayList<>(),
                cita -> {
                    Intent intent = new Intent(getActivity(), ReciboActivity.class);
                    intent.putExtra("cita_id", cita.getId());
                    startActivity(intent);
                }
        );

        recyclerView.setAdapter(adapter);


        CitasViewModelFactory factory = new CitasViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(requireActivity(),factory).get(CitasViewModel.class);

        int userId = SessionManager.getInstance(requireContext()).getUserId();

        viewModel.getCitasDeUsuario(userId).observe(getViewLifecycleOwner(), citas -> {
            adapter.actualizarCitas(citas);
        });

        return vista;
    }
}