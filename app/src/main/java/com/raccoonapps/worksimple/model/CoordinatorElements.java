package com.raccoonapps.worksimple.model;

import android.graphics.drawable.BitmapDrawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.raccoonapps.worksimple.MainActivity;
import com.raccoonapps.worksimple.components.AccessoryWrapper;

public class CoordinatorElements {

    private FrameLayout root;
    private ImageView girlImage;

    public CoordinatorElements(FrameLayout root, ImageView girlImage) {
        this.root = root;
        this.girlImage = girlImage;
    }

    public void imageCoordinator(AccessoryWrapper image, BitmapDrawable drawable, double percentCoordinateImageX, double percentCoordinateImageY) {
        if (drawable != null) {
            double girlEndX = Squeezing.occupyWidthGirl();
            double girlEndY = Squeezing.occupyHeightGirl();
            double lengthBeforeXGirl = root.getWidth() - girlImage.getX();
            lengthBeforeXGirl = root.getWidth() - lengthBeforeXGirl;
            double lengthBeforeYGirl = root.getHeight() - girlImage.getY();
            lengthBeforeYGirl = root.getHeight() - lengthBeforeYGirl;

            double translationHairX = (girlEndX * percentCoordinateImageX) / 100;
            double translationHairY = (girlEndY * percentCoordinateImageY) / 100;


            double centerHairsX = ((Squeezing.occupyWidthAccessory(drawable)) / 2);
            double centerHairsY = ((Squeezing.occupyHeightAccessory(drawable)) / 2);

            image.getAccessoryImage().setTranslationX((float) (lengthBeforeXGirl + (translationHairX - centerHairsX)));
            image.getAccessoryImage().setTranslationY((float) (lengthBeforeYGirl + (translationHairY - centerHairsY)));

            image.setImageDrawable(drawable);
        } else {
            root.removeView(image.getAccessoryImage());
        }
    }

    public static float setCoordinatorGirlX(int widthImage, double percentCoordinateGirlX) {
        return ((float)(((percentCoordinateGirlX* MainActivity.screenWidth)/100) - (widthImage/2)));
    }

    public static float setCoordinatorGirlY(int heightImage, double percentCoordinateGirlY) {
        return ((float)(((percentCoordinateGirlY* MainActivity.screenHeight)/100) - (heightImage/2)));
    }

}
