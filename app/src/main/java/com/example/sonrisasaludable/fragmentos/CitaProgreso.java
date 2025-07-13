package com.example.sonrisasaludable.fragmentos;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sonrisasaludable.R;

public class CitaProgreso extends Fragment {

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cita, container, false);

        progressBar = view.findViewById(R.id.progressBar);

        actualizarProgreso(100); // Ya no hay pasos, progreso completo

        // Inflar el formulario y agregarlo al contenedorInterno
        FrameLayout contenedor = view.findViewById(R.id.contenedorInterno);

        View formulario = inflater.inflate(R.layout.formulario_cita, contenedor, false);

        contenedor.addView(formulario);

        return view;
    }

    private void actualizarProgreso(int progreso) {
        if (progressBar != null) {
            ObjectAnimator anim = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), progreso);
            anim.setDuration(500);
            anim.start();
        }
    }
}