package com.example.sonrisasaludable.data.services;

import com.example.sonrisasaludable.data.dao.ResenaDao;
import com.example.sonrisasaludable.data.dao.CitaDao;
import com.example.sonrisasaludable.data.entidades.ResenaEntity;
import com.example.sonrisasaludable.data.entidades.CitaEntity;
import com.example.sonrisasaludable.data.models.ResenaConDetalles;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ResenaService {
    private ResenaDao resenaDao;
    private CitaDao citaDao;

    public ResenaService(ResenaDao resenaDao, CitaDao citaDao) {
        this.resenaDao = resenaDao;
        this.citaDao = citaDao;
    }

    // Crear reseña (solo si la cita está completada y es del paciente)
    public boolean crearResena(int citaId, int pacienteId, int calificacion, String comentario) {
        CitaEntity cita = citaDao.getById(citaId);
        if (cita == null || cita.getUsuario_id() != pacienteId || !cita.getEstado().equals("Completada")) {
            return false;
        }

        // Verificar que no existe ya una reseña para esta cita
        if (resenaDao.getByCitaId(citaId) != null) {
            return false;
        }

        String fechaActual = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        ResenaEntity resena = new ResenaEntity(0, citaId, calificacion, comentario, fechaActual);
        resenaDao.insert(resena);
        return true;
    }

    // Obtener reseñas de un doctor con detalles
    public List<ResenaConDetalles> getResenasByDoctor(int doctorId) {
        return resenaDao.getResenasByDoctorWithDetails(doctorId);
    }

    // Obtener promedio de calificación de un doctor
    public double getPromedioCalificacion(int doctorId) {
        Double promedio = resenaDao.getPromedioCalificacionDoctor(doctorId);
        return promedio != null ? promedio : 0.0;
    }

    // Obtener citas completadas sin reseña para un paciente
    public List<CitaEntity> getCitasSinResena(int pacienteId) {
        return resenaDao.getCitasCompletadasSinResena(pacienteId);
    }

    // Editar reseña (solo el propietario)
    public boolean editarResena(int resenaId, int pacienteId, int nuevaCalificacion, String nuevoComentario) {
        ResenaEntity resena = resenaDao.getById(resenaId);
        if (resena == null) return false;

        if (!resenaDao.puedeEditarResena(resena.getCitaId(), pacienteId)) {
            return false;
        }

        resena.setCalificacion(nuevaCalificacion);
        resena.setComentario(nuevoComentario);
        resenaDao.update(resena);
        return true;
    }

    // Eliminar reseña (solo el propietario)
    public boolean eliminarResena(int resenaId, int pacienteId) {
        ResenaEntity resena = resenaDao.getById(resenaId);
        if (resena == null) return false;

        if (!resenaDao.puedeEditarResena(resena.getCitaId(), pacienteId)) {
            return false;
        }

        resenaDao.delete(resena);
        return true;
    }

    // Obtener estadísticas de un doctor
    public ResenaStats getEstadisticasDoctor(int doctorId) {
        double promedio = getPromedioCalificacion(doctorId);
        int totalResenas = resenaDao.getCountResenasByDoctor(doctorId);
        return new ResenaStats(promedio, totalResenas);
    }

    public static class ResenaStats {
        public final double promedio;
        public final int totalResenas;

        public ResenaStats(double promedio, int totalResenas) {
            this.promedio = promedio;
            this.totalResenas = totalResenas;
        }
    }
}