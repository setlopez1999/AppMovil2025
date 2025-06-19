package com.example.sonrisasaludable.fragmentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.ReciboActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistorialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HistorialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistorialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistorialFragment newInstance(String param1, String param2) {
        HistorialFragment fragment = new HistorialFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        // Inflar la vista del fragmento
        View vista = inflater.inflate(R.layout.fragment_historial, container, false);

        // Botón eliminar cita (próximas citas)
        ImageButton btnDeleteCita = vista.findViewById(R.id.deletecita);

        // Botón detalle cita (citas pasadas)
        ImageButton btnDetalleCita = vista.findViewById(R.id.detallecita);

        // Listener para eliminar cita
        btnDeleteCita.setOnClickListener(v -> {
            // Lógica para eliminar cita
            Toast.makeText(getContext(), "CitaProgreso borrada en BD, actualizar fragmento", Toast.LENGTH_SHORT).show();
        });

        // Listener para ver detalle de cita (llevar a ReciboActivity)
        btnDetalleCita.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReciboActivity.class);
            startActivity(intent);
        });

        return vista;
    }

}