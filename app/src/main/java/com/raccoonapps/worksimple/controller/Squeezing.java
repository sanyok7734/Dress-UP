package com.raccoonapps.worksimple.controller;

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

        Log.d("KAKOGOX", "widthScreen " + widthScreen);
        Log.d("KAKOGOX", "heightScreen " + heightScreen);
        Log.d("KAKOGOX", "widthGirl " + widthGirl);
        Log.d("KAKOGOX", "heightGirl " + heightGirl);

        double occupyY = (80 * heightScreen) / 100;
        double occupyX = (80 * widthScreen) / 100;

        Log.d("KAKOGOX", "occupyY " + occupyY);
        Log.d("KAKOGOX", "occupyX " + occupyX);

        if (heightGirl > occupyY) {
            occupyY = heightGirl - occupyY;
            occupyY = (occupyY * 100) / heightGirl;
            Log.d("KAKOGOX", "yesY " + occupyY);
        } else {
            occupyY = 0;
            Log.d("KAKOGOX", "noY " + occupyY);
        }

        if (widthGirl > occupyX) {
            occupyX = widthGirl - occupyX;
            occupyX = (occupyX * 100) / widthGirl;
            Log.d("KAKOGOX", "yesX " + occupyX);
        } else {
            occupyX = 0;
            Log.d("KAKOGOX", "noX " + occupyX);
        }

        if (occupyY >= occupyX) {
            occupy = 100 - occupyY;
            Log.d("KAKOGOX", "occupy " + occupy);
        }
        if (occupyY <= occupyX) {
            occupy = 100 - occupyX;
        }

      /*  if (occupyX == 0 && occupyY == 0) {
            occupy = 100;
        }*/
    }

    public static int occupyWidthGirl() {
        Log.d("KAKOGOX", "GW " + ((occupy * widthGirl) / 100));
        Log.d("KAKOGOX", "GWoccupy " + occupy);
        return (int) ((occupy * widthGirl) / 100);
    }

    public static int occupyHeightGirl() {
        Log.d("KAKOGOX", "GH " + ((occupy * heightGirl) / 100));
        Log.d("KAKOGOX", "GHoccupy " + occupy);
        return (int) ((occupy * heightGirl) / 100);
    }

    public static int occupyWidthAccessory(BitmapDrawable drawable) {
        Log.d("KAKOGOX", "occupyWidthAccessory " + occupy);
        return (int) ((occupy * drawable.getIntrinsicWidth()) / 100);
    }

    public static int occupyHeightAccessory(BitmapDrawable drawable) {
        return (int) ((occupy * drawable.getIntrinsicHeight()) / 100);
    }

    public static BitmapDrawable getImageGirl() {
        return imageGirl;
    }
}
