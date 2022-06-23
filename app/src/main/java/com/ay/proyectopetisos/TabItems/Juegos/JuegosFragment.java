package com.ay.proyectopetisos.TabItems.Juegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ay.proyectopetisos.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class JuegosFragment extends Fragment {
    int cantCollares,perScore;
    TextView tvCantCollares;
    TextView tvScoreMax;
    Button btnJugar;
    AdView adView;

    public JuegosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_juegos, container, false);
        tvCantCollares = view.findViewById(R.id.tv_canMonedasJuego);
        tvScoreMax = view.findViewById(R.id.tv_scorejuego);
        btnJugar = view.findViewById(R.id.btn_jugar);
        SharedPreferences sp1 = getActivity().getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        cantCollares = sp1.getInt("cantCollares",0);
        perScore = sp1.getInt("cantScore",0);
        tvScoreMax.setText(String.valueOf(perScore));
        tvCantCollares.setText(String.valueOf(cantCollares));
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),PetPilotGame.class);
                startActivity(i);
            }
        });

        adView = (AdView) view.findViewById(R.id.adViewJuego);
        cargarAnuncio();
        return view;
    }

    private void cargarAnuncio() {
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}