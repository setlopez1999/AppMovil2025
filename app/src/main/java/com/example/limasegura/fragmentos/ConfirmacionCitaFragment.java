package com.example.limasegura.fragmentos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.limasegura.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmacionCitaFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class ConfirmacionCitaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmacionCitaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmacionCitaFragment newInstance(String param1, String param2) {
        ConfirmacionCitaFragment fragment = new ConfirmacionCitaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConfirmacionCitaFragment() {
        // Required empty public constructor
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

        // Inflar la vista
        View vista = inflater.inflate(R.layout.fragment_confirmacion_cita, container, false);

        // Aquí podrías inicializar los TextView si quieres mostrar dinámicamente:
        // TextView txtTratamiento = vista.findViewById(R.id.txtTratamiento);
        // txtTratamiento.setText("Limpieza dental"); -> Ejemplo

        return vista;
    }



}