package com.example.limasegura.fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.animation.ObjectAnimator;

import com.example.limasegura.R;

public class Reporte extends Fragment {

    private int pasoActual = 0; // 0: Calendario, 1: Horario, 2: Tratamiento...
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reporte, container, false);

        Button btnSiguiente = view.findViewById(R.id.btnSiguiente);
        ImageButton btnRegresarCita = view.findViewById(R.id.btnRegresarCita);
        progressBar = view.findViewById(R.id.progressBar);


        actualizarProgreso();
        // Mostrar primer fragmento
        getChildFragmentManager().beginTransaction()
                .replace(R.id.contenedorInterno, new CalendarioFragment())
                .commit();

        btnSiguiente.setOnClickListener(v -> {
            pasoActual++;

            if (pasoActual == 1) {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.contenedorInterno, new FechaFragment())
                        .commit();
            } else if (pasoActual == 2) {
                getChildFragmentManager().beginTransaction()
                        .replace(R.id.contenedorInterno, new TratamientoFragment())
                        .commit();
                btnSiguiente.setText("Finalizar");
            } else if (pasoActual == 3) {
                Toast.makeText(getContext(), "Cita agendada con éxito", Toast.LENGTH_SHORT).show();
                // Reiniciar si se desea repetir el flujo
                pasoActual = 2; // o 0 si deseas volver al inicio
            }
            actualizarProgreso();
        });

        btnRegresarCita.setOnClickListener(v -> {
            if (pasoActual > 0) {
                pasoActual--;

                if (pasoActual == 0) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.contenedorInterno, new CalendarioFragment())
                            .commit();
                    btnSiguiente.setText("Siguiente");
                } else if (pasoActual == 1) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.contenedorInterno, new FechaFragment())
                            .commit();
                    btnSiguiente.setText("Siguiente");
                }
                actualizarProgreso();
            }
        });

        return view;
    }
    // Actualiza el progreso de progreesBar
    private void actualizarProgreso() {
        int progreso;

        switch (pasoActual) {
            case 0:
                progreso = 0;
                break;
            case 1:
                progreso = 33;
                break;
            case 2:
                progreso = 66; //pantalla tratamiento
                break;
            default:
                progreso = 0;
                break;
        }
        if (progressBar != null) {
            ObjectAnimator anim = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progreso);
            anim.setDuration(500); // duración de la animación en milisegundos
            anim.start();

        }

    }
}
