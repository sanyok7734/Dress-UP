package com.raccoonapps.worksimple.model;

public class Accessory {

    private Coordinates coordinates;
    private int image;
    private boolean include = false;

    public Accessory(Coordinates coordinates, int image) {
        this.coordinates = coordinates;
        this.image = image;
    }


    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getImage() {
        return image;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    @Override
    public String toString() {
        return "Accessory [" +
                "image=" + image +
                ", coordinates=" + coordinates +
                ']';
    }
}
