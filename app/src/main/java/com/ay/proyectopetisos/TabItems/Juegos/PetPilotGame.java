package com.ay.proyectopetisos.TabItems.Juegos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ay.proyectopetisos.R;
import com.ay.proyectopetisos.Util.Util;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class PetPilotGame extends AppCompatActivity {
    StringRequest stringRequest;
    RequestQueue requestQueue;
    Button btnJugar;
    TextView tvScoreMaximo;
    ImageView imgMute;
    private View acitity;
    private View decorView;
    private SharedPreferences preferences;
    private boolean ismute,ischange = false;
    private InterstitialAd mInterstitialAd;
    AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_pilot_game);
        btnJugar = findViewById(R.id.btn_jugarPilot);
        acitity = findViewById(R.id.activity_main_game);
        requestQueue = Volley.newRequestQueue(this);
        tvScoreMaximo = findViewById(R.id.tv_scoreingamepilot);
        imgMute = findViewById(R.id.btn_mute);
        decorView = this.getWindow().getDecorView();
        preferences = getSharedPreferences("preferencias", Context.MODE_PRIVATE);
        int scoreMax = preferences.getInt("cantScore",0);
        tvScoreMaximo.setText(String.valueOf(scoreMax));
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnJugar.setEnabled(false);
                crearAnuncio();
                /*startActivity(new Intent(PetPilotGame.this,GameActivity.class));
                finish();*/
            }
        });
        ismute =  preferences.getBoolean("isMute",false);
        if (ismute)
            imgMute.setImageResource(R.drawable.ic_volumen);
        else
            imgMute.setImageResource(R.drawable.ic_volumenmute);

        imgMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ismute = !ismute;
                if (ismute)
                    imgMute.setImageResource(R.drawable.ic_volumen);
                else
                    imgMute.setImageResource(R.drawable.ic_volumenmute);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isMute",ismute);
                editor.commit();
            }
        });
        ischange = preferences.getBoolean("scoreUp",false);
        if (ischange){
            actualizarScoreCollares();
        }
        hideSystemUI();
    }

    private void crearAnuncio() {
        adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NotNull LoadAdError loadAdError) {
                Log.d(TAG, loadAdError.toString());
                mInterstitialAd = null;
                startActivity(new Intent(PetPilotGame.this,GameActivity.class));
                finish();
                btnJugar.setEnabled(true);
            }

            @Override
            public void onAdLoaded(@NotNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdClicked() {
                        Log.d(TAG, "Ad was clicked.");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad dismissed fullscreen content.");
                        mInterstitialAd = null;
                        startActivity(new Intent(PetPilotGame.this,GameActivity.class));
                        finish();
                        btnJugar.setEnabled(true);
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull @NotNull AdError adError) {
                        Log.e(TAG, "Ad failed to show fullscreen content.");
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdImpression() {
                        Log.d(TAG, "Ad recorded an impression.");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.");
                    }
                });
                mostrarAnuncio();
            }
        });

    }
    private void mostrarAnuncio(){
        if (mInterstitialAd != null){
            mInterstitialAd.show(this);
        }else crearAnuncio();
    }

    private void actualizarScoreCollares() {
        int id_persona = preferences.getInt("idUsuario",0);
        int scorefinal = preferences.getInt("cantScore",0);
        int collares = preferences.getInt("cantCollares",0);
        String url = Util.RUTA + "actualizar_score_collares.php";
        url = url.replace(" ", "%20");
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("scoreUp",false);
                editor.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(acitity.getContext(), "Error al actualizar" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("id_persona",String.valueOf(id_persona));
                params.put("per_score",String.valueOf(scorefinal));
                params.put("per_collares",String.valueOf(collares));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateForOrientation(newConfig.orientation);
    }

    private void updateForOrientation(int orientation) {
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "Horizontal", Toast.LENGTH_SHORT).show();
            hideSystemUI();
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            showSystemUI();
        }
    }

    private void showSystemUI() {
        acitity.setFitsSystemWindows(true);
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_VISIBLE);
    }

    private void hideSystemUI() {

        acitity.setFitsSystemWindows(false);
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}