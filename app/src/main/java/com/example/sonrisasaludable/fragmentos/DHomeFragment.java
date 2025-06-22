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
import android.widget.Toast;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.DetalleCitaActivity;
import com.example.sonrisasaludable.actividades.DetalleDentistaActivity;
import com.example.sonrisasaludable.data.models.factory.CitasViewModelFactory;
import com.example.sonrisasaludable.data.models.adapters.CitasAdapter;
import com.example.sonrisasaludable.data.models.viewmodels.CitasViewModel;


public class DHomeFragment extends Fragment {
    //variables privaditas
    private RecyclerView recicler;
    private CitasAdapter adaptador;
    private CitasViewModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //inflamos la vista que regresaremos
        View root = inflater.inflate(R.layout.fragment_d_home, container, false);
        //instanciamos el recicler con el id
        recicler = root.findViewById(R.id.recyclerCitas);
        //le ponemos que el recicler sea lineal vertical
        recicler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recicler.setHasFixedSize(true);

        model = new ViewModelProvider(
                this,
                new CitasViewModelFactory(requireContext())
        ).get(CitasViewModel.class);

        //model.citasdeprueba();
        //aplicamos el observador de datos  y su listener
        model.getCitas().observe(getViewLifecycleOwner(), citas -> {
            if (citas != null && !citas.isEmpty()) {
                mostrar("Si");
                adaptador = new CitasAdapter(requireContext(), citas, cita -> {
                    // ESTO abre a la nueva actividad detalle citas
                    Intent intent = new Intent(requireContext(), DetalleCitaActivity.class);
                    intent.putExtra("cita", cita);
                    startActivity(intent);
                });

                recicler.setAdapter(adaptador);
            }else{
                mostrar("no");
            }
        });


        return root;
    }
    public void mostrar(String mensaje){
        Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
    }
}