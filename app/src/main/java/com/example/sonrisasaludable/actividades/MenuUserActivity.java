package com.example.sonrisasaludable.actividades;

import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.utilidades.FragmentAdapter;

public class MenuUserActivity extends AppCompatActivity {

    private ImageButton btn1, btn2, btn3, btn4, btn5;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_user);
        //Colores pacientitos
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.primario));


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        viewPager = findViewById(R.id.fragment_container);
        FragmentAdapter adapter = new FragmentAdapter(this);
        viewPager.setAdapter(adapter);

        // Clicks de los botones cambian el ViewPager
        btn1.setOnClickListener(v -> viewPager.setCurrentItem(0, true));
        btn2.setOnClickListener(v -> viewPager.setCurrentItem(1, true));
        btn3.setOnClickListener(v -> viewPager.setCurrentItem(2, true));
        btn4.setOnClickListener(v -> viewPager.setCurrentItem(3, true));
        btn5.setOnClickListener(v -> viewPager.setCurrentItem(4, true));

        // Opcional: puedes escuchar el cambio de página para cambiar estilos de botones si quieres
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Aquí puedes cambiar el estilo del botón activo si quieres
            }
        });
    }
}