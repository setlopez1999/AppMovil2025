package com.example.sonrisasaludable.fragmentos;


import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.SesionActivity;
import com.example.sonrisasaludable.utilidades.UserPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;


public class DAjustesFragment extends Fragment  implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private static final String PREFS_NAME = "AjustesApp";
    private static final String KEY_TEMA = "tema";
    private static final String KEY_IDIOMA = "idioma";
    private static final String KEY_NOTIFICACIONES = "notificaciones";
    private static final String KEY_VOLUMEN = "volumen";
    private static final String KEY_HUELLA = "huella_dactilar";

    private UserPreferences prefs;

    // Views
    private Spinner spnTemaApp;
    private Spinner spnIdioma;
    private Switch swNotificaciones;
    private Switch swHuellaDactilar;
    private SeekBar seekVolumenNotif;
    private LinearLayout llVolumenNotif;
    private Button btnGuardarAjustes;
    private Button btnRestaurarDefecto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_ajustes, container, false);

        // Inicializar SharedPreferences
        prefs = new UserPreferences(getContext());

        // Inicializar vistas
        initViews(view);

        // Configurar spinners
        setupSpinners();

        // Cargar configuraciones guardadas
        //loadSettings();

        // Configurar listeners
        setupListeners();

        Button btnCerrarSesion = view.findViewById(R.id.btnCerrarSesionDoctor);;
        btnCerrarSesion.setOnClickListener(v -> {
            // Crear Intent para ir a SesionActivity
            Intent intent = new Intent(getActivity(), SesionActivity.class);
            startActivity(intent);

            // Finalizar la actividad actual (opcional)
            getActivity().finish();
        });
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnGuardarAjustes)
            loadSettings();
        else if (v.getId() == R.id.btnRestaurarDefecto)
            restaurarDefecto();
    }
    private void initViews(View view) {
        spnTemaApp = view.findViewById(R.id.spnTemaApp);
        spnIdioma = view.findViewById(R.id.spnIdioma);
        swNotificaciones = view.findViewById(R.id.spinwNotificaciones);
        swHuellaDactilar = view.findViewById(R.id.spswHuellaDactilar);
        seekVolumenNotif = view.findViewById(R.id.spseekVolumenNotif);
        llVolumenNotif = view.findViewById(R.id.llVolumenNotif);
        btnGuardarAjustes = view.findViewById(R.id.btnGuardarAjustes);
        btnRestaurarDefecto = view.findViewById(R.id.btnRestaurarDefecto);

    }

    private void setupSpinners() {
        // Configurar spinner de temas
        String[] temas = {"Claro", "Oscuro", "Automático"};
        ArrayAdapter<String> adapterTemas = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                temas
        );
        adapterTemas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTemaApp.setAdapter(adapterTemas);

        // Configurar spinner de idiomas
        String[] idiomas = {"Español", "English", "Português"};
        ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                idiomas
        );
        adapterIdiomas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnIdioma.setAdapter(adapterIdiomas);
    }
    private void loadSettings() {
        boolean temaOscuro = prefs.isTemaOscuro();
        AppCompatDelegate.setDefaultNightMode(
                temaOscuro ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
        spnTemaApp.setSelection(temaOscuro ? 1 : 0);

        String idioma = prefs.getIdioma();
        spnIdioma.setSelection(getIdiomaIndex(idioma));

        com.example.sonrisasaludable.utilidades.LocaleHelper.cambiarIdioma(getActivity(), idioma);

        // Recargamos_Todo XD
        getActivity().recreate();

        boolean notificacionesActivas = prefs.isNotificacionesActivas();
        swNotificaciones.setChecked(notificacionesActivas);
        llVolumenNotif.setVisibility(notificacionesActivas ? View.VISIBLE : View.GONE);

        int volumen = prefs.getVolumenNotificaciones();
        seekVolumenNotif.setProgress(volumen);

        boolean huella = prefs.isHuellaActiva();
        swHuellaDactilar.setChecked(huella);
    }
    private void setupListeners() {
        // Listener para notificaciones
        swNotificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            llVolumenNotif.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            if (!isChecked) {
                Toast.makeText(getContext(), "Notificaciones desactivadas", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para huella dactilar
        swHuellaDactilar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Aquí puedes agregar lógica para verificar si el dispositivo tiene sensor de huella
                Toast.makeText(getContext(), "Huella dactilar activada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Huella dactilar desactivada", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para volumen
        seekVolumenNotif.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Opcional: mostrar el valor actual
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getContext(), "Volumen: " + seekBar.getProgress() + "%", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para spinner de tema
        spnTemaApp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tema = parent.getItemAtPosition(position).toString();
                // Aquí puedes aplicar el tema inmediatamente si quieres
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Listener para spinner de idioma
        spnIdioma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idioma = parent.getItemAtPosition(position).toString();
                // Aquí puedes cambiar el idioma inmediatamente si quieres
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Botón guardar
        btnGuardarAjustes.setOnClickListener(v -> guardarAjustes());

        // Botón restaurar
        btnRestaurarDefecto.setOnClickListener(v -> restaurarDefecto());
    }
    private int getIdiomaIndex(String idioma) {
        switch (idioma) {
            case "es": return 0;
            case "en": return 1;
            case "pt": return 2;
            default: return 0;
        }
    }
    private void guardarAjustes() {
        boolean esOscuro = (spnTemaApp.getSelectedItemPosition() == 1);
        prefs.setTemaOscuro(esOscuro);
        switch (spnIdioma.getSelectedItemPosition()) {
            case 0:
                prefs.setIdioma("es");
                break;
            case 1:
                prefs.setIdioma("en");
                break;
            case 2:
                prefs.setIdioma("pt");
                break;
        }
        prefs.setNotificacionesActivas(swNotificaciones.isChecked());
        prefs.setVolumenNotificaciones(seekVolumenNotif.getProgress());
        prefs.setHuellaActiva(swHuellaDactilar.isChecked());
        Toast.makeText(getContext(), "Ajustes guardados correctamente", Toast.LENGTH_SHORT).show();
    }
    private void restaurarDefecto() {
        // Restaurar valores por defecto
        spnTemaApp.setSelection(0); // Tema claro
        spnIdioma.setSelection(0); // Español
        swNotificaciones.setChecked(true);
        llVolumenNotif.setVisibility(View.VISIBLE);
        seekVolumenNotif.setProgress(50);
        swHuellaDactilar.setChecked(false);
        prefs.clearAll();
        Toast.makeText(getContext(), "Ajustes restaurados por defecto", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}