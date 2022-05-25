package com.ay.proyectopetisos.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ay.proyectopetisos.TabItems.Donacion.DonacionesFragment;
import com.ay.proyectopetisos.TabItems.Inicio.InicioFragment;
import com.ay.proyectopetisos.TabItems.Juegos.JuegosFragment;
import com.ay.proyectopetisos.TabItems.Notificaciones.NotificacionesFragment;
import com.ay.proyectopetisos.TabItems.Opciones.OpcionesFragment;
import com.ay.proyectopetisos.TabItems.Tienda.TiendaFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TabsAdapter extends FragmentPagerAdapter {
    int numoftabs;
    public TabsAdapter(@NonNull @NotNull FragmentManager fm, int numoftabs) {
        super(fm);
        this.numoftabs = numoftabs;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new InicioFragment();
                break;
            case 1:
                fragment = new DonacionesFragment();
                break;
            case 2:
                fragment = new JuegosFragment();
                break;
            case 3:
                fragment = new TiendaFragment();
                break;
            case 4:
                fragment = new NotificacionesFragment();
                break;
            case 5:
                fragment = new OpcionesFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return numoftabs;
    }

}
