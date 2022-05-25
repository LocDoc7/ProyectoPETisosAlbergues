package com.ay.proyectopetisos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.ay.proyectopetisos.ui.Login.LoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class splash extends AppCompatActivity {

    LottieAnimationView gifpet;
    ImageView namesplash;
    Animation animAlpha;
    Timer timer = new Timer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        }

        namesplash = findViewById(R.id.nameslash);
        gifpet = findViewById(R.id.gifpet);

        animAlpha = AnimationUtils.loadAnimation(this,R.anim.alpha);
        namesplash.startAnimation(animAlpha);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(splash.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        },3000);
    }
}