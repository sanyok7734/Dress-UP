package com.raccoonapps.worksimple.components;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.raccoonapps.worksimple.model.Squeezing;

/**
 * Created by sanyok on 09.11.15.
 */
public class AccessoryWrapper {

    private ImageView accessoryImage;
    private int tag;

    public AccessoryWrapper(Context context) {
        accessoryImage = new ImageView(context);
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

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
