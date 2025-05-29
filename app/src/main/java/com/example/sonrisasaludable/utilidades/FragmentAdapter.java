package com.example.sonrisasaludable.utilidades;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sonrisasaludable.fragmentos.ConfiguracionFragment;
import com.example.sonrisasaludable.fragmentos.DentistasFragment;
import com.example.sonrisasaludable.fragmentos.HistorialFragment;
import com.example.sonrisasaludable.fragmentos.MenuFragment;
import com.example.sonrisasaludable.fragmentos.Cita;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0: return new MenuFragment();
            case 1: return new DentistasFragment();
            case 2: return new Cita();
            case 3: return new HistorialFragment();
            case 4: return new ConfiguracionFragment();
            default: return new MenuFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
