package com.example.sonrisasaludable.fragmentos;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.DetalleCitaActivity;
import com.example.sonrisasaludable.data.models.CitaConDetalles;
import com.example.sonrisasaludable.data.models.factory.CitasViewModelFactory;
import com.example.sonrisasaludable.data.models.adapters.CitasAdapter;
import com.example.sonrisasaludable.data.models.viewmodels.CitasViewModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DHomeFragment extends Fragment {
    //variables privaditas

    private RecyclerView recicler;
    private CitasAdapter adaptador;
    private CitasViewModel model;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_d_home, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().getWindow().getDecorView()
                    .setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
        }
        recicler = root.findViewById(R.id.recyclerCitas);
        recicler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recicler.setHasFixedSize(true);

        // Crear un adaptador vacío para evitar "No adapter attached"
        adaptador = new CitasAdapter(requireContext(), new ArrayList<>(), cita -> {
            Intent intent = new Intent(requireContext(), DetalleCitaActivity.class);
            intent.putExtra("cita", cita);
            startActivity(intent);
        });
        recicler.setAdapter(adaptador);

        model = new ViewModelProvider(
                this,
                new CitasViewModelFactory(requireContext())
        ).get(CitasViewModel.class);





        model.getCitas().observe(getViewLifecycleOwner(), citas -> {
            if (citas != null && !citas.isEmpty()) {
                //mostrar("Citas encontradas: " + citas.size());
                adaptador.actualizarCitas(citas);
                actualizarContadores(citas, root);
            } else {
                //mostrar("No hay citas para mostrar");
                adaptador.actualizarCitas(Collections.emptyList());
            }
        });

        return root;
    }

    private void actualizarContadores(List<CitaConDetalles> citas, View root) {
        //buscamos los textview para actualizar
        TextView citas_confirmados = root.findViewById(R.id.d_home_confirmados);
        TextView citas_pednientes = root.findViewById(R.id.d_home_pendientes);
        TextView citas_completadas = root.findViewById(R.id.d_home_completadas);

        int confirmadas = 0,pendientes=0,completadas = 0;

        if (citas != null) {
            for (CitaConDetalles cita : citas) {
                // 2 maneras
                //hoy++;
                //if (esHoy(cita.getFecha())) hoy++;
                //usaremos esta
                if ("Confirmada".equalsIgnoreCase(cita.getEstado())) confirmadas++;
                if ("Pendiente".equalsIgnoreCase(cita.getEstado())) pendientes++;
                if ("Completada".equalsIgnoreCase(cita.getEstado())) completadas++;
            }
        }

        //actualizamos
        citas_confirmados.setText(String.valueOf(confirmadas));
        citas_pednientes.setText(String.valueOf(pendientes));
        citas_completadas.setText(String.valueOf(completadas));
    }


    // abocadoooo digo !!  deprecado :)
    private boolean esHoy(String fecha) {
        //por implementar
        return true;
    }

    public void mostrar(String mensaje){
        Toast.makeText(getContext(),mensaje,Toast.LENGTH_SHORT).show();
    }
}