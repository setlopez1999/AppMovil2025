package com.example.limasegura.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.limasegura.R;

public class SesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sesion);

        // Obtener los elementos de la UI
        EditText edtCorreo = findViewById(R.id.sesTxtCorreo);
        EditText edtClave = findViewById(R.id.sesTxtClave);
        Button btnIngresar = findViewById(R.id.sesBtnIngresar);
        CheckBox chkRecordar = findViewById(R.id.sesChkRecordar);
        TextView lblRegistro = findViewById(R.id.sesLblRegistro);

        // Configuración de padding para el contenido
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        lblRegistro.setOnClickListener(v -> {

            Intent intent = new Intent(SesionActivity.this, RegistroActivity.class);
            startActivity(intent);
            finish();

        });

        // Acción del botón "Ingresar"
        btnIngresar.setOnClickListener(v -> {
            String correo = edtCorreo.getText().toString().trim();
            String clave = edtClave.getText().toString().trim();

            // Validación básica de correo y contraseña
            if (correo.equals("1") && clave.equals("1")) {
                // Si es correcto, ir a la siguiente actividad
                Intent intent = new Intent(SesionActivity.this, MenuActivity.class);
                startActivity(intent);
                finish(); // Opcional: finaliza esta actividad para que no se pueda regresar con el botón de atrás
            } else {
                // Si el usuario o la contraseña son incorrectos
                Toast.makeText(SesionActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        // Acción del botón "Salir"
        Button btnSalir = findViewById(R.id.sesBtnSalir);
        btnSalir.setOnClickListener(v -> {
            // Cerrar la actividad actual (salir de la app o regresar a la actividad anterior)
            finish();
        });

    }
}