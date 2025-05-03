package com.example.sonrisasaludable.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.SesionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfiguracionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfiguracionFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Declaración de las variables de los componentes de la vista
    Spinner cboIdiomas; // Para el ComboBox
    CheckBox chkNotificaciones; // Para el CheckBox
    TextView lblSonido; // Para la etiqueta de Sonido
    SeekBar barSonido; // Para la barra de sonido
    Button btnAplicar,btnRestaurar, btnCerrarSesion; // Para el botón aplicar y Restaurar



    public ConfiguracionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfiguracionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfiguracionFragment newInstance(String param1, String param2) {
        ConfiguracionFragment fragment = new ConfiguracionFragment();
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
        // Inflar el layout para este fragmento
        View vista = inflater.inflate(R.layout.fragment_configuracion, container, false);

        // Inicializar los componentes de la vista
        cboIdiomas = vista.findViewById(R.id.frgCfgcboIdioma); // Spinner (ComboBox)
        chkNotificaciones = vista.findViewById(R.id.froCfgchkNotificacines); // Checkbox
        lblSonido = vista.findViewById(R.id.frgCfgCblSonido); // Etiqueta de Sonido
        barSonido = vista.findViewById(R.id.frgCfgBarSonido); // Barra de sonido (SeekBar)

        // Botones
        btnAplicar = vista.findViewById(R.id.frgCfg8tnAplicar); // Botón Aplicar
        btnRestaurar = vista.findViewById(R.id.frgCfgBtnRestaurar); // Botón Restaurar

        btnAplicar.setOnClickListener(this);
        btnRestaurar.setOnClickListener(this);
        barSonido.setOnSeekBarChangeListener(this);

        //Botón cerrar sesión
        Button btnCerrarSesion = vista.findViewById(R.id.btnCerrarSesion);

        btnCerrarSesion.setOnClickListener(v -> {
            // Crear Intent para ir a SesionActivity
            Intent intent = new Intent(getActivity(), SesionActivity.class);
            startActivity(intent);

            // Finalizar la actividad actual (opcional)
            getActivity().finish();
        });
        cargarPreferencias();
        return vista;
    }


    private void cargarPreferencias() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferencias",Context.MODE_PRIVATE);
        int idioma =preferences.getInt("idioma",0);
        boolean notificaciones = preferences.getBoolean("notificaciones",false);
        int sonido = preferences.getInt("sonido",100);

        cboIdiomas.setSelection(idioma);
        chkNotificaciones.setChecked(notificaciones);
        barSonido.setProgress(sonido);

    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.frgCfg8tnAplicar)
            aplicar();
        else if (v.getId() == R.id.frgCfgBtnRestaurar) {
            restaurar();
        }
    }
    private void aplicar() {
        SharedPreferences preferences = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idioma", cboIdiomas.getSelectedItemPosition());
        editor.putBoolean("notificaciones", chkNotificaciones.isChecked());
        editor.putInt("sonido", barSonido.getProgress());
        editor.apply();
        Toast.makeText(getContext(), "Preferencias quardadas", Toast.LENGTH_SHORT).show();
    }


    private void restaurar() {
        cboIdiomas.setSelection(0);
        chkNotificaciones.setChecked(true);
        barSonido.setProgress(100);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar == barSonido) {
            String volumen = "Volumen: ";
            switch (progress) {
                case 0:
                    volumen += "Minimo";
                    break;
                case 100:
                    volumen += "Máximo";
                    break;
                default:
                    volumen += String.valueOf(progress);
                    break;
            }
            lblSonido.setText(volumen);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }



}













