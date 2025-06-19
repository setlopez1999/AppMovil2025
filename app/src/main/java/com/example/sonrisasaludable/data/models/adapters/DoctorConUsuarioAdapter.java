package com.example.sonrisasaludable.data.models.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.models.DoctorConUsuario;
import java.util.List;

public class DoctorConUsuarioAdapter extends RecyclerView.Adapter<DoctorConUsuarioAdapter.DoctorViewHolder> {

    private final Context context;
    private final List<DoctorConUsuario> doctores;
    private final OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(DoctorConUsuario doctor);
    }

    public DoctorConUsuarioAdapter(Context context, List<DoctorConUsuario> doctores, OnItemClickListener listener) {
        this.context = context;
        this.doctores = doctores;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_doctor_card, parent, false);
        return new DoctorViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        DoctorConUsuario doctor = doctores.get(position);
        holder.tvNombre.setText("Dr. " + doctor.getApellidos());
        holder.tvEspecialidad.setText(doctor.getNombreEspecialidad()); // o usa nombre real si lo tienes

        // Aquí puedes cargar la foto que aparecera antes de presionar
        Glide.with(context).load(doctor.getFotoPerfil()).into(holder.imgDoctor);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(doctor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctores.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDoctor;
        TextView tvNombre, tvEspecialidad;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDoctor = itemView.findViewById(R.id.imgDoctorItem);
            tvNombre = itemView.findViewById(R.id.tvNombreDoctorItem);
            tvEspecialidad = itemView.findViewById(R.id.tvEspecialidadItem);
        }
    }
}
