package com.example.sonrisasaludable.fragmentos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.entidades.*;
import com.example.sonrisasaludable.data.models.DoctorConUsuario;
import com.example.sonrisasaludable.data.models.factory.CitasViewModelFactory;
import com.example.sonrisasaludable.data.models.viewmodels.CitasViewModel;
import com.example.sonrisasaludable.utilidades.SessionManager;

import java.util.Calendar;
import java.util.Collections;

public class FormularioCitaFragment extends androidx.fragment.app.Fragment {

    private CitasViewModel viewModel;

    private Spinner spUsuario, spDoctor, spServicio, spSede;
    private TextView etFecha, etHora;
    private TextView etNota;
    private Button btnGuardarCita;

    private UsuarioEntity usuarioSesion;

    public FormularioCitaFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.formulario_cita, container, false);

        spUsuario = view.findViewById(R.id.spUsuario);
        spDoctor = view.findViewById(R.id.spDoctor);
        spServicio = view.findViewById(R.id.spServicio);
        spSede = view.findViewById(R.id.spSede);
        etFecha = view.findViewById(R.id.etFecha);
        etHora = view.findViewById(R.id.etHora);
        etNota = view.findViewById(R.id.etNota);
        btnGuardarCita = view.findViewById(R.id.btnGuardarCita);

        CitasViewModelFactory factory = new CitasViewModelFactory(requireContext());
        viewModel = new ViewModelProvider(this, factory).get(CitasViewModel.class);

        observarDatos();

        etFecha.setOnClickListener(v -> mostrarDatePicker());
        etHora.setOnClickListener(v -> mostrarTimePicker());

        btnGuardarCita.setOnClickListener(v -> agendarCita());

        return view;
    }

    private void mostrarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                (view, year1, month1, dayOfMonth) -> {
                    String fechaSeleccionada = String.format("%04d-%02d-%02d", year1, month1 + 1, dayOfMonth);
                    etFecha.setText(fechaSeleccionada);
                }, year, month, day);
        datePicker.show();
    }

    private void mostrarTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(requireContext(),
                (view, hourOfDay, minute1) -> {
                    String horaSeleccionada = String.format("%02d:%02d:00", hourOfDay, minute1);
                    etHora.setText(horaSeleccionada);
                }, hour, minute, true);
        timePicker.show();
    }

    private void observarDatos() {
        SessionManager session = SessionManager.getInstance(requireContext());
        int userIdSesion = session.getUserId();

        // Usuario fijo y deshabilitado
        viewModel.getUsuarios().observe(getViewLifecycleOwner(), usuarios -> {
            for (UsuarioEntity u : usuarios) {
                if (u.getId() == userIdSesion) {
                    usuarioSesion = u;
                    break;
                }
            }

            if (usuarioSesion != null) {
                ArrayAdapter<UsuarioEntity> adapter = new ArrayAdapter<>(requireContext(),
                        android.R.layout.simple_spinner_item,
                        Collections.singletonList(usuarioSesion)); // solo uno
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spUsuario.setAdapter(adapter);
                spUsuario.setEnabled(false);
            }
        });

        // DoctorConUsuario muestra nombre y apellido
        viewModel.getDoctoresConUsuario().observe(getViewLifecycleOwner(), doctores -> {
            ArrayAdapter<DoctorConUsuario> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, doctores);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDoctor.setAdapter(adapter);
        });

        viewModel.getServicios().observe(getViewLifecycleOwner(), servicios -> {
            ArrayAdapter<ServicioEntity> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, servicios);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spServicio.setAdapter(adapter);
        });

        viewModel.getSedes().observe(getViewLifecycleOwner(), sedes -> {
            ArrayAdapter<SedeEntity> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, sedes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSede.setAdapter(adapter);
        });
    }

    private void agendarCita() {
        SessionManager session = SessionManager.getInstance(requireContext());

        int usuarioId = session.getUserId(); // usuario actual de la sesión
        DoctorConUsuario doctor = (DoctorConUsuario) spDoctor.getSelectedItem();
        ServicioEntity servicio = (ServicioEntity) spServicio.getSelectedItem();
        SedeEntity sede = (SedeEntity) spSede.getSelectedItem();

        String fecha = etFecha.getText().toString();
        String hora = etHora.getText().toString();
        String nota = etNota.getText().toString();

        if (doctor == null || servicio == null || sede == null ||
                fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        CitaEntity nuevaCita = new CitaEntity(
                0,                // id = 0 porque Room lo genera
                usuarioId,
                doctor.getId(),
                servicio.getId(),
                sede.getId(),
                fecha,
                hora,
                "pendiente",
                nota,
                ""                // creado_en, opcional
        );

        viewModel.insertarCitaConSincronizacion(nuevaCita,requireContext());

        Toast.makeText(requireContext(), "Cita agendada como pendiente", Toast.LENGTH_SHORT).show();
    }

}
