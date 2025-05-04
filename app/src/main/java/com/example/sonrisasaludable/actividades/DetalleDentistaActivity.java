package com.example.sonrisasaludable.actividades;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.sonrisasaludable.R;

public class DetalleDentistaActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText etComentario;
    private Button btnEnviarResena, btnCerrarDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_dentista);

        // Enlazar vistas
        ratingBar = findViewById(R.id.ratingBar);
        etComentario = findViewById(R.id.etComentario);
        btnEnviarResena = findViewById(R.id.btnEnviarResena);
        btnCerrarDetalle = findViewById(R.id.btnCerrarDetalle);

        // Botón Enviar Reseña
        btnEnviarResena.setOnClickListener(v -> {
            float estrellas = ratingBar.getRating();
            String comentario = etComentario.getText().toString().trim();

            // Mostrar el TOAST
            Toast.makeText(DetalleDentistaActivity.this,
                    "ENVIAR A BD Gracias por tu reseña: " + estrellas + " estrellas" +
                            (comentario.isEmpty() ? "" : "\nComentario: " + comentario),
                    Toast.LENGTH_LONG).show();
        });

        // Botón Cerrar → volver a DentistasFragment (solo cerrando la Activity)
        btnCerrarDetalle.setOnClickListener(v -> {
            finish(); // Cierra la actividad y regresa al fragment anterior
        });
    }
}
