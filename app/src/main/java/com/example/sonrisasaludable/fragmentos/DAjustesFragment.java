package com.example.sonrisasaludable.fragmentos;


import com.example.sonrisasaludable.R;
import android.content.Context;
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

import androidx.fragment.app.Fragment;


public class DAjustesFragment extends Fragment {

    private static final String PREFS_NAME = "AjustesApp";
    private static final String KEY_TEMA = "tema";
    private static final String KEY_IDIOMA = "idioma";
    private static final String KEY_NOTIFICACIONES = "notificaciones";
    private static final String KEY_VOLUMEN = "volumen";
    private static final String KEY_HUELLA = "huella_dactilar";

    private SharedPreferences sharedPreferences;

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
        sharedPreferences = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Inicializar vistas
        initViews(view);

        // Configurar spinners
        setupSpinners();

        // Cargar configuraciones guardadas
        loadSettings();

        // Configurar listeners
        setupListeners();

        return view;
    }

    private void initViews(View view) {
        spnTemaApp = view.findViewById(R.id.spnTemaApp);
        spnIdioma = view.findViewById(R.id.spnIdioma);
        swNotificaciones = view.findViewById(R.id.swNotificaciones);
        swHuellaDactilar = view.findViewById(R.id.swHuellaDactilar);
        seekVolumenNotif = view.findViewById(R.id.seekVolumenNotif);
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
        // Cargar tema
        int temaGuardado = sharedPreferences.getInt(KEY_TEMA, 0);
        spnTemaApp.setSelection(temaGuardado);

        // Cargar idioma
        int idiomaGuardado = sharedPreferences.getInt(KEY_IDIOMA, 0);
        spnIdioma.setSelection(idiomaGuardado);

        // Cargar notificaciones
        boolean notificacionesActivas = sharedPreferences.getBoolean(KEY_NOTIFICACIONES, true);
        swNotificaciones.setChecked(notificacionesActivas);

        // Mostrar/ocultar volumen según notificaciones
        llVolumenNotif.setVisibility(notificacionesActivas ? View.VISIBLE : View.GONE);

        // Cargar volumen
        int volumen = sharedPreferences.getInt(KEY_VOLUMEN, 50);
        seekVolumenNotif.setProgress(volumen);

        // Cargar huella dactilar
        boolean huellaActiva = sharedPreferences.getBoolean(KEY_HUELLA, false);
        swHuellaDactilar.setChecked(huellaActiva);
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

    private void guardarAjustes() {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Guardar todas las configuraciones
        editor.putInt(KEY_TEMA, spnTemaApp.getSelectedItemPosition());
        editor.putInt(KEY_IDIOMA, spnIdioma.getSelectedItemPosition());
        editor.putBoolean(KEY_NOTIFICACIONES, swNotificaciones.isChecked());
        editor.putInt(KEY_VOLUMEN, seekVolumenNotif.getProgress());
        editor.putBoolean(KEY_HUELLA, swHuellaDactilar.isChecked());

        // Aplicar cambios
        editor.apply();

        Toast.makeText(getContext(), "Ajustes guardados correctamente", Toast.LENGTH_SHORT).show();

        // Opcional: aplicar cambios inmediatamente
        aplicarCambios();
    }

    private void restaurarDefecto() {
        // Restaurar valores por defecto
        spnTemaApp.setSelection(0); // Tema claro
        spnIdioma.setSelection(0); // Español
        swNotificaciones.setChecked(true);
        llVolumenNotif.setVisibility(View.VISIBLE);
        seekVolumenNotif.setProgress(50);
        swHuellaDactilar.setChecked(false);

        Toast.makeText(getContext(), "Ajustes restaurados por defecto", Toast.LENGTH_SHORT).show();
    }

    private void aplicarCambios() {
        // Aquí puedes aplicar los cambios inmediatamente
        // Por ejemplo, cambiar tema, idioma, etc.

        // Ejemplo para tema:
        int tema = spnTemaApp.getSelectedItemPosition();
        switch (tema) {
            case 0: // Claro
                // Aplicar tema claro
                break;
            case 1: // Oscuro
                // Aplicar tema oscuro
                break;
            case 2: // Automático
                // Aplicar tema automático
                break;
        }

        // Ejemplo para idioma:
        int idioma = spnIdioma.getSelectedItemPosition();
        switch (idioma) {
            case 0: // Español
                // Cambiar a español
                break;
            case 1: // English
                // Cambiar a inglés
                break;
            case 2: // Português
                // Cambiar a portugués
                break;
        }
    }

    // Método público para obtener configuraciones desde otras partes de la app
    public static boolean isNotificacionesActivadas(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_NOTIFICACIONES, true);
    }

    public static int getVolumenNotificaciones(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_VOLUMEN, 50);
    }

    public static boolean isHuellaDactilarActivada(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_HUELLA, false);
    }
}