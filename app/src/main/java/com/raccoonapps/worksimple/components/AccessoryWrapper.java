package com.raccoonapps.worksimple.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raccoonapps.worksimple.controller.Squeezing;

/**
 * Created by sanyok on 09.11.15.
 */
public class AccessoryWrapper implements Comparable<AccessoryWrapper> {

    private ImageView accessoryImage;
    private CategoryWrapper categoryWrapper;
    private int tag;
    private int layer;
    private double fromX;
    private double toX;
    private double fromY;
    private double toY;
    private int X;
    private int Y;


    public AccessoryWrapper(Context context, CategoryWrapper categoryWrapper) {
        accessoryImage = new ImageView(context);
        this.categoryWrapper = categoryWrapper;
    }

    public ImageView getAccessoryImage() {
        return accessoryImage;
    }

    public void setAccessoryImage(BitmapDrawable drawable) {
        ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(
                Squeezing.occupyWidthAccessory(drawable), Squeezing.occupyHeightAccessory(drawable));
        accessoryImage.setLayoutParams(layoutParam);
    }

    public void setImageDrawable(BitmapDrawable drawable) {
        accessoryImage.setImageDrawable(drawable);
    }

    public boolean isRemove() {
        Drawable imgDrawable = ((ImageView)accessoryImage).getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();

        //Limit x, y range within bitmap
        if(X < 0){
            X = 0;
        }else if(X > bitmap.getWidth()-1){
            X = bitmap.getWidth()-1;
        }

        if(Y < 0){
            Y = 0;
        }else if(Y > bitmap.getHeight()-1){
            Y = bitmap.getHeight()-1;
        }

        int touchedRGB = bitmap.getPixel(X, Y);
        String color = Integer.toHexString(touchedRGB);
        return !color.equals("0");
    }


    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public double getFromX() {
        return fromX;
    }

    public void setFromX(double fromX) {
        this.fromX = fromX;
    }

    public double getToX() {
        return toX;
    }

    public void setToX(double toX) {
        this.toX = toX;
    }

    public double getToY() {
        return toY;
    }

    public void setToY(double toY) {
        this.toY = toY;
    }

    public double getFromY() {
        return fromY;
    }

    public void setFromY(double fromY) {
        this.fromY = fromY;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    @Override
    public int compareTo(@NonNull AccessoryWrapper another) {
        if (this.getLayer()<another.getLayer()){
            return -1;
        }else{
            return 1;
        }
    }

    public CategoryWrapper getCategoryWrapper() {
        return categoryWrapper;
    }
}
