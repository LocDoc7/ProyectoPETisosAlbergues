package com.ay.proyectopetisosalbergue.TabItems.Juegos;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ay.proyectopetisosalbergue.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class JuegosFragment extends Fragment {
    int cantCollares,perScore;
    CardView cardJuego;
    TextView tvCantCollares;
    TextView tvScoreMax;
    Button btnJugar;
    AdView adView;
    Dialog Mydialog;

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
        cardJuego = view.findViewById(R.id.card_juego1);
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
        cardJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarDialogInfoJuego();
            }
        });

        adView = (AdView) view.findViewById(R.id.adViewJuego);
        cargarAnuncio();
        return view;
    }

    private void cargarDialogInfoJuego() {
        Mydialog = new Dialog(getContext());
        Mydialog.setContentView(R.layout.dialog_info);
        ((TextView) Mydialog.findViewById(R.id.tvTittleInfo)).setText("PILOT PET");
        ((TextView) Mydialog.findViewById(R.id.tvMensajeInfo)).setText("PilotPet es un viedeojuego basado en Flappy Bird con disparos. Ayuda a Kevin a prevenir una invasión de pájaros y recolecta monedas para grandes recompensas.");
        ((Button) Mydialog.findViewById(R.id.btnConfirmarDialogInfo)).setText("Lo tengo");
        ((ImageView) Mydialog.findViewById(R.id.img_juegoInfo)).setImageResource(R.drawable.demopetpilotgame);
        Mydialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Mydialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Mydialog.findViewById(R.id.btnConfirmarDialogInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mydialog.dismiss();
            }
        });
        Mydialog.show();

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