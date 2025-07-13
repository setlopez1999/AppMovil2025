package com.example.sonrisasaludable.fragmentos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.interfaces.OnEditarPerfilDoctorListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPerfilDoctor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPerfilDoctor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditPerfilDoctor() {
        // Required empty public constructor
    }
    private OnEditarPerfilDoctorListener listener;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPerfilDoctor.
     */
    // TODO: Rename and change types and number of parameters

    Button btnCancelar ;
    public static EditPerfilDoctor newInstance(String param1, String param2) {
        EditPerfilDoctor fragment = new EditPerfilDoctor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnEditarPerfilDoctorListener) {
            listener = (OnEditarPerfilDoctorListener) context;
        } else {
            throw new RuntimeException(context.toString() + " debe implementar OnEditarPerfilDoctorListener");
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_perfil_doctor, container, false);
        btnCancelar = view.findViewById(R.id.btnCancelarEditarPerfilD);
        btnCancelar.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancelarEdicion();
            }
        });

        return view;
    }
}