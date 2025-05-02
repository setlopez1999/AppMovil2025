package com.example.limasegura.fragmentos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.limasegura.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CitaExitosaFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CitaExitosaFragment extends Fragment {

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
     * @return A new instance of fragment CitaExitosaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CitaExitosaFragment newInstance(String param1, String param2) {
        CitaExitosaFragment fragment = new CitaExitosaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CitaExitosaFragment() {
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
        View vista = inflater.inflate(R.layout.fragment_cita_exitosa, container, false);

        // Puedes inicializar el TextView o ImageView si quieres personalizar algo, pero en este caso no es necesario

        return vista;
    }

}