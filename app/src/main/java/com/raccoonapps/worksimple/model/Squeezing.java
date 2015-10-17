package com.raccoonapps.worksimple.model;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Squeezing {
    private Squeezing() {
    }

    public static double occupy = 100;

    private static double widthGirl;
    private static double heightGirl;
    public static BitmapDrawable imageGirl;

    public static void squeezingPercentage(BitmapDrawable drawable, double widthScreen, double heightScreen) {
        imageGirl = drawable;
        widthGirl = drawable.getIntrinsicWidth();
        heightGirl = drawable.getIntrinsicHeight();

        double occupyY = (80 * heightScreen) / 100;
        double occupyX = (80 * widthScreen) / 100;
        Log.d("OCCUPY", "occupyY = " + occupyY);
        Log.d("OCCUPY", "occupyX = " + occupyX);
        Log.d("OCCUPY", "heightGirl = " + heightGirl);
        Log.d("OCCUPY", "widthGirl = " + widthGirl);

        if (heightGirl > occupyY) {
            occupyY = heightGirl - occupyY;
            occupyY = (occupyY * 100) / heightGirl;
            Log.d("OCCUPY", "LOL");
        } else {
            occupyY = 0;
        }

        if (widthGirl > occupyX) {
            occupyX = widthGirl - occupyX;
            occupyX = (occupyX * 100) / widthGirl;
            Log.d("OCCUPY", "BLA");
        } else {
            occupyX = 0;
        }

        if (occupyY >= occupyX) {
            occupy = 100 - occupyY;
            Log.d("OCCUPY", "1");
        }
        if (occupyY <= occupyX) {
            occupy = 100 - occupyX;
            Log.d("OCCUPY", "2");
        }

        if (occupyX == 0) {
            occupy = 100;
        }

        Log.d("OCCUPY", "occupy = " + occupy);

    }

    public static int occupyWidthGirl() {
        return (int) ((occupy * widthGirl) / 100);
    }

    public static int occupyHeightGirl() {
        return (int) ((occupy * heightGirl) / 100);
    }

    public static BitmapDrawable getImageGirl() {
        return imageGirl;
    }
}
