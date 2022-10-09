package com.ay.proyectopetisosalbergue.TabItems.Juegos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.ay.proyectopetisosalbergue.R;

import static com.ay.proyectopetisosalbergue.TabItems.Juegos.GameView.screenRatioX;
import static com.ay.proyectopetisosalbergue.TabItems.Juegos.GameView.screenRatioY;

public class Bird {
    public int speed = 5;
    public boolean wasShot = true;
    int x,y, width, height, birdCounter = 1;
    Bitmap bird1,bird2,bird3,bird4;

    Bird (Resources res){
        bird1 = BitmapFactory.decodeResource(res, R.drawable.bird1);
        bird2 = BitmapFactory.decodeResource(res, R.drawable.bird2);
        bird3 = BitmapFactory.decodeResource(res, R.drawable.bird3);
        bird4 = BitmapFactory.decodeResource(res, R.drawable.bird4);

        width = bird1.getWidth();
        height = bird1.getHeight();

        /*width /= 1.5;
        height /= 1.5;*/

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        bird1 = Bitmap.createScaledBitmap(bird1,width,height, false);
        bird2 = Bitmap.createScaledBitmap(bird2,width,height, false);
        bird3 = Bitmap.createScaledBitmap(bird3,width,height, false);
        bird4 = Bitmap.createScaledBitmap(bird4,width,height, false);
        y = -height;
    }
    Bitmap getBird(){
        if (birdCounter == 1){
            birdCounter++;
            return bird1;
        }
        if (birdCounter == 2){
            birdCounter++;
            return bird2;
        }
        if (birdCounter == 3){
            birdCounter++;
            return bird3;
        }

        birdCounter = 1;

        return bird4;
    }

    Rect getCollisionShape(){
        return new Rect(x,y,x + width, y + height);
    }

}