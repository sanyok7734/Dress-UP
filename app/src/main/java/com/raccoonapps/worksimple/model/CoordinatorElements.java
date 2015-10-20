package com.raccoonapps.worksimple.model;

import android.graphics.drawable.BitmapDrawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.raccoonapps.worksimple.MainActivity;

public class CoordinatorElements {

    private FrameLayout root;
    private ImageView girlImage;

    public CoordinatorElements(FrameLayout root, ImageView girlImage) {
        this.root = root;
        this.girlImage = girlImage;
    }

    public void imageCoordinator(ImageView image, BitmapDrawable drawable, double percentCoordinateImageX, double percentCoordinateImageY) {
        if (drawable != null) {
            double girlEndX = girlImage.getWidth();
            double girlEndY = girlImage.getHeight();
            double lengthBeforeXGirl = root.getWidth() - girlImage.getX();
            lengthBeforeXGirl = root.getWidth() - lengthBeforeXGirl;
            double lengthBeforeYGirl = root.getHeight() - girlImage.getY();
            lengthBeforeYGirl = root.getHeight() - lengthBeforeYGirl;

            double translationHairX = (girlEndX * percentCoordinateImageX) / 100;
            double translationHairY = (girlEndY * percentCoordinateImageY) / 100;


            double centerHairsX = Squeezing.occupyWidthAccessory(drawable)/ 2;
            double centerHairsY = Squeezing.occupyHeightAccessory(drawable) / 2;

            image.setTranslationX((float) (lengthBeforeXGirl + (translationHairX - centerHairsX)));
            image.setTranslationY((float) (lengthBeforeYGirl + (translationHairY - centerHairsY)));

            image.setImageDrawable(drawable);
        } else {
            root.removeView(image);
        }
    }

    public static float setCoordinatorGirlX(int widthImage, double percentCoordinateGirlX) {
        return ((float)(((percentCoordinateGirlX* MainActivity.screenWidth)/100) - (widthImage/2)));
    }

}
