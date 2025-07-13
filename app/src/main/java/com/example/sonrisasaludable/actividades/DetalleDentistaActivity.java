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
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.services.ResenaService;
import com.example.sonrisasaludable.utilidades.SessionManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetalleDentistaActivity extends AppCompatActivity {

    private ImageView imgDoctor;
    private TextView tvNombreDoctor, tvEspecialidad, tvDireccion, tvTelefono;
    private RatingBar ratingBar;
    private EditText etComentario;
    private Button btnEnviarResena, btnCerrarDetalle;
    private ResenaService resenaService;
    private SessionManager sessionManager;
    private ExecutorService executor;
    private int doctorId;
    private int citaId = -1;

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

        // Inicializar servicios
        AppDatabase db = AppDatabase.getInstance(this);
        resenaService = new ResenaService(db.resenaDao(), db.citaDao());
        sessionManager = SessionManager.getInstance(this);
        executor = Executors.newSingleThreadExecutor();

        // Obtener datos del intent
        DoctorConUsuario doctor = (DoctorConUsuario) getIntent().getSerializableExtra("doctor");
        citaId = getIntent().getIntExtra("citaId", -1);

        if (doctor != null) {
            doctorId = doctor.getId();
            tvNombreDoctor.setText(doctor.getNombreCompleto());
            tvEspecialidad.setText("Especialidad ID: " + doctor.getEspecialidadId());
            tvDireccion.setText("Sede: Clínica Central");
            tvTelefono.setText("Teléfono: +51 987 654 321");

            // Cargar imagen con Glide
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

        // Verificar si puede crear reseña
        verificarPermisoResena();

        // Botón Enviar Reseña
        btnEnviarResena.setOnClickListener(v -> enviarResena());

        // Botón Cerrar
        btnCerrarDetalle.setOnClickListener(v -> finish());
    }

    private void verificarPermisoResena() {
        if (citaId == -1 || !sessionManager.getRole().equals("paciente")) {
            btnEnviarResena.setEnabled(false);
            btnEnviarResena.setText("No disponible");
            return;
        }

        executor.execute(() -> {
            // Verificar si ya existe reseña para esta cita
            AppDatabase db = AppDatabase.getInstance(this);
            boolean yaExisteResena = db.resenaDao().getByCitaId(citaId) != null;
            
            runOnUiThread(() -> {
                if (yaExisteResena) {
                    btnEnviarResena.setText("Ya reseñado");
                    btnEnviarResena.setEnabled(false);
                }
            });
        });
    }

    private void enviarResena() {
        int calificacion = (int) ratingBar.getRating();
        String comentario = etComentario.getText().toString().trim();

        if (calificacion == 0) {
            Toast.makeText(this, "Por favor selecciona una calificación", Toast.LENGTH_SHORT).show();
            return;
        }

        executor.execute(() -> {
            boolean success = resenaService.crearResena(citaId, sessionManager.getUserId(), calificacion, comentario);
            
            runOnUiThread(() -> {
                if (success) {
                    Toast.makeText(this, "Reseña enviada exitosamente", Toast.LENGTH_SHORT).show();
                    btnEnviarResena.setText("Ya reseñado");
                    btnEnviarResena.setEnabled(false);
                } else {
                    Toast.makeText(this, "Error al enviar la reseña", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }
}
