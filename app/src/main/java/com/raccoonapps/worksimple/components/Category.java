package com.raccoonapps.worksimple.components;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewGroup.*;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.raccoonapps.worksimple.MainActivity;
import com.raccoonapps.worksimple.R;
import com.raccoonapps.worksimple.model.Accessory;
import com.raccoonapps.worksimple.model.CoordinatorElements;

import java.util.ArrayList;

/**
 * Created by sanyok on 10.10.15.
 */
public class Category {

    private Context context;
    private Drawable drawableCategory;
    private Drawable drawableButton;
    private boolean check = false;

    private ImageView imageView;
    private ImageView contentGirl;
    private ArrayList<Accessory> accessories = new ArrayList<>();


    public Category(int resourceDrawable, Context context, FrameLayout contentGirl, ArrayList<Accessory> accessories) {
        this.context = context;
        this.contentGirl = (ImageView) contentGirl.findViewById(R.id.girl);

        //set accessory for girl
        imageView = new ImageView(context);
        LayoutParams layoutParam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParam);
        contentGirl.addView(imageView);

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

    public void setDrawableButton(int buttonDrawable) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            drawableButton = context.getDrawable(buttonDrawable);
        } else {
            drawableButton = context.getResources().getDrawable(buttonDrawable);
        }
    }

    public void setCoordinateImage(double X, double Y) {
        CoordinatorElements.imageCoordinator(MainActivity.screenWidth, MainActivity.screenHeight, X, Y, imageView, contentGirl);
    }

    public void setImageView(int imageAccessory) {
        imageView.setImageResource(imageAccessory);
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
}
