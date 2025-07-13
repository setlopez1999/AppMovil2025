package com.example.sonrisasaludable.utilidades;

import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    
    public static void applyTheme(Context context) {
        SessionManager sessionManager = SessionManager.getInstance(context);
        if (sessionManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    
    public static void toggleTheme(Context context) {
        SessionManager sessionManager = SessionManager.getInstance(context);
        boolean currentMode = sessionManager.isDarkMode();
        sessionManager.setDarkMode(!currentMode);
        applyTheme(context);
    }
}