package com.example.sonrisasaludable.actividades;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.models.DoctorConUsuario;

public class DetalleDentistaActivity extends AppCompatActivity {

    private ImageView imgDoctor;
    private TextView tvNombreDoctor, tvEspecialidad, tvDireccion, tvTelefono;
    private RatingBar ratingBar;
    private EditText etComentario;
    private Button btnEnviarResena, btnCerrarDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_dentista);

        // Enlazar vistas
        imgDoctor = findViewById(R.id.imgDoctor);
        tvNombreDoctor = findViewById(R.id.tvNombreDoctor);
        tvEspecialidad = findViewById(R.id.tvEspecialidad);
        tvDireccion = findViewById(R.id.tvDireccion);
        tvTelefono = findViewById(R.id.tvTelefono);
        ratingBar = findViewById(R.id.ratingBar);
        etComentario = findViewById(R.id.etComentario);
        btnEnviarResena = findViewById(R.id.btnEnviarResena);
        btnCerrarDetalle = findViewById(R.id.btnCerrarDetalle);

        // Obtener doctor desde el intent
        DoctorConUsuario doctor = (DoctorConUsuario) getIntent().getSerializableExtra("doctor");

        if (doctor != null) {
            tvNombreDoctor.setText(doctor.getNombreCompleto());
            tvEspecialidad.setText("Especialidad ID: " + doctor.getEspecialidadId()); // Cambia si tienes nombre de especialidad
            tvDireccion.setText("Sede: Clínica Central"); // Reemplaza si tienes sede real
            tvTelefono.setText("Teléfono: +51 987 654 321"); // Reemplaza si tienes teléfono

            ratingBar.setRating((float) doctor.getReputacion());

            // Cargar imagen con Glide (si tienes una URL o ruta válida)
            if (doctor.getFotoPerfil() != null && !doctor.getFotoPerfil().isEmpty()) {
                Glide.with(this)
                        .load(doctor.getFotoPerfil())
                        .placeholder(R.drawable.doctor1_default)
                        .circleCrop()
                        .into(imgDoctor);
            } else {
                imgDoctor.setImageResource(R.drawable.doctor1_default);
            }
        }

        // Botón Enviar Reseña
        btnEnviarResena.setOnClickListener(v -> {
            float estrellas = ratingBar.getRating();
            String comentario = etComentario.getText().toString().trim();

            Toast.makeText(this,
                    "ENVIAR A BD Gracias por tu reseña: " + estrellas + " estrellas" +
                            (comentario.isEmpty() ? "" : "\nComentario: " + comentario),
                    Toast.LENGTH_LONG).show();
        });

        // Botón Cerrar
        btnCerrarDetalle.setOnClickListener(v -> {
            finish(); // Vuelve al fragment anterior
        });
    }
}
