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
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.utilidades.SessionManager;
import com.example.sonrisasaludable.utilidades.ThemeManager;
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
        
        // Aplicar tema antes de setContentView
        ThemeManager.applyTheme(this);
        
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
        // Usar Room local (datos persistentes):
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

        // Crear cita de prueba para API REST
        crearCitaPruebaParaApi();
        
        // Verificar si puede crear reseña
        verificarPermisoResena();

        // Botón Enviar Reseña
        btnEnviarResena.setOnClickListener(v -> enviarResena());

        // Botón Cerrar
        btnCerrarDetalle.setOnClickListener(v -> finish());
    }

    private void verificarPermisoResena() {
        if (!sessionManager.getRole().equals("paciente")) {
            btnEnviarResena.setEnabled(false);
            btnEnviarResena.setText("No disponible");
            return;
        }

        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            
            if (citaId != -1) {
                // Si viene de una cita específica
                boolean yaExisteResena = db.resenaDao().getByCitaId(citaId) != null;
                runOnUiThread(() -> {
                    if (yaExisteResena) {
                        btnEnviarResena.setText("Ya reseñado");
                        btnEnviarResena.setEnabled(false);
                    }
                });
            } else {
                // Buscar citas completadas sin reseña con este doctor
                var citasSinResena = resenaService.getCitasSinResena(sessionManager.getUserId());
                var citaConDoctor = citasSinResena.stream()
                    .filter(cita -> cita.getDoctor_id() == doctorId)
                    .findFirst();
                    
                runOnUiThread(() -> {
                    if (citaConDoctor.isPresent()) {
                        citaId = citaConDoctor.get().getId();
                        btnEnviarResena.setEnabled(true);
                        btnEnviarResena.setText("Enviar reseña");
                    } else {
                        btnEnviarResena.setEnabled(false);
                        btnEnviarResena.setText("Sin citas completadas");
                    }
                });
            }
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
                    // Toast.makeText(this, "Reseña guardada localmente", Toast.LENGTH_LONG).show();
                    Toast.makeText(this, "Reseña enviada exitosamente", Toast.LENGTH_SHORT).show();
                    btnEnviarResena.setText("Ya reseñado");
                    btnEnviarResena.setEnabled(false);
                } else {
                    Toast.makeText(this, "Error al enviar la reseña", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void crearCitaPruebaParaApi() {
        executor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            
            // Verificar si ya existe una cita completada
            boolean tieneCompletada = false;
            try {
                var citasSinResena = resenaService.getCitasSinResena(sessionManager.getUserId());
                tieneCompletada = citasSinResena.stream().anyMatch(c -> c.getDoctor_id() == doctorId);
            } catch (Exception e) {
                // Ignorar error
            }
            
            if (!tieneCompletada) {
                // Crear cita completada de prueba para API REST
                CitaEntity cita = new CitaEntity();
                cita.setId((int) System.currentTimeMillis() % 100000);
                cita.setUsuario_id(sessionManager.getUserId());
                cita.setDoctor_id(doctorId);
                cita.setFecha("2024-01-15");
                cita.setHora("10:00");
                cita.setEstado("Completada");
                cita.setNota("Cita de prueba para API REST");
                cita.setCreado_en("2024-01-15 09:00:00");
                
                db.citaDao().insert(cita);
                
                runOnUiThread(() -> {
                    // Toast.makeText(this, "[API REST] Cita de prueba creada", Toast.LENGTH_SHORT).show();
                });
            }
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
