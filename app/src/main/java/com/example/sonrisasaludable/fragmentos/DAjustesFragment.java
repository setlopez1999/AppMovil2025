package com.example.sonrisasaludable.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.actividades.SesionActivity;
import com.example.sonrisasaludable.utilidades.UserPreferences;

import java.util.concurrent.Executor;

public class DAjustesFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {

    private UserPreferences prefs;

    private Spinner spnTemaApp, spnIdioma;
    private Switch swNotificaciones, swHuellaDactilar;
    private SeekBar seekVolumenNotif;
    private LinearLayout llVolumenNotif;

    private AudioManager audioManager;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_ajustes, container, false);

        prefs = new UserPreferences(getContext());

        spnTemaApp = view.findViewById(R.id.spnTemaApp);
        spnIdioma = view.findViewById(R.id.spnIdioma);
        swNotificaciones = view.findViewById(R.id.spinwNotificaciones);
        swHuellaDactilar = view.findViewById(R.id.spswHuellaDactilar);
        seekVolumenNotif = view.findViewById(R.id.spseekVolumenNotif);
        llVolumenNotif = view.findViewById(R.id.llVolumenNotif);

        Button btnGuardarAjustes = view.findViewById(R.id.btnGuardarAjustes);
        Button btnRestaurarDefecto = view.findViewById(R.id.btnRestaurarDefecto);
        Button btnCerrarSesion = view.findViewById(R.id.btnCerrarSesionDoctor);

        audioManager = (AudioManager) requireContext().getSystemService(Context.AUDIO_SERVICE);

        setupBiometricPrompt();
        setupSpinners();
        setupListeners();

        btnGuardarAjustes.setOnClickListener(v -> guardarAjustes());
        btnRestaurarDefecto.setOnClickListener(v -> restaurarDefecto());
        btnCerrarSesion.setOnClickListener(v -> cerrarSesion());

        loadSettings();

        return view;
    }

    private void setupBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(requireContext());
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                prefs.setHuellaActiva(true);
                swHuellaDactilar.setChecked(true);
                Toast.makeText(getContext(), "Huella activada correctamente", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getContext(), "No se reconoció la huella", Toast.LENGTH_SHORT).show();
                swHuellaDactilar.setChecked(false);
            }

            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getContext(), "Error: " + errString, Toast.LENGTH_SHORT).show();
                swHuellaDactilar.setChecked(false);
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Verificar huella")
                .setSubtitle("Coloca tu dedo en el sensor")
                .setNegativeButtonText("Cancelar")
                .build();
    }

    private void setupSpinners() {
        String[] temas = {"Claro", "Oscuro", "Automático"};
        ArrayAdapter<String> adapterTemas = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, temas);
        adapterTemas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTemaApp.setAdapter(adapterTemas);

        String[] idiomas = {"Español", "Inglés", "Portugués"};
        ArrayAdapter<String> adapterIdiomas = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, idiomas);
        adapterIdiomas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnIdioma.setAdapter(adapterIdiomas);
    }

    private void setupListeners() {
        swNotificaciones.setOnCheckedChangeListener((buttonView, isChecked) -> {
            llVolumenNotif.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        swHuellaDactilar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (isBiometricAvailable()) {
                    biometricPrompt.authenticate(promptInfo);
                } else {
                    Toast.makeText(getContext(), "No hay sensor biométrico disponible", Toast.LENGTH_SHORT).show();
                    swHuellaDactilar.setChecked(false);
                }
            } else {
                prefs.setHuellaActiva(false);
                Toast.makeText(getContext(), "Huella dactilar desactivada", Toast.LENGTH_SHORT).show();
            }
        });

        seekVolumenNotif.setOnSeekBarChangeListener(this);
    }

    private void loadSettings() {
        spnTemaApp.setSelection(prefs.isTemaOscuro() ? 1 : 0);
        spnIdioma.setSelection(getIdiomaIndex(prefs.getIdioma()));
        swNotificaciones.setChecked(prefs.isNotificacionesActivas());
        llVolumenNotif.setVisibility(prefs.isNotificacionesActivas() ? View.VISIBLE : View.GONE);
        seekVolumenNotif.setProgress(prefs.getVolumenNotificaciones());
        swHuellaDactilar.setChecked(prefs.isHuellaActiva());

        AppCompatDelegate.setDefaultNightMode(
                prefs.isTemaOscuro() ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    private void guardarAjustes() {
        prefs.setTemaOscuro(spnTemaApp.getSelectedItemPosition() == 1);

        String idioma;
        switch (spnIdioma.getSelectedItemPosition()) {
            case 1: idioma = "en"; break;
            case 2: idioma = "pt"; break;
            default: idioma = "es"; break;
        }
        prefs.setIdioma(idioma);

        prefs.setNotificacionesActivas(swNotificaciones.isChecked());
        prefs.setVolumenNotificaciones(seekVolumenNotif.getProgress());

        // Actualiza volumen real del sistema
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        int newVolume = (maxVolume * seekVolumenNotif.getProgress()) / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, newVolume, 0);

        Toast.makeText(getContext(), "Ajustes guardados", Toast.LENGTH_SHORT).show();

        com.example.sonrisasaludable.utilidades.LocaleHelper.cambiarIdioma(getActivity(), idioma);
        getActivity().recreate();
    }

    private void restaurarDefecto() {
        spnTemaApp.setSelection(0);
        spnIdioma.setSelection(0);
        swNotificaciones.setChecked(true);
        llVolumenNotif.setVisibility(View.VISIBLE);
        seekVolumenNotif.setProgress(50);
        swHuellaDactilar.setChecked(false);

        prefs.clearAll();

        // Restablecer volumen del sistema a 50%
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, maxVolume / 2, 0);

        Toast.makeText(getContext(), "Ajustes restaurados por defecto", Toast.LENGTH_SHORT).show();

        com.example.sonrisasaludable.utilidades.LocaleHelper.cambiarIdioma(getActivity(), "es");
        getActivity().recreate();
    }

    private void cerrarSesion() {
        startActivity(new Intent(getActivity(), SesionActivity.class));
        getActivity().finish();
    }

    private boolean isBiometricAvailable() {
        BiometricManager biometricManager = BiometricManager.from(requireContext());
        return biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                == BiometricManager.BIOMETRIC_SUCCESS;
    }

    private int getIdiomaIndex(String idioma) {
        switch (idioma) {
            case "en": return 1;
            case "pt": return 2;
            default: return 0;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
        int newVolume = (maxVolume * seekBar.getProgress()) / 100;
        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, newVolume, 0);

        Toast.makeText(getContext(), "Volumen: " + seekBar.getProgress() + "%", Toast.LENGTH_SHORT).show();
    }
}
