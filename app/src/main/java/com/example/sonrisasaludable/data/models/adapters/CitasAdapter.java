package com.example.sonrisasaludable.data.models.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.models.CitaConDetalles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitasViewHolder> {

    private final Context context;
    private final List<CitaConDetalles> citas;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CitaConDetalles cita);
    }

    public CitasAdapter(Context context, List<CitaConDetalles> citas, OnItemClickListener listener) {
        this.context = context;
        this.citas = new ArrayList<>(citas);
        this.listener = listener;
    }

    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cita_card, parent, false);
        return new CitasViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, int position) {
        CitaConDetalles cita = citas.get(position);
        if (cita == null) return;

        String paciente = cita.getPaciente_nombre() != null ? cita.getPaciente_nombre() : "Sin nombre";
        String paciente2 = cita.getPaciente_apellido() != null ? cita.getPaciente_apellido() : "Sin apellido";
        String servicio = cita.getServicio_nombre() != null ? cita.getServicio_nombre() : "Sin servicio";
        String hora = cita.getHora() != null ? cita.getHora() : "Sin hora";
        String estadoStr = cita.getEstado() != null ? cita.getEstado() : "Sin estado";

        holder.nombre.setText(capitalizarPrimeraLetra(paciente) + " " + capitalizarPrimeraLetra(paciente2));
        holder.servicio.setText(servicio);

        // Formatooooooooo xd ayuda de aqui https://developer.android.com/reference/java/text/SimpleDateFormat
        SimpleDateFormat formatoEntrada = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatoSalida = new SimpleDateFormat("h:mm a");

        try {
            // Ahorasi a date
            Date date = formatoEntrada.parse(hora);

            // Luego en su formato de 12horas , a futuro lo podemos modularizar
            String hora2 = formatoSalida.format(date);

            holder.hora.setText(hora2);
        } catch (ParseException e) {
            e.printStackTrace();
            holder.hora.setText("00");
        }
        //holder.hora.setText(hora);
        holder.estado.setText(estadoStr);

        Color_Estado_Cita(holder.estado);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(cita);
            }
        });
    }

    private void Color_Estado_Cita(TextView estado) {
        String key = estado.getText() != null ? estado.getText().toString() : "";
        if (key.equals("Confirmada")) {
            estado.setBackgroundResource(R.drawable.status_confirmed_bg);
        } else if (key.equals("Pendiente")) {
            estado.setBackgroundResource(R.drawable.status_pending_bg);
        } else if (key.equals("Completada")) {
            estado.setBackgroundResource(R.drawable.status_completed_bg);
        } else {
            estado.setBackgroundColor(Color.GRAY); // estado desconocido
        }
    }
    public static String capitalizarPrimeraLetra(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    @Override
    public int getItemCount() {
        return citas.size();
    }
    public void actualizarCitas(List<CitaConDetalles> nuevasCitas) {
        citas.clear();
        citas.addAll(nuevasCitas);
        notifyDataSetChanged();
    }
    public static class CitasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, servicio, hora, estado;

        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.item_nombre_paciente);
            servicio = itemView.findViewById(R.id.item_servicio_de_la_cita);
            hora = itemView.findViewById(R.id.item_hora_de_la_cita);
            estado = itemView.findViewById(R.id.item_estado_cita);
        }
    }
}
