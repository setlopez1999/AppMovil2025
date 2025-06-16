package com.example.sonrisasaludable.actividades;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.data.models.LoginRequest;
import com.example.sonrisasaludable.data.models.LoginResponse;
import com.example.sonrisasaludable.data.network.ApiService;
import com.example.sonrisasaludable.data.network.RetrofitClient;
import com.example.sonrisasaludable.data.worker.UsuarioSyncWorker;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SesionActivity extends AppCompatActivity {

    private CheckBox chkRecordar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);

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
                    String token = response.body().getToken();
                    String rol = response.body().getRol();
                    saveToken(token, rol);
                    redirectUser(rol);
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
            // Aquí puedes implementar guardar cuenta localmente si quieres
        }

        switch (rol) {
            case "admin":
                //intent = new Intent(SesionActivity.this, AdminActivity.class);
                intent = new Intent(SesionActivity.this, MenuAdminActivity.class); // o deja comentado si no tienes
                break;
            case "doctor":
                intent = new Intent(SesionActivity.this, MenuDoctorActivity.class);
                break;
            case "paciente":
                intent = new Intent(SesionActivity.this, MenuUserActivity.class);
                break;
            default:
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
