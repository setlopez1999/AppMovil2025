package com.example.sonrisasaludable.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreferences {

    private static final String PREF_NAME = "user_preferences";
    private final SharedPreferences prefs;

    public UserPreferences(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Tema T oscurito : F clarito xd
    public void setTemaOscuro(boolean oscuro) {
        prefs.edit().putBoolean("tema_oscuro", oscuro).apply();
    }

    public boolean isTemaOscuro() {
        return prefs.getBoolean("tema_oscuro", false); // por defecto claro
    }
    public void setIdioma(String idioma) {
        prefs.edit().putString("idioma", idioma).apply();
    }
    public String getIdioma() {
        return prefs.getString("idioma", "es"); // por defecto español
    }
    public void setNotificacionesActivas(boolean activas) {
        prefs.edit().putBoolean("estado_notificaciones", activas).apply();
    }
    public boolean isNotificacionesActivas() {
        return prefs.getBoolean("estado_notificaciones", true); // activadas por defecto
    }
    // Volumen 0 - 100
    public void setVolumenNotificaciones(int volumen) {
        prefs.edit().putInt("vol_notificaciones", volumen).apply();
    }
    public int getVolumenNotificaciones() {
        return prefs.getInt("vol_notificaciones", 100); // máximo por defecto
    }

    public void setHuellaActiva(boolean activa) {
        prefs.edit().putBoolean("huella_dactilar", activa).apply();
    }
    public boolean isHuellaActiva() {
        return prefs.getBoolean("huella_dactilar", false); // desactivado por defecto
    }

    // Limpiar todo (opcional)
    public void clearAll() {
        prefs.edit().clear().apply();
    }
}
