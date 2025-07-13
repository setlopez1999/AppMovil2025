package com.example.sonrisasaludable.data.models.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.models.ResenaConDetalles;
import java.util.List;

public class ResenasAdapter extends RecyclerView.Adapter<ResenasAdapter.ResenaViewHolder> {
    private List<ResenaConDetalles> resenas;
    private OnResenaClickListener editListener;
    private OnResenaClickListener deleteListener;

    public interface OnResenaClickListener {
        void onClick(int resenaId);
    }

    public ResenasAdapter(List<ResenaConDetalles> resenas, OnResenaClickListener editListener, OnResenaClickListener deleteListener) {
        this.resenas = resenas;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    @Override
    public ResenaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_resena_card, parent, false);
        return new ResenaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResenaViewHolder holder, int position) {
        ResenaConDetalles resena = resenas.get(position);
        
        holder.tvNombrePaciente.setText(resena.getNombrePaciente());
        holder.tvFecha.setText(resena.getFecha().substring(0, 10)); // Solo fecha
        holder.tvComentario.setText(resena.getComentario());
        holder.ratingBar.setRating(resena.getCalificacion());
        
        if (resena.getServicioNombre() != null) {
            holder.tvServicio.setText(resena.getServicioNombre());
            holder.tvServicio.setVisibility(View.VISIBLE);
        } else {
            holder.tvServicio.setVisibility(View.GONE);
        }

        // Ocultar botones si no hay listeners (perfil del doctor)
        if (editListener == null || deleteListener == null) {
            holder.btnEditar.setVisibility(View.GONE);
            holder.btnEliminar.setVisibility(View.GONE);
        } else {
            holder.btnEditar.setOnClickListener(v -> editListener.onClick(resena.getResenaId()));
            holder.btnEliminar.setOnClickListener(v -> deleteListener.onClick(resena.getResenaId()));
        }
    }

    @Override
    public int getItemCount() {
        return resenas.size();
    }

    static class ResenaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePaciente, tvFecha, tvComentario, tvServicio, btnEditar, btnEliminar;
        RatingBar ratingBar;

        ResenaViewHolder(View itemView) {
            super(itemView);
            tvNombrePaciente = itemView.findViewById(R.id.tvNombrePaciente);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvComentario = itemView.findViewById(R.id.tvComentario);
            tvServicio = itemView.findViewById(R.id.tvServicio);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}