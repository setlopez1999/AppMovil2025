package com.example.sonrisasaludable.actividades;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sonrisasaludable.R;

import java.util.Locale;

public class CargaActivity extends AppCompatActivity {
    ProgressBar barCarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aplicarIdiomaGuardado(); // <-- APLICAR IDIOMA PRIMERO
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carga);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        barCarga = findViewById(R.id.carBarCarga);
        Thread tCarga = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= barCarga.getMax() ; i++) {
                    barCarga.setProgress(i);
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Intent iSesion = new Intent(getApplicationContext(), SesionActivity.class);
                startActivity(iSesion);
                finish();
            }
        });
        tCarga.start();
    }

    public void aplicarIdiomaGuardado() {
        SharedPreferences prefs = getSharedPreferences("Ajustes", MODE_PRIVATE);
        int idioma = prefs.getInt("idioma", 0); // idioma por defecto
        String codigoIdioma = "es";
        if (idioma == 1) {
            codigoIdioma = "en";
        } else if (idioma == 2) {
            codigoIdioma = "ru";
        } else if (idioma == 3) {
            codigoIdioma = "zh";
        }

        // Aquí aplicamos el idioma
        Locale nuevoLocale = new Locale(codigoIdioma);
        Locale.setDefault(nuevoLocale);
        Configuration config = new Configuration();
        config.setLocale(nuevoLocale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

}