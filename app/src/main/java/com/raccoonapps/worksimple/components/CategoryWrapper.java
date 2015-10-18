package com.raccoonapps.worksimple.components;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.Category;
import com.raccoonapps.worksimple.model.CoordinatorElements;
import com.raccoonapps.worksimple.model.Squeezing;

import java.util.ArrayList;
import java.util.List;


public class CategoryWrapper {

    private boolean selectedCategory = false;
    private boolean lamination = false;

    private Context context;
    private Drawable drawableCategory;
    private Drawable drawableButton;
    private FrameLayout screen;
    private ImageView accessoryImage;

    private CoordinatorElements coordinatorElements;


    private List<ImageView> imageViews = new ArrayList<>();
    private List<Accessory> accessories = new ArrayList<>();


    public CategoryWrapper(Category category, Context context, FrameLayout contentGirl) {
        this.context = context;
        this.screen = contentGirl;
        ImageView girl = (ImageView) contentGirl.findViewById(R.id.girl);


        //coordinator elements initialization
        coordinatorElements = new CoordinatorElements(screen, girl);

        //set accessor for girl
        this.accessories = category.getAccessories();

        //set image in button
        int identifier = context.getResources().getIdentifier("drawable/" + category.getCategoryIcon(), null, context.getPackageName());
        drawableCategory = context.getResources().getDrawable(identifier);
        drawableButton = context.getResources().getDrawable(R.drawable.btn_right);
    }

    public CategoryWrapper(Category category, Context context, Boolean lamination, FrameLayout contentGirl) {
        this(category, context, contentGirl);
        this.lamination = lamination;
    }

    public void setDrawableButton(int buttonDrawable) {
        drawableButton = context.getResources().getDrawable(buttonDrawable);
    }

    public void setCoordinateImage(int tag, BitmapDrawable drawable, double X, double Y) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (imageViews.get(i).getTag().equals(tag)) {
                screen.removeView(imageViews.get(i));
                imageViews.remove(imageViews.get(i));
                return;
            }
        }

        if (!lamination) {
            screen.removeView(accessoryImage);
            imageViews.removeAll(imageViews);
            createImage(tag, drawable, X, Y);
        } else {
            createImage(tag, drawable, X, Y);
        }
    }

    private void createImage(int tag, BitmapDrawable drawable, double X, double Y) {
        accessoryImage = new ImageView(context);
        LayoutParams layoutParam = new LayoutParams(
                Squeezing.occupyWidthAccessory(drawable), Squeezing.occupyHeightAccessory(drawable));
        accessoryImage.setLayoutParams(layoutParam);
        accessoryImage.setTag(tag);
        imageViews.add(accessoryImage);
        screen.addView(accessoryImage);
        coordinatorElements.imageCoordinator(accessoryImage, drawable, X, Y);
    }


    public Drawable getDrawableCategory() {
        return drawableCategory;
    }

    public Drawable getDrawableButton() {
        return drawableButton;
    }

    public boolean isSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(boolean selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }
}