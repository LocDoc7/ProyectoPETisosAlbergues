package com.ay.proyectopetisosalbergue.TabItems.Juegos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.ay.proyectopetisosalbergue.R;

public class Coin {
    public int speed = 7;
    public boolean wasClaim = true;
    int x,y, width, height;
    Bitmap coin;

    Coin (Resources res){
        coin = BitmapFactory.decodeResource(res, R.drawable.collarcoin);

        width = coin.getWidth();
        height = coin.getHeight();

        width /= 2;
        height /= 2;

        /*width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);*/

        coin = Bitmap.createScaledBitmap(coin,width,height, false);
        y = -height;
    }

    Rect getCollisionShape(){
        return new Rect(x,y,x + width, y + height);
    }
}
