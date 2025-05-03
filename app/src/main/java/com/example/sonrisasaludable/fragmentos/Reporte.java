package com.example.sonrisasaludable.fragmentos;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sonrisasaludable.R;

public class Reporte extends Fragment {

    private int pasoActual = 0; // 0: Calendario, 1: Horario, 2: Tratamiento, 3: Confirmar, 4: Exitosa
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

        // Mostrar primer fragmento (Calendario)
        getChildFragmentManager().beginTransaction()
                .replace(R.id.contenedorInterno, new CalendarioFragment())
                .commit();

        btnSiguiente.setOnClickListener(v -> {
            pasoActual++;

            switch (pasoActual) {
                case 1:
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.contenedorInterno, new FechaFragment())
                            .commit();
                    btnSiguiente.setText("Siguiente");
                    break;

                case 2:
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.contenedorInterno, new TratamientoFragment())
                            .commit();
                    btnSiguiente.setText("Siguiente");
                    break;

                case 3:
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.contenedorInterno, new ConfirmacionCitaFragment())
                            .commit();
                    btnSiguiente.setText("Confirmar");
                    break;

                case 4:
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.contenedorInterno, new CitaExitosaFragment())
                            .commit();
                    btnSiguiente.setText("Finalizar");
                    break;

                default:
                    // Aquí finaliza → podrías cerrar o volver al inicio
                    getActivity().onBackPressed(); // O navegar a inicio directamente
                    break;
            }

            actualizarProgreso();
        });

        btnRegresarCita.setOnClickListener(v -> {
            if (pasoActual > 0) {
                pasoActual--;

                switch (pasoActual) {
                    case 0:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.contenedorInterno, new CalendarioFragment())
                                .commit();
                        btnSiguiente.setText("Siguiente");
                        break;

                    case 1:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.contenedorInterno, new FechaFragment())
                                .commit();
                        btnSiguiente.setText("Siguiente");
                        break;

                    case 2:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.contenedorInterno, new TratamientoFragment())
                                .commit();
                        btnSiguiente.setText("Siguiente");
                        break;

                    case 3:
                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.contenedorInterno, new ConfirmacionCitaFragment())
                                .commit();
                        btnSiguiente.setText("Confirmar");
                        break;
                }

                actualizarProgreso();
            }
        });

        return view;
    }

    private void actualizarProgreso() {
        int progreso;

        switch (pasoActual) {
            case 0:
                progreso = 0;
                break;
            case 1:
                progreso = 25;
                break;
            case 2:
                progreso = 50;
                break;
            case 3:
                progreso = 75;
                break;
            case 4:
                progreso = 100;
                break;
            default:
                progreso = 0;
                break;
        }

        if (progressBar != null) {
            ObjectAnimator anim = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progreso);
            anim.setDuration(500);
            anim.start();
        }
    }
}
