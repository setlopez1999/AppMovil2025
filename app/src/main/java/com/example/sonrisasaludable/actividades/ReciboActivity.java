package com.example.sonrisasaludable.actividades;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.sonrisasaludable.R;

public class ReciboActivity extends AppCompatActivity {

    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);

        // Botón para regresar
        btnBack = findViewById(R.id.btnCierraDetalle);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finaliza la actividad, regresando automáticamente al Historial de citas
                finish();
            }
        });
    }
}
