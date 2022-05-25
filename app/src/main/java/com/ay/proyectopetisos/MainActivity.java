package com.ay.proyectopetisos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.ay.proyectopetisos.Adapters.TabsAdapter;
import com.ay.proyectopetisos.TabItems.Donacion.DonacionesFragment;
import com.ay.proyectopetisos.TabItems.Inicio.InicioFragment;
import com.ay.proyectopetisos.TabItems.Juegos.JuegosFragment;
import com.ay.proyectopetisos.TabItems.Notificaciones.NotificacionesFragment;
import com.ay.proyectopetisos.TabItems.Opciones.OpcionesFragment;
import com.ay.proyectopetisos.TabItems.Tienda.TiendaFragment;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    TabsAdapter tabsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_menu_pricipal);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_inicio));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_donacion));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_play));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_tienda));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_notificaciones));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_opciones));
        tabsAdapter = new TabsAdapter(this.getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    tabsAdapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 1) {
                    tabsAdapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 2) {
                    tabsAdapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 3) {
                    tabsAdapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 4) {
                    tabsAdapter.notifyDataSetChanged();
                }
                if (tab.getPosition() == 5) {
                    tabsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}