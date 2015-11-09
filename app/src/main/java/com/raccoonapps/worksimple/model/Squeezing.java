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

        double occupyY = (50 * heightScreen) / 100;
        double occupyX = (50 * widthScreen) / 100;

        if (heightGirl > occupyY) {
            occupyY = heightGirl - occupyY;
            occupyY = (occupyY * 100) / heightGirl;
        } else {
            occupyY = 0;
        }

        if (widthGirl > occupyX) {
            occupyX = widthGirl - occupyX;
            occupyX = (occupyX * 100) / widthGirl;
        } else {
            occupyX = 0;
        }

        if (occupyY >= occupyX) {
            occupy = 100 - occupyY;
        }
        if (occupyY <= occupyX) {
            occupy = 100 - occupyX;
        }

        if (occupyX == 0) {
            occupy = 100;
        }
    }

    public static int occupyWidthGirl() {
        Log.d("ROOOT", "GW " + ((occupy * widthGirl) / 100));
        return (int) ((occupy * widthGirl) / 100);
    }

    public static int occupyHeightGirl() {
        Log.d("ROOOT", "GH " + ((occupy * heightGirl) / 100));
        return (int) ((occupy * heightGirl) / 100);
    }

    public static int occupyWidthAccessory(BitmapDrawable drawable) {
        return (int) ((occupy * drawable.getIntrinsicWidth()) / 100);
    }

    public static int occupyHeightAccessory(BitmapDrawable drawable) {
        return (int) ((occupy * drawable.getIntrinsicHeight()) / 100);
    }

    public static BitmapDrawable getImageGirl() {
        return imageGirl;
    }
}
