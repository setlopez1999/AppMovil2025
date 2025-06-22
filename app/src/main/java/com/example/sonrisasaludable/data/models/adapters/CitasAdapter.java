package com.example.sonrisasaludable.data.models.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.models.CitaConDetalles;


import java.util.List;

public class CitasAdapter extends RecyclerView.Adapter<CitasAdapter.CitasViewHolder>  {
    //aqui pondremos todos los datos de las citas sin id's

    private final Context context;
    private final List<CitaConDetalles> citas;
    private final CitasAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(CitaConDetalles cita);
    }
    public CitasAdapter(Context context, List<CitaConDetalles> citas, CitasAdapter.OnItemClickListener listener) {
        this.context = context;
        this.citas = citas;
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
        holder.nombre.setText(" " + cita.getPaciente_nombre());
        holder.servicio.setText(cita.getServicio_nombre());
        holder.hora.setText(cita.getHora());
        holder.estado.setText(cita.getEstado());
        Color_Estado_Cita(holder.estado);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(cita);
            }
        });
    }

    private void Color_Estado_Cita(TextView estado) {
        //cambiamos el color de acuerdo al estado
        String key = (String)estado.getText();
        if(key.equals("Confirmada")){
            //pintamos de verde
            estado.setBackgroundResource(R.drawable.status_confirmed_bg);
        }
        else if (key.equals("Pendiente")) {
            estado.setBackgroundResource(R.drawable.status_pending_bg);
        }
        else if (key.equals("Completada")){
            estado.setBackgroundResource(R.drawable.status_completed_bg);
        }
    }

    @Override
    public int getItemCount() {
        return citas.size();
    }



    public static class CitasViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, servicio,hora,estado;

        public CitasViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.item_nombre_paciente);
            servicio = itemView.findViewById(R.id.item_servicio_de_la_cita);
            hora = itemView.findViewById(R.id.item_hora_de_la_cita);
            estado = itemView.findViewById(R.id.item_estado_cita);
        }
    }
}
