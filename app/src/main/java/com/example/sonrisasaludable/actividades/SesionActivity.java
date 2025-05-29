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

import com.example.sonrisasaludable.R;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class SesionActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://10.0.2.2:3000/GOGOGO/"; //IP DEL SERVIDOR QUE USAREMOS CON NODE ETC xd
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

            // Validar si amboas estan vacios
            if (correo.isEmpty() || clave.isEmpty()) {
                Toast.makeText(SesionActivity.this,
                        "Por favor ingrese ambos campos", Toast.LENGTH_SHORT).show();
                return;
                //Aqui el redirect lo puse para probar pero normalmento esto solo manda Return
                //redirectUser("doctor");

            }

            loginUser(correo, clave);
        });

        // Registro
        lblRegistro.setOnClickListener(v -> {
            Intent intent = new Intent(SesionActivity.this, RegistroActivity.class);
            startActivity(intent);
            finish();
        });



        // Salimos
        Button btnSalir = findViewById(R.id.sesBtnSalir);
        btnSalir.setOnClickListener(v -> finish());
    }

    // SOLICITUD AL BACKENNND
    private void loginUser(String correo, String clave) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = "{\"correo\":\"" + correo + "\", \"contraseña\":\"" + clave + "\"}";

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + "login")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(SesionActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // sacamos los datos de la respuesta
                    String jsonResponse = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        String token = jsonObject.getString("token");
                        String rol = jsonObject.getString("rol");

                        // guardar en las preferencias
                        saveToken(token, rol);

                        // Redirigir al usuario por el roleo
                        redirectUser(rol);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> Toast.makeText(SesionActivity.this, "Error de respuesta del servidor", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(SesionActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }


    // TODO ESTO SOLO SI ES NECESARIO GUARDARLO DE AHI LO VEMOS

    // Guardamos el token y rol en SharedPreferences
    private void saveToken(String token, String rol) {
        SharedPreferences sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.putString("rol", rol);
        editor.apply();
    }

    // abrir actividad correspondientea
    private void redirectUser(String rol) {
        Intent intent = null;
        if (chkRecordar.isChecked()) {
            //si esta tocado, guardamos su cuenta en la bd local
        }

        switch (rol) {
            case "admin":
                //intent = new Intent(SesionActivity.this, AdminActivity.class);
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
}
