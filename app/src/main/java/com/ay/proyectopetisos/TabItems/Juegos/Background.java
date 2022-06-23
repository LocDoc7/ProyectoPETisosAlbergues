package com.ay.proyectopetisos.TabItems.Juegos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ay.proyectopetisos.R;

public class Background {
    int x = 0,y = 0,backgroundCounter = 1;
    Bitmap background1,background2,background3;
    Background (int screenX, int screenY, Resources res){
        background1 = BitmapFactory.decodeResource(res, R.drawable.background1);
        background2 = BitmapFactory.decodeResource(res, R.drawable.background2);
        background3 = BitmapFactory.decodeResource(res, R.drawable.background3);

        background1 = Bitmap.createScaledBitmap(background1,screenX,screenY,false);
        background2 = Bitmap.createScaledBitmap(background2,screenX,screenY,false);
        background3 = Bitmap.createScaledBitmap(background3,screenX,screenY,false);

    }
    Bitmap getBackground(){
        if (backgroundCounter == 1){
            backgroundCounter++;
            return background1;
        }
        if (backgroundCounter == 2){
            backgroundCounter++;
            return background2;
        }

        backgroundCounter = 1;

        return background3;
    }
}
