package com.example.sonrisasaludable.actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.models.CitaConDetalles;
import com.example.sonrisasaludable.data.network.ApiService;
import com.example.sonrisasaludable.data.network.RetrofitClient;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleCitaActivity extends AppCompatActivity {

    private CitaConDetalles cita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_cita);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cita = (CitaConDetalles) getIntent().getSerializableExtra("cita");
        if (cita == null) {
            Toast.makeText(this, "Cita no encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView tvInfo = findViewById(R.id.tvDetalleCitaInfo);
        Spinner spinner = findViewById(R.id.spEstadoCita);
        Button btnGuardar = findViewById(R.id.btnGuardarEstado);
        Button btnCancelar = findViewById(R.id.btnCancelar);

        tvInfo.setText("Paciente: " + cita.getPaciente_nombre()+ "\n" +
                "Fecha: " + cita.getFecha() + "\n" +
                "Hora: " + cita.getHora());

        String[] estados = {"Confirmada", "Pendiente", "Completada"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, estados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int pos = 0;
        for (int i = 0; i < estados.length; i++) {
            if (estados[i].equalsIgnoreCase(cita.getEstado())) {
                pos = i;
                break;
            }
        }
        spinner.setSelection(pos);

        btnGuardar.setOnClickListener(v -> {
            String nuevoEstado = spinner.getSelectedItem().toString();
            cita.setEstado(nuevoEstado);
            actualizarCitaEnBackend(cita);
        });

        btnCancelar.setOnClickListener(v -> finish());
    }

    private void actualizarCitaEnBackend(CitaConDetalles cita) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

        Call<Void> call = apiService.actualizarCita(cita.getId(), cita);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DetalleCitaActivity.this, "Cita actualizada", Toast.LENGTH_SHORT).show();

                    Executors.newSingleThreadExecutor().execute(() -> {
                        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

                        db.citaDao().actualizarEstado(cita.getId(), cita.getEstado());
                    });

                    finish();
        } else {
            Toast.makeText(DetalleCitaActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
        }
    }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DetalleCitaActivity.this, "Fallo en la conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
