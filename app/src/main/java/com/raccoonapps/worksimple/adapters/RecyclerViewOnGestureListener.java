package com.raccoonapps.worksimple.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.raccoonapps.worksimple.components.CategoryWrapper;

import java.util.List;

/**
 * Created by sanyok on 28.10.15.
 */
public class RecyclerViewOnGestureListener extends GestureDetector.SimpleOnGestureListener {

    RecyclerView recyclerView;
    private List<CategoryWrapper> categories;

    public RecyclerViewOnGestureListener(RecyclerView recyclerView, List<CategoryWrapper> categories) {
        this.recyclerView = recyclerView;
        this.categories = categories;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {


        return super.onSingleTapConfirmed(e);
    }

}
