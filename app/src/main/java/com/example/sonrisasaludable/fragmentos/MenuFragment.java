package com.example.sonrisasaludable.fragmentos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.MapaActivity;
import com.example.sonrisasaludable.utilidades.SessionManager;

public class MenuFragment extends Fragment {
    private SessionManager sessionManager;
    private TextView bienvenida;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Inicializar SessionManager
        sessionManager = SessionManager.getInstance(getContext());

        bienvenida = view.findViewById(R.id.tvBienvenida) ;
        bienvenida.setText(sessionManager.getUserName());


        // Referencias a las sedes
        View sede1 = view.findViewById(R.id.sede1);
        View sede2 = view.findViewById(R.id.sede2);
        View sede3 = view.findViewById(R.id.sede3);

        sede1.setOnClickListener(v -> abrirMapa("Sede Central", "Av. Principal 123 - Centro"));
        sede2.setOnClickListener(v -> abrirMapa("Sede Norte", "Calle Secundaria 456 - Norte"));
        sede3.setOnClickListener(v -> abrirMapa("Sede Sur", "Av. Del Sol 789 - Sur"));

        return view;
    }

    private void abrirMapa(String nombreSede, String direccion) {
        Intent intent = new Intent(requireContext(), MapaActivity.class);
        intent.putExtra("nombreSede", nombreSede);
        intent.putExtra("direccion", direccion);
        startActivity(intent);
    }
}
