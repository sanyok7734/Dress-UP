package com.raccoonapps.worksimple.model;

import android.util.Log;

import com.raccoonapps.worksimple.components.Category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CategoryManager {

    private ArrayList<Category> categoriesManager;

    public CategoryManager(ArrayList<Category> categories) {
        this.categoriesManager = categories;
        Collections.sort(categoriesManager, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                return -(lhs.getLayer()-rhs.getLayer());
            }
        });

        for (Category category: categoriesManager) {
            Log.d("LayersCategory", "layer = "+category.getLayer());
            category.createImageCategory();
        }

    }
}
