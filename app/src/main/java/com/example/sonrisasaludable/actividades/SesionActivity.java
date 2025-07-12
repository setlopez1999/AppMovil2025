package com.example.sonrisasaludable.actividades;

import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.database.AppDatabase;
import com.example.sonrisasaludable.data.entidades.DoctorEntity;
import com.example.sonrisasaludable.data.models.*;
import com.example.sonrisasaludable.data.network.*;
import com.example.sonrisasaludable.utilidades.SessionManager;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.*;


public class SesionActivity extends AppCompatActivity {

    private CheckBox chkRecordar;
    // mi solteron
    SessionManager sesion ;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);
        sesion = SessionManager.getInstance(this);
        database = AppDatabase.getInstance(getApplicationContext());
        EditText edtCorreo = findViewById(R.id.sesTxtCorreo);
        EditText edtClave = findViewById(R.id.sesTxtClave);
        Button btnIngresar = findViewById(R.id.sesBtnIngresar);
        chkRecordar = findViewById(R.id.sesChkRecordar);
        TextView lblRegistro = findViewById(R.id.sesLblRegistro);

        btnIngresar.setOnClickListener(v -> {
            String correo = edtCorreo.getText().toString().trim();
            String clave = edtClave.getText().toString().trim();

            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(SesionActivity.this, "Por favor ingrese ambos campos", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(correo, clave);
        });

        lblRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(SesionActivity.this, RegistroActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnSalir = findViewById(R.id.sesBtnSalir);
        btnSalir.setOnClickListener(v -> finish());


    }
    private void loginUser(String correo, String clave) {
        ApiService apiService = RetrofitClient.getApiService();

        LoginRequest loginRequest = new LoginRequest(correo, clave);
        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    //Al parecer usar el objeto SessionMannager hace que afuerzas
                    // le tenga que meter try catch a todo xd
                    // Nota despues investigar porque
                    try {
                        guardardatos(response);
                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    redirectUser(sesion.getRole());
                } else {
                    Toast.makeText(SesionActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SesionActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void guardardatos(Response<LoginResponse> response) throws GeneralSecurityException, IOException {

        String token = response.body().getAuthToken();
        int id = response.body().getUserId();
        String rol = response.body().getUserRole();
        String name = response.body().getUserName();
        String email = response.body().getUserEmail();
        String photo = response.body().getUserPhoto();
        boolean status = response.body().isLoggedIn(); //Este ta por la puras xdddd
        sesion.saveSession(token,id,rol,name,email,photo);

        int id_doctor = -1;
        if (rol.equals("doctor")) {
            ExecutorService serv = Executors.newSingleThreadExecutor();
            serv.execute(() -> {
                DoctorEntity doctor = database.doctorDao().getByUsuarioId(id);
                int idDoctor = (doctor != null) ? doctor.getId() : -1;
                sesion.saveDoctorId(idDoctor);
                Log.d("SesionActivity", "Doctor ID guardado: " + idDoctor + "Con id user de :" + id);
            });
        }
    }


    //DEPRECADO
    private void saveToken(String token, String rol) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("rol", rol);
        editor.apply();
    }
    private void redirectUser(String rol) {
        Intent intent;
        if (chkRecordar.isChecked()) {
            // Futuro
        }

        switch (rol) {
            case "admin":
                intent = new Intent(SesionActivity.this, MenuAdminActivity.class); // o deja comentado si no tienes
                break;
            case "doctor":
                intent = new Intent(SesionActivity.this, MenuDoctorActivity.class);
                break;
            case "paciente":
                intent = new Intent(SesionActivity.this, MenuUserActivity.class);
                break;
            default:
                mostrar("PASA POR DEFAULT");
                intent = new Intent(this, SesionActivity.class);
                break;
        }
        startActivity(intent);
        finish();



    }
    public void mostrar(String mensaje){
        Toast.makeText(SesionActivity.this, mensaje, Toast.LENGTH_SHORT).show();
    }

}
