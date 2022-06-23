package com.ay.proyectopetisos.TabItems.Juegos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.ay.proyectopetisos.R;

import static com.ay.proyectopetisos.TabItems.Juegos.GameView.screenRatioX;
import static com.ay.proyectopetisos.TabItems.Juegos.GameView.screenRatioY;

public class Cloud {
    public int speed = 5;
    int x,y, width, height;
    Bitmap cloud;

    Cloud (Resources res){
        cloud = BitmapFactory.decodeResource(res, R.drawable.cloud);

        width = cloud.getWidth();
        height = cloud.getHeight();

        /*width /= 1.5;
        height /= 1.5;*/

        width = (int) (width * screenRatioX);
        height = (int) (height * screenRatioY);

        cloud = Bitmap.createScaledBitmap(cloud,width,height, false);
        y = -height;
    }

    Rect getCollisionShape(){
        return new Rect(x,y,x + width, y + height);
    }
}
