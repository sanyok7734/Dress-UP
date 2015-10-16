package com.raccoonapps.worksimple.model;

import java.util.List;

public class Category {

    private List<Accessory> accessories;
    private String categoryTitle;
    private String categoryIcon;

    public Category(List<Accessory> accessories, String categoryTitle, String categoryIcon) {
        this.accessories = accessories;
        this.categoryTitle = categoryTitle;
        this.categoryIcon = categoryIcon.split("\\.")[0];
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


    @Override
    public String toString() {
        return "Category [" +
                "categoryTitle='" + categoryTitle + '\'' +
                ", categoryIcon='" + categoryIcon + '\'' +
                ", accessories=" + accessories +
                ']';
    }
}
