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

import java.util.ArrayList;
import java.util.List;


public class CategoryWrapper {

    private boolean selectedCategory = false;
    private boolean overflow = false;

    private Context context;
    private Drawable drawableCategory;
    private Drawable drawableButton;
    private FrameLayout screen;

    private CoordinatorElements coordinatorElements;

    private ImageView imageView;
    private ArrayList<ImageView> imageViews = new ArrayList<>();
    private List<Accessory> accessories = new ArrayList<>();


    public CategoryWrapper(Category category, Context context, FrameLayout contentGirl) {
        this.context = context;
        this.screen = contentGirl;
        ImageView girl = (ImageView) contentGirl.findViewById(R.id.girl);


        //инициализация кординатора элементов
        coordinatorElements = new CoordinatorElements(screen, girl);

        //set accessory for girl
        this.accessories = category.getAccessories();

        //set image in button
        int identifier = context.getResources().getIdentifier("drawable/" + category.getCategoryIcon(), null, context.getPackageName());
        drawableCategory = context.getResources().getDrawable(identifier);
        drawableButton = context.getResources().getDrawable(R.drawable.btn_right);
    }

    public CategoryWrapper(Category category, Context context, Boolean overflow, FrameLayout contentGirl) {
        this(category, context, contentGirl);
        this.overflow = overflow;
    }

    public void setDrawableButton(int buttonDrawable) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawableButton = context.getDrawable(buttonDrawable);
        } else {
            drawableButton = context.getResources().getDrawable(buttonDrawable);
        }
    }

    public void setCoordinateImage(int tag, BitmapDrawable drawable, double X, double Y) {
        if (!overflow) {
            for (int i = 0; i < imageViews.size(); i++) {
                if (imageViews.get(0).getTag().equals(tag)) {
                    screen.removeView(imageViews.get(0));
                    imageViews.remove(imageViews.get(0));
                    return;
                }
            }
            screen.removeView(imageView);
            imageViews.removeAll(imageViews);
            imageView = new ImageView(context);
            LayoutParams layoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParam);
            imageView.setTag(tag);
            screen.addView(imageView);
            imageViews.add(imageView);
            coordinatorElements.imageCoordinator(imageView, drawable, X, Y);
        } else {
            for (int i = 0; i < imageViews.size(); i++) {
                if (imageViews.get(i).getTag().equals(tag)) {
                    screen.removeView(imageViews.get(i));
                    imageViews.remove(imageViews.get(i));
                    return;
                }
            }
            imageView = new ImageView(context);
            LayoutParams layoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            imageView.setLayoutParams(layoutParam);
            imageView.setTag(tag);
            imageViews.add(imageView);
            screen.addView(imageView);
            coordinatorElements.imageCoordinator(imageView, drawable, X, Y);
        }
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
