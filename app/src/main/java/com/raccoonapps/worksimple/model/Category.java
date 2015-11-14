package com.raccoonapps.worksimple.model;

import java.util.List;

public class Category {

    private List<Accessory> accessories;
    private String categoryTitle;
    private String categoryIcon;
    private String categoryIconPressed;

    public Category(List<Accessory> accessories, String categoryTitle, String categoryIcon, String categoryIconPressed) {
        this.accessories = accessories;
        this.categoryTitle = categoryTitle;
        this.categoryIcon = categoryIcon.split("\\.")[0];
        this.categoryIconPressed = categoryIconPressed.split("\\.")[0];
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

    public String getCategoryIconPressed() {
        return categoryIconPressed;
    }

    @Override
    public String toString() {
        return "Category{" +
                "accessories=" + accessories +
                ", categoryTitle='" + categoryTitle + '\'' +
                ", categoryIcon='" + categoryIcon + '\'' +
                ", categoryIconPressed='" + categoryIconPressed + '\'' +
                '}';
    }
}
