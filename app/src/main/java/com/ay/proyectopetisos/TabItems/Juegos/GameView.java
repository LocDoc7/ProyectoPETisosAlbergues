package com.ay.proyectopetisos.TabItems.Juegos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable{
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int screenX,screenY, score = 0,level=0,cantCollaresAtrapados = 0;
    public static float screenRatioX, screenRatioY;
    private Paint paint;
    private Bird[] birds;
    private Cloud[] clouds;
    private Coin[] coins;
    private Random random;
    private SoundPool soundPool;
    private SharedPreferences preferences;
    private List<Bullet> bullets;
    private int sound,soundCoin,sounGameOver;
    private Flight flight;
    private GameActivity activity;
    private Background background1;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);
        this.activity = activity;

        preferences = activity.getSharedPreferences("preferencias",Context.MODE_PRIVATE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);

        sound = soundPool.load(activity, R.raw.disparo, 1);
        soundCoin = soundPool.load(activity, R.raw.coinclaim, 1);
        sounGameOver = soundPool.load(activity,R.raw.gameover,1);
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX,screenY,getResources());
        flight = new Flight(this,screenY,getResources());
        bullets = new ArrayList<>();
        paint = new Paint();
        paint.setTextSize(90);
        paint.setColor(Color.BLACK);
        birds = new Bird[4];
        for (int i = 0; i < 4; i++){
            Bird bird = new Bird(getResources());
            birds[i] = bird;
        }
        coins = new Coin[1];
        for (int i = 0; i < 1; i++){
            Coin coin = new Coin(getResources());
            coins[i] = coin;
        }
        clouds = new Cloud[1];
        for (int i = 0; i < 1; i++){
            Cloud cloud = new Cloud(getResources());
            clouds[i] = cloud;
        }
        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(17);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private void update() {
        if (flight.isGoingUp)
            flight.y -= 30 * screenRatioY;
        else if (flight.isGoingDown)
            flight.y += 30 * screenRatioY;

        if (flight.y < 0)
            flight.y = 0;
        if (flight.y >= screenY - flight.height)
            flight.y = screenY - flight.height;

        List<Bullet> trash =new ArrayList<>();
        List<Cloud> trashCloud =new ArrayList<>();
        List<Coin> trashCoin =new ArrayList<>();

        for (Bullet bullet : bullets){
            if (bullet.x > screenX)
                trash.add(bullet);
            bullet.x += 50 * screenRatioX;

            for (Bird bird : birds){
                if (Rect.intersects(bird.getCollisionShape(), bullet.getCollisionShape())){
                    score++;
                    level++;
                    bird.x = - 500;
                    bullet.x = screenX + 500;
                    bird.wasShot = true;
                }
            }
        }
        for (Bullet bullet : trash)
            bullets.remove(bullet);
        for (Cloud cloud : clouds){
            cloud.x -= cloud.speed;
            if (cloud.x < screenX)
                trashCloud.add(cloud);
            //cloud.x -= 50 * screenRatioX;

            if (cloud.x + cloud.width < 0){
                cloud.x = screenX;
                cloud.y = random.nextInt(screenY - cloud.height);
            }

            /*if (Rect.intersects(cloud.getCollisionShape(),flight.getCollisionShape())){
                isGameOver = true;
                return;
            }*/
        }
        for (Cloud cloud : trashCloud)
            bullets.remove(cloud);

        for (Coin coin : coins){
            coin.x -= coin.speed;
            if (coin.x < screenX)
                trashCoin.add(coin);
            //cloud.x -= 50 * screenRatioX;

            if (coin.x + coin.width < 0){
                coin.x = screenX;
                coin.y = random.nextInt(screenY - coin.height);
            }

            if (Rect.intersects(coin.getCollisionShape(),flight.getCollisionShape())){
                getSoundCoinClaim();
                cantCollaresAtrapados++;
                coin.x = - 500;
                trashCoin.add(coin);
            }
        }
        for (Coin coin : trashCoin)
            bullets.remove(coin);

        for (Bird bird : birds){
            bird.x -= bird.speed;
            if (bird.x + bird.width < 0){
                if (!bird.wasShot){
                    isGameOver = true;
                    return;
                }
                int bound = (int) (1.5 * screenRatioX);
                bird.speed = random.nextInt(bound);
                if (bird.speed < 10 * screenRatioX)
                    bird.speed = (int) (10 * screenRatioX);
                bird.x = screenX;
                bird.y = random.nextInt(screenY - bird.height);
                bird.wasShot = false;
            }
            if (Rect.intersects(bird.getCollisionShape(),flight.getCollisionShape())){
                isGameOver = true;
                return;
            }
        }

    }

    private void draw() {

        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            if (level<=30){
                canvas.drawBitmap(background1.background1,0,0,paint);
            }else if (level<=50){
                canvas.drawBitmap(background1.background2,0,0,paint);
            }else if (level<100){
                canvas.drawBitmap(background1.background3,0,0,paint);
            }else if (level==100){
                level = 0;
            }

            canvas.drawText("Score: "+score,0,100,paint);
            for (Bird bird : birds){
                canvas.drawBitmap(bird.getBird(),bird.x,bird.y,paint);
            }
            for (Cloud cloud : clouds){
                canvas.drawBitmap(cloud.cloud,cloud.x,cloud.y,paint);
            }
            for (Coin coin : coins){
                canvas.drawBitmap(coin.coin,coin.x,coin.y,paint);
            }

            if (isGameOver){
                gameOverSound();
                isPlaying = false;
                canvas.drawBitmap(flight.getDead(),flight.x,flight.y,paint);
                getHolder().unlockCanvasAndPost(canvas);
                saveIfHightScore();
                waitBeforeWExiting();
                return;
            }

            canvas.drawBitmap(flight.getFlight(),flight.x,flight.y,paint);

            for (Bullet bullet : bullets)
                canvas.drawBitmap(bullet.bullet, bullet.x, bullet.y, paint);

            getHolder().unlockCanvasAndPost(canvas);
        }

    }



    private void waitBeforeWExiting() {
        try {
            Thread.sleep(3000);
            activity.startActivity(new Intent(activity,PetPilotGame.class));
            activity.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHightScore() {
        boolean isChange = false;
        SharedPreferences.Editor editor = preferences.edit();
        int cantColares = preferences.getInt("cantCollares",0);
        if (preferences.getInt("cantScore",0)<score){
            editor.putInt("cantScore",score);
            isChange = true;
        }
        if (cantCollaresAtrapados > 0){
            editor.putInt("cantCollares",cantColares + cantCollaresAtrapados);
            isChange = true;
        }
        editor.putBoolean("scoreUp",isChange);
        editor.commit();
    }

    public void resume(){
        isPlaying=true;
        thread = new Thread(this);
        thread.start();
    }
    public void pause(){
        try {
            isPlaying = false;
            thread.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                if (event.getX() < screenX / 2 && event.getY() < screenY/2){
                    flight.isGoingUp = true;
                }
                if (event.getX() < screenX / 2 && event.getY() > screenY/2){
                    flight.isGoingDown = true;
                }
                break;
            }

            case  MotionEvent.ACTION_UP:{
                flight.isGoingUp = false;
                flight.isGoingDown = false;
                if (event.getX() > screenX / 2){
                    flight.toShoot++;
                }
                break;
            }
        }

        return true;
    }

    public void newBullet() {
        if (preferences.getBoolean("isMute",false))
            soundPool.play(sound,1,1,0,0,1);

        Bullet bullet = new Bullet(getResources());
        bullet.x = flight.x + flight.width;
        bullet.y = flight.y + (flight.height / 2);
        bullets.add(bullet);
    }
    private void getSoundCoinClaim() {
        if (preferences.getBoolean("isMute",false))
            soundPool.play(soundCoin,1,1,0,0,1);
    }
    private void gameOverSound() {
        if (preferences.getBoolean("isMute",false))
            soundPool.play(sounGameOver,1,1,0,0,1);
    }
}