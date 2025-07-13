package com.example.sonrisasaludable.fragmentos;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.models.UsuarioResponse;
import com.example.sonrisasaludable.interfaces.OnPerfilActionsListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilPaciente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilPaciente extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnPerfilActionsListener listener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PerfilPaciente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PerfilPaciente.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilPaciente newInstance(String param1, String param2) {
        PerfilPaciente fragment = new PerfilPaciente();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Fragment parentFragment = getParentFragment(); // importante para fragments hijos

        if (parentFragment instanceof OnPerfilActionsListener) {
            listener = (OnPerfilActionsListener) parentFragment;
        } else {
            throw new ClassCastException(parentFragment + " debe implementar OnPerfilActionsListener");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_paciente, container, false);

        Button btnEditar = view.findViewById(R.id.btnEditarPerfilP);
        btnEditar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEditarPerfil();
            }
        });

        // Acceder al padre (ConfiguracionFragment)
        Fragment parentFragment = getParentFragment();
        if (parentFragment instanceof ConfiguracionFragment) {
            UsuarioResponse usuario = ((ConfiguracionFragment) parentFragment).getUsuarioActual();
            if (usuario != null) {
                // Referencias a los componentes
                TextView tvNombre = view.findViewById(R.id.tvNombreUsuario);
                TextView tvCorreo = view.findViewById(R.id.tvCorreoUsuario);
                TextView tvTelefono = view.findViewById(R.id.tvTelefonoUsuario);
                ImageView ivFoto = view.findViewById(R.id.ivFotoPaciente);

                // Asignar datos
                tvNombre.setText(usuario.getNombres() + " " + usuario.getApellidos());
                tvCorreo.setText(usuario.getCorreo());
                tvTelefono.setText(usuario.getTelefono());

                if (usuario.getFoto_perfil() != null && !usuario.getFoto_perfil().isEmpty()) {
                    //String imagenConTimestamp = usuario.getFoto_perfil() + "?t=" + System.currentTimeMillis();
                    Glide.with(this).load(usuario.getFoto_perfil()).into(ivFoto);
                }
            }
        }

        return view;
    }
}