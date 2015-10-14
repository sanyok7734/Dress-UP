package com.raccoonapps.worksimple.model;

import java.util.List;

public class Category {

    private List<Accessory> accessories;
    private String categoryTitle;
    private String categoryIcon;
    private int layer;

    public Category(List<Accessory> accessories, String categoryTitle, String categoryIcon, int layer) {
        this.accessories = accessories;
        this.categoryTitle = categoryTitle;
        this.categoryIcon = categoryIcon;
        this.layer = layer;
    }

    public List<Accessory> getAccessories() {
        return accessories;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public int getLayer() {
        return layer;
    }
}
