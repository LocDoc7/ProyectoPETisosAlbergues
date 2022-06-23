package com.ay.proyectopetisos.TabItems.Juegos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.ay.proyectopetisos.R;

import static com.ay.proyectopetisos.TabItems.Juegos.GameView.screenRatioX;
import static com.ay.proyectopetisos.TabItems.Juegos.GameView.screenRatioY;

public class Coin {
    public int speed = 7;
    public boolean wasClaim = true;
    int x,y, width, height;
    Bitmap coin;

    Coin (Resources res){
        coin = BitmapFactory.decodeResource(res, R.drawable.collarcoin);

        width = coin.getWidth();
        height = coin.getHeight();

        width /= 1.5;
        height /= 1.5;

        /*width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);*/

        coin = Bitmap.createScaledBitmap(coin,width,height, false);
        y = -height;
    }

    Rect getCollisionShape(){
        return new Rect(x,y,x + width, y + height);
    }
}
