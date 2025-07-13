package com.example.sonrisasaludable.actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.models.CitaConDetalles;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReciboActivity extends AppCompatActivity {

    private TextView tvNumCita, tvFechaHora, tvDoctor, tvPaciente, tvTratamiento, tvSalon, tvNotas, tvTotal;
    private Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recibo);

        int citaId = getIntent().getIntExtra("cita_id", -1);
        if (citaId == -1) {
            finish(); // si no viene id, salimos
            return;
        }

        initViews();

        cargarCita(citaId);

        btnCerrar.setOnClickListener(v -> finish());
    }

    private void initViews() {
        tvNumCita = findViewById(R.id.tvNumCita);
        tvFechaHora = findViewById(R.id.tvFechaHora);
        tvDoctor = findViewById(R.id.tvDoctor);
        tvPaciente = findViewById(R.id.tvPaciente);
        tvTratamiento = findViewById(R.id.tvTratamiento);
        tvSalon = findViewById(R.id.tvSalon);
        tvNotas = findViewById(R.id.tvNotas);
        tvTotal = findViewById(R.id.tvTotal);
        btnCerrar = findViewById(R.id.btnCierraDetalle);
    }

    private void cargarCita(int citaId) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            CitaConDetalles cita = AppDatabase.getInstance(getApplicationContext())
                    .citaDao()
                    .getCitaConDetallesById(citaId);

            runOnUiThread(() -> {
                if (cita != null) {
                    mostrarDatos(cita);
                } else {
                    finish(); // no encontrada
                }
            });
        });
    }

    private void mostrarDatos(CitaConDetalles cita) {
        tvNumCita.setText("Número de cita: " + cita.getId());
        tvFechaHora.setText("Fecha: " + cita.getFecha() + " " + cita.getHora());
        tvDoctor.setText("Doctor: " + cita.getDoctor_info());
        tvPaciente.setText("Paciente: " + cita.getPaciente_nombre() + " " + cita.getPaciente_apellido());
        tvTratamiento.setText("Tratamiento: " + cita.getServicio_nombre());

        double precio = calcularPrecio(cita.getServicio_id());
        tvTotal.setText(String.format("S/. %.2f", precio));
    }

    private double calcularPrecio(int servicioId) {
        if (servicioId == 1) {
            return 120.00;
        } else if (servicioId == 2) {
            return 1500.00;
        } else if (servicioId == 3) {
            return 800.00;
        } else if (servicioId == 4) {
            return 350.00;
        } else if (servicioId == 5) {
            return 250.00;
        } else if (servicioId == 6) {
            return 300.00;
        } else {
            return 100.00;
        }
    }
}
