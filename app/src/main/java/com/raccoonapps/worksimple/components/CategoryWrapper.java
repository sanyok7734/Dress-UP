package com.raccoonapps.worksimple.components;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.Category;
import com.raccoonapps.worksimple.model.CoordinatorElements;

import java.util.ArrayList;
import java.util.List;


public class CategoryWrapper {

    private boolean selectedCategory = false;
    private boolean lamination = false;

    private Context context;
    //private Drawable drawableCategory;
    private Category category;
    private Drawable drawableButton;
    private Drawable drawableButtonPressed;
    private Drawable drawableButtonDefault;
    private FrameLayout screen;
    private AccessoryWrapper accessoryImage = null;

    private CoordinatorElements coordinatorElements;


    private List<AccessoryWrapper> imageViews = new ArrayList<>();
    private List<Accessory> accessories = new ArrayList<>();


    public CategoryWrapper(Category category, Context context, FrameLayout contentGirl) {
        this.context = context;
        this.screen = contentGirl;
        this.category = category;
        ImageView girl = (ImageView) contentGirl.findViewById(R.id.girl);


        //coordinator elements initialization
        coordinatorElements = new CoordinatorElements(screen, girl);

        //set accessor for girl
        this.accessories = category.getAccessories();

        //set image in button
        int identifier = context.getResources().getIdentifier("drawable/" + category.getCategoryIcon(), null, context.getPackageName());
        int identifierPressed = context.getResources().getIdentifier("drawable/" + category.getCategoryIconPressed(), null, context.getPackageName());
        //заполняю в зависимости от текущего состояния
        drawableButton = context.getResources().getDrawable(identifier);

        //два состояния нажатое и дефолтное
        drawableButtonPressed = context.getResources().getDrawable(identifierPressed);
        drawableButtonDefault = context.getResources().getDrawable(identifier);
    }

    public CategoryWrapper(Category category, Context context, Boolean lamination, FrameLayout contentGirl) {
        this(category, context, contentGirl);
        this.lamination = lamination;
    }

    public Drawable getDrawableButtonPressed() {
        return drawableButtonPressed;
    }

    public Drawable getDrawableButtonDefault() {
        return drawableButtonDefault;
    }

    public void setDrawableButton(Drawable buttonDrawable) {
        drawableButton = buttonDrawable;
    }

    public Drawable getDrawableButton() {
        return drawableButton;
    }

    public void setCoordinateImage(int tag, BitmapDrawable drawable, double X, double Y) {
        for (int i = 0; i < imageViews.size(); i++) {
            if (imageViews.get(i).getTag() == tag) {
                screen.removeView(imageViews.get(i).getAccessoryImage());
                imageViews.remove(imageViews.get(i));
                return;
            }
        }

        if (!lamination) {
            if (accessoryImage != null) {
                screen.removeView(accessoryImage.getAccessoryImage());
                imageViews.clear();
            }
            createImage(tag, drawable, X, Y);
        } else {
            createImage(tag, drawable, X, Y);
        }
    }

    private void createImage(int tag, BitmapDrawable drawable, double X, double Y) {
        accessoryImage = new AccessoryWrapper(context);
        accessoryImage.setTag(tag);
        accessoryImage.setAccessoryImage(drawable);
        imageViews.add(accessoryImage);
        screen.addView(accessoryImage.getAccessoryImage());
        coordinatorElements.imageCoordinator(accessoryImage, drawable, X, Y);
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