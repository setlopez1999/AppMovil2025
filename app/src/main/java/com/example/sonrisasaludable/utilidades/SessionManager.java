package com.example.sonrisasaludable.utilidades;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import com.example.sonrisasaludable.data.dao.DoctorDao;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.models.UsuarioResponse;

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

    private static final String KEY_USER_DNI = "user_dni";
    private static final String KEY_USER_NOMBRES = "user_nombres";
    private static final String KEY_USER_APELLIDOS = "user_apellidos";
    private static final String KEY_USER_CORREO = "user_correo";
    private static final String KEY_USER_TELEFONO = "user_telefono";
    private static final String KEY_USER_DIRECCION = "user_direccion";
    private static final String KEY_USER_SEXO = "user_sexo";
    private static final String KEY_USER_FECHANAC = "user_fechanacimiento";
    private static final String KEY_USER_FOTO = "user_foto_perfil";

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
    public void saveSession(String token, int userId, String role, String name, String email , String photoUrl) {
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
    public void guardarUsuario(UsuarioResponse usuario) {
        String nuevaFoto = usuario.getFoto_perfil();

        if (nuevaFoto != null && !nuevaFoto.isEmpty()) {
            // Asegura que no se cachee
            nuevaFoto += "?t=" + System.currentTimeMillis();
        } else {
            nuevaFoto = prefs.getString(KEY_USER_FOTO, "");
        }

        prefs.edit()
                .putString(KEY_USER_DNI, usuario.getDni())
                .putString(KEY_USER_NOMBRES, usuario.getNombres())
                .putString(KEY_USER_APELLIDOS, usuario.getApellidos())
                .putString(KEY_USER_CORREO, usuario.getCorreo())
                .putString(KEY_USER_TELEFONO, usuario.getTelefono())
                .putString(KEY_USER_DIRECCION, usuario.getDireccion())
                .putString(KEY_USER_SEXO, usuario.getSexo())
                .putString(KEY_USER_FECHANAC, usuario.getFechanacimiento())
                .putString(KEY_USER_FOTO, nuevaFoto)
                .apply();
    }
    public UsuarioResponse getUsuario() {
        UsuarioResponse u = new UsuarioResponse();
        u.setDni(prefs.getString(KEY_USER_DNI,""));
        u.setNombres(prefs.getString(KEY_USER_NOMBRES, ""));
        u.setApellidos(prefs.getString(KEY_USER_APELLIDOS, ""));
        u.setCorreo(prefs.getString(KEY_USER_CORREO, ""));
        u.setTelefono(prefs.getString(KEY_USER_TELEFONO, ""));
        u.setDireccion(prefs.getString(KEY_USER_DIRECCION, ""));
        u.setSexo(prefs.getString(KEY_USER_SEXO, ""));
        u.setFechanacimiento(prefs.getString(KEY_USER_FECHANAC, ""));
        u.setFoto_perfil(prefs.getString(KEY_USER_FOTO, ""));
        return u;
    }
    public String getToken() { return prefs.getString(KEY_TOKEN, null); }
    public int getUserId() { return prefs.getInt(KEY_USER_ID, -1); }

    public int getDoctorId() { return prefs.getInt(KEY_DOCTOR_ID, -1); }
    public String getRole() { return prefs.getString(KEY_ROLE, null); }
    public String getUserName() { return prefs.getString(KEY_NAME, ""); }
    public String getEmail() { return prefs.getString(KEY_EMAIL, ""); }
    public String getPhotoUrl() { return prefs.getString(KEY_PHOTO, ""); }
    public  String getTelefono(){
        return prefs.getString(KEY_USER_TELEFONO,"948271624");
    }

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
