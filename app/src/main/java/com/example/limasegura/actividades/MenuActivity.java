package com.example.limasegura.actividades;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.limasegura.R;
import com.example.limasegura.fragmentos.ConfiguracionFragment;
import com.example.limasegura.fragmentos.DentistasFragment;
import com.example.limasegura.fragmentos.HistorialFragment;
import com.example.limasegura.fragmentos.MenuFragment;
import com.example.limasegura.fragmentos.Reporte;

public class MenuActivity extends AppCompatActivity {

    private ImageButton btn1, btn2, btn3, btn4, btn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtener los botones
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        // Cargar el primer fragmento al inicio
        if (savedInstanceState == null) {
            loadFragment(new MenuFragment());
        }

        // Listener para el botón 1
        btn1.setOnClickListener(v -> loadFragment(new ConfiguracionFragment()));

        // Listener para el botón 2
        btn2.setOnClickListener(v -> loadFragment(new MenuFragment()));

        // Listener para el botón 3
        btn3.setOnClickListener(v -> loadFragment(new HistorialFragment()));

        // Listener para el botón 4
        btn4.setOnClickListener(v -> loadFragment(new Reporte()));

        // Listener para el botón 5
        btn5.setOnClickListener(v -> loadFragment(new DentistasFragment()));
    }

    // Método para cargar un fragmento
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // 'fragment_container' es el contenedor donde se reemplazarán los fragmentos
        transaction.addToBackStack(null); // Agrega la transacción al back stack para poder usar el botón de atrás
        transaction.commit();
    }


}