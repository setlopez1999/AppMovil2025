package com.example.sonrisasaludable.actividades;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.fragmentos.EditPerfilDoctor;
import com.example.sonrisasaludable.interfaces.OnEditarPerfilDoctorListener;
import com.example.sonrisasaludable.utilidades.DoctorFragmentAdapter;

public class MenuDoctorActivity extends AppCompatActivity implements OnEditarPerfilDoctorListener {

    private ImageButton btn1, btn2, btn3, btn4, btn5;
    private ViewPager2 viewPager;
    private View fragmentOverlayContainer;

    public void mostrarEditPerfilDoctorFragment() {
        viewPager.setVisibility(View.GONE); // Oculta el ViewPager
        fragmentOverlayContainer.setVisibility(View.VISIBLE); // Muestra el contenedor overlay

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentOverlayContainer, new EditPerfilDoctor())
                .addToBackStack(null)
                .commit();
    }
    public void volverAViewPager() {
        getSupportFragmentManager().popBackStack(); // Quita el fragmento overlay
        fragmentOverlayContainer.setVisibility(View.GONE); // Oculta overlay
        viewPager.setVisibility(View.VISIBLE); // Vuelve a mostrar el ViewPager
    }
    @Override
    public void irAEditarPerfilDoctor() {
        mostrarEditPerfilDoctorFragment();
    }

    @Override
    public void onCancelarEdicion() {
        volverAViewPager();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_doctor);
        fragmentOverlayContainer = findViewById(R.id.fragmentOverlayContainer);

        // Cambiamos el colorsito bonito
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.subsecundario));


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        viewPager = findViewById(R.id.viewPagerDoctor);
        DoctorFragmentAdapter adapter = new DoctorFragmentAdapter(this);
        viewPager.setAdapter(adapter);

        btn1.setOnClickListener(v -> viewPager.setCurrentItem(0, true));
        btn2.setOnClickListener(v -> viewPager.setCurrentItem(1, true));
        btn3.setOnClickListener(v -> viewPager.setCurrentItem(2, true));
        btn4.setOnClickListener(v -> viewPager.setCurrentItem(3, true));
        btn5.setOnClickListener(v -> viewPager.setCurrentItem(4, true));

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Cambiar estilos si deseas
            }
        });
    }
}
