package com.raccoonapps.worksimple.model;

public class Accessory {

    private Coordinates coordinates;
    private int image;

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

    @Override
    public String toString() {
        return "Accessory{" +
                ", coordinates=" + coordinates +
                ", image='" + image + '\'' +
                '}';
    }

}
