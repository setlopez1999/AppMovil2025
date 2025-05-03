package com.example.sonrisasaludable.fragmentos;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.sonrisasaludable.R;

import java.util.LinkedHashMap;
import java.util.Map;

public class FechaFragment extends Fragment {

    private String horarioSeleccionado = "";
    private Button botonSeleccionado;

    public String getHorarioSeleccionado() {
        return horarioSeleccionado;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fecha, container, false);
        GridLayout gridHorarios = view.findViewById(R.id.gridHorarios);

        // Horarios disponibles (puedes adaptar desde API)
        Map<String, Boolean> horariosDisponibles = new LinkedHashMap<>();
        horariosDisponibles.put("9:00", true);
        horariosDisponibles.put("10:00", true);
        horariosDisponibles.put("11:00", false);
        horariosDisponibles.put("12:00", true);
        horariosDisponibles.put("13:00", true);
        horariosDisponibles.put("16:00", true);
        horariosDisponibles.put("17:00", true);
        horariosDisponibles.put("18:00", false);
        horariosDisponibles.put("19:00", true);

        for (Map.Entry<String, Boolean> entry : horariosDisponibles.entrySet()) {
            String hora = entry.getKey();
            boolean disponible = entry.getValue();

            Button button = new Button(getContext());
            button.setText(hora);
            button.setEnabled(disponible);
            button.setAllCaps(false);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(8, 8, 8, 8);
            button.setLayoutParams(params);

            if (disponible) {
                button.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.darker_gray));
                button.setTextColor(Color.BLACK);

                button.setOnClickListener(v -> {
                    if (botonSeleccionado != null) {
                        botonSeleccionado.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.darker_gray));
                    }
                    botonSeleccionado = button;
                    horarioSeleccionado = hora;
                    button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28C076")));
                });

            } else {
                button.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EEEEEE")));
                button.setTextColor(Color.GRAY);
            }

            gridHorarios.addView(button);
        }

        return view;
    }
}
