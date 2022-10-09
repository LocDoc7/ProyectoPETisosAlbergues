package com.ay.proyectopetisosalbergue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ay.proyectopetisosalbergue.Adapters.TabsAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    TabsAdapter tabsAdapter;
    Dialog Mydialog;

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
                limpiarBackStack();
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

        cargarDialogBienvenida();

    }

    private void limpiarBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        for (int i = 0;i < fm.getBackStackEntryCount();++i){
            fm.popBackStack();
        }
    }
    private void cargarDialogBienvenida() {
        Mydialog = new Dialog(this);
        Mydialog.setContentView(R.layout.dialog_bienvenida);
        Mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Mydialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Mydialog.findViewById(R.id.btn_facebookbienvenida).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "CLick", Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("https://www.facebook.com/Petisos-101614329283353/?ti=as");

                try {
                    ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                    if (applicationInfo.enabled) {
                        uri = Uri.parse("fb://page/101614329283353");
                    }
                } catch (PackageManager.NameNotFoundException ignored) {
                }
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        Mydialog.findViewById(R.id.btnConfirmarDialogBienvenida).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mydialog.dismiss();
            }
        });
        Mydialog.show();
    }
}