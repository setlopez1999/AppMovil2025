package com.example.sonrisasaludable.utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.sonrisasaludable.data.dao.DoctorDao;
import com.example.sonrisasaludable.data.database.AppDatabase;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class SessionManager {


    private static SessionManager instancia;
    private SharedPreferences prefs;
    private static final String PREF_NAME = "secure_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROLE = "user_role";
    private static final String KEY_NAME = "user_name";
    private static final String KEY_EMAIL = "user_email";
    private static final String KEY_PHOTO = "user_photo";
    private static final String KEY_LOGGED_IN = "is_logged_in";
    private static final String KEY_DOCTOR_ID = "doctor_id";

    //Construcctor PRIVADOOOOOOOOOOOOO   porque me muestra rojo xddd
    private SessionManager(Context context) {
        try {
            prefs = EncryptedSharedPreferences.create(
                    "secure_prefs",
                    MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
                    context.getApplicationContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //METODITO para llamar la instancia
    public static synchronized SessionManager getInstance(Context context) {
        if (instancia == null) {
            instancia = new SessionManager(context.getApplicationContext());
        }
        return instancia;
    }

    //Guardar Session
    public void saveSession(String token, int userId, String role, String name, String email, String photoUrl) {
        prefs.edit()
                .putString(KEY_TOKEN, token)
                .putInt(KEY_USER_ID, userId)
                .putString(KEY_ROLE, role)
                .putString(KEY_NAME, name)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PHOTO, photoUrl)
                .putBoolean(KEY_LOGGED_IN, true)
                .putInt(KEY_DOCTOR_ID,-1)
                .apply();
    }

    public String getToken() { return prefs.getString(KEY_TOKEN, null); }
    public int getUserId() { return prefs.getInt(KEY_USER_ID, -1); }

    public int getDoctorId() { return prefs.getInt(KEY_DOCTOR_ID, -1); }
    public String getRole() { return prefs.getString(KEY_ROLE, null); }
    public String getUserName() { return prefs.getString(KEY_NAME, ""); }
    public String getEmail() { return prefs.getString(KEY_EMAIL, ""); }
    public String getPhotoUrl() { return prefs.getString(KEY_PHOTO, ""); }

    public boolean isLoggedIn() { return prefs.getBoolean(KEY_LOGGED_IN, false); }

    public void clearSession() {
        prefs.edit().clear().apply();
    }

    public void saveDoctorId(int id){
        //buscamos el id de doctor por id usuario
        prefs.edit()
                .putInt(KEY_DOCTOR_ID, id)
                .apply();
    }
}
