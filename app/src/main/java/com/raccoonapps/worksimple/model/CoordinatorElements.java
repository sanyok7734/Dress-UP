package com.raccoonapps.worksimple.model;

import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

public class CoordinatorElements {

    private View root;
    private ImageView girlImage;

    public CoordinatorElements(View root, ImageView girlImage) {
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


            double centerHairsX = drawable.getBitmap().getWidth() / 2;
            double centerHairsY = drawable.getBitmap().getHeight() / 2;

            image.setTranslationX((float) (lengthBeforeXGirl + (translationHairX - centerHairsX)));
            image.setTranslationY((float) (lengthBeforeYGirl + (translationHairY - centerHairsY)));

            image.setImageDrawable(drawable);
        } else {
            image.setImageResource(0);
        }
    }

}
