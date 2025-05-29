package com.example.sonrisasaludable.actividades;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.sonrisasaludable.R;
import com.example.sonrisasaludable.utilidades.DoctorFragmentAdapter;

public class MenuDoctorActivity extends AppCompatActivity {

    private ImageButton btn1, btn2, btn3, btn4, btn5;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_doctor);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);

        viewPager = findViewById(R.id.fragment_container);
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
