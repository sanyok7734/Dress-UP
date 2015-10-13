package com.raccoonapps.worksimple.components;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.CoordinatorElements;

import java.util.ArrayList;


/**
 * Created by sanyok on 10.10.15.
 */
public class Category  implements Comparable<Category>{
    private int id ;
    private int positionAccessory = -1;

    private Context context;
    private Drawable drawableCategory;
    private Drawable drawableButton;
    private boolean check = false;

    private int layer;

    private CoordinatorElements coordinatorElements;

    private ImageView imageView;
    private ArrayList<Accessory> accessories = new ArrayList<>();
    FrameLayout screen;


    public Category(int resourceDrawable, Context context, FrameLayout contentGirl, int layer, ArrayList<Accessory> accessories) {
        this.context = context;
        this.layer = layer;
        screen = contentGirl;
        ImageView girl = (ImageView) contentGirl.findViewById(R.id.girl);

        imageView = new ImageView(context);
        LayoutParams layoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParam);
        //инициализация кординатора элементов
        coordinatorElements = new CoordinatorElements(screen, girl);

        //set accessory for girl
        this.accessories = accessories;

        //set image in button
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            drawableCategory = context.getDrawable(resourceDrawable);
        } else {
            drawableCategory = context.getResources().getDrawable(resourceDrawable);
        }

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            drawableButton = context.getDrawable(R.drawable.btn_right);
        } else {
            drawableButton = context.getResources().getDrawable(R.drawable.btn_right);
        }
    }

    public void createImageCategory() {
        screen.addView(imageView);
    }

    public void setDrawableButton(int buttonDrawable) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            drawableButton = context.getDrawable(buttonDrawable);
        } else {
            drawableButton = context.getResources().getDrawable(buttonDrawable);
        }
    }

    public void setCoordinateImage(BitmapDrawable drawable, double X, double Y) {
        coordinatorElements.imageCoordinator(imageView, drawable, X, Y);
    }

    public Drawable getDrawableCategory() {
        return drawableCategory;
    }

    public Drawable getDrawableButton() {
        return drawableButton;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public ArrayList<Accessory> getAccessories() {
        return accessories;
    }

    public int getLayer() {
        return layer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionAccessory() {
        return positionAccessory;
    }

    public void setPositionAccessory(int positionAccessory) {
        this.positionAccessory = positionAccessory;
    }

    @Override
    public int compareTo(Category category) {
        return (id-category.getId());
    }
}
