package com.example.sonrisasaludable.utilidades;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sonrisasaludable.fragmentos.DHomeFragment;
import com.example.sonrisasaludable.fragmentos.DPerfilFragment;
import com.example.sonrisasaludable.fragmentos.DHorarioFragment;
import com.example.sonrisasaludable.fragmentos.DHistorialCitasFragment;
import com.example.sonrisasaludable.fragmentos.DAjustesFragment;

public class DoctorFragmentAdapter extends FragmentStateAdapter {

    public DoctorFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new DHomeFragment();
            case 1: return new DPerfilFragment();
            case 2: return new DHorarioFragment();
            case 3: return new DHistorialCitasFragment();
            case 4: return new DAjustesFragment();
            default: return new DHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
