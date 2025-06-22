package com.example.sonrisasaludable.fragmentos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.utilidades.SessionManager;

public class DPerfilFragment extends Fragment {

    private SessionManager sessionManager;

    private TextView tvNombreDoctor, tvEspecialidad, tvCedula, tvEmail, tvTelefono;
    private ImageView ivFotoDoctor;


    public DPerfilFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout
        View view = inflater.inflate(R.layout.fragment_d_perfil, container, false);

        // Inicializar SessionManager
        sessionManager = SessionManager.getInstance(getContext());

        // Vincular vistas
        ivFotoDoctor = view.findViewById(R.id.ivFotoDoctor);
        tvNombreDoctor = view.findViewById(R.id.tvNombreDoctor);
        tvEspecialidad = view.findViewById(R.id.tvEspecialidad);
        tvCedula = view.findViewById(R.id.tvCedulaProfesional);
        tvEmail = view.findViewById(R.id.tvEmailDoctor);
        tvTelefono = view.findViewById(R.id.tvTelefonoDoctor);

        // Cargar datos del doctor desde SessionManager
        cargarDatosDoctor();

        return view;
    }

    private void cargarDatosDoctor() {
        tvNombreDoctor.setText(sessionManager.getUserName());
        tvEspecialidad.setText(sessionManager.getRole());
        tvCedula.setText(sessionManager.getToken());
        tvEmail.setText(sessionManager.getEmail());
        tvTelefono.setText(sessionManager.getEmail());
        // Cargar imagen de perfil con Glide
        String fotoUrl = sessionManager.getPhotoUrl();
        if (fotoUrl != null && !fotoUrl.isEmpty()) {
            Glide.with(this)
                    .load(fotoUrl)
                    .placeholder(R.drawable.doctor1_default) // imagen por defecto
                    .error(R.drawable.doctor1_default) // si falla la carga
                    .circleCrop() // redondea la imagen
                    .into(ivFotoDoctor);
        }
    }
}
