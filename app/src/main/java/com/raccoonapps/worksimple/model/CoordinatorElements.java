package com.raccoonapps.worksimple.model;

import android.util.Log;
import android.widget.ImageView;

/**
 * Created by sanyok on 06.10.15.
 */
public class CoordinatorElements {

    public static final String TAG = "COORDDEB";

    public static void imageCoordinator(double screenWidth, double screenHeight, double percentCoordinateImageX, double percentCoordinateImageY, ImageView image, ImageView girlImage) {

        double girlEndX = girlImage.getWidth();
        double girlEndY = girlImage.getHeight();
        double lengthBeforeXGirl = screenWidth - girlImage.getX();
        lengthBeforeXGirl = screenWidth - lengthBeforeXGirl;
        double lengthBeforeYGirl = screenHeight - girlImage.getY();
        lengthBeforeYGirl = screenHeight - lengthBeforeYGirl;

        double translationHairX = (girlEndX * percentCoordinateImageX) / 100;
        double translationHairY = (girlEndY * percentCoordinateImageY) / 100;

        double centerHairsX = image.getWidth() / 2;
        Log.d(TAG, "girlImage.getX() = " + girlImage.getX());
        Log.d(TAG, "screenWidth = " + screenWidth);
        Log.d(TAG, "girlEndX = " + girlEndX);
        Log.d(TAG, "lengthBeforeXGirl = " + lengthBeforeXGirl);

        image.setTranslationX((float) (lengthBeforeXGirl + (translationHairX - centerHairsX)));
        image.setTranslationY((float) (lengthBeforeYGirl + translationHairY));
    }

}
