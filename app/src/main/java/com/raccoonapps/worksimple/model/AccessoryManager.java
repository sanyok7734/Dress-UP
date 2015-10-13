package com.raccoonapps.worksimple.model;

import com.raccoonapps.worksimple.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccessoryManager {

    private Map<String, ArrayList<Accessory>> accessoryCategoris = new HashMap<>();
    private static AccessoryManager accessoryManager;

    private AccessoryManager() {
        accessoryCategoris.put("Hair", getHair());
        accessoryCategoris.put("Hat", getHat());
        accessoryCategoris.put("Bag", getBag());
    }

    public static AccessoryManager getInstance() {
        if (accessoryManager == null) {
            accessoryManager = new AccessoryManager();
        }
        return accessoryManager;
    }

    public ArrayList<Accessory> getAccessory(String title) {
        return accessoryCategoris.get(title);
    }

    private ArrayList<Accessory> getHair() {
        ArrayList<Accessory> hair = new ArrayList<>();
        hair.add(new Accessory(new Coordinates(34.66258, 12.91028), R.drawable.hair1));
        hair.add(new Accessory(new Coordinates(44.785, 13.45733), R.drawable.hair2));
        hair.add(new Accessory(new Coordinates(36.503, 12.80088), R.drawable.hair3));
        hair.add(new Accessory(new Coordinates(36.19632, 11.26915), R.drawable.hair4));
        hair.add(new Accessory(new Coordinates(36.8098, 14.11379), R.drawable.hair5));
        hair.add(new Accessory(new Coordinates(37.11675, 12.36324), R.drawable.hair6));

        return hair;
    }

    private ArrayList<Accessory> getHat() {
        ArrayList<Accessory> hat = new ArrayList<>();
        hat.add(new Accessory(new Coordinates(36.50307, 2.95405), R.drawable.hat1));
        hat.add(new Accessory(new Coordinates(37.4235, 4.3763), R.drawable.hat2));
        hat.add(new Accessory(new Coordinates(36.81, 5.79869), R.drawable.hat3));
        return hat;
    }


    private ArrayList<Accessory> getBag() {
        ArrayList<Accessory> bag = new ArrayList<>();
        bag.add(new Accessory(new Coordinates(7.66871, 67.39606), R.drawable.bag1));
        bag.add(new Accessory(new Coordinates(7.05522, 65.64551), R.drawable.bag2));
        bag.add(new Accessory(new Coordinates(5.21472, 66.52079), R.drawable.bag3));
        bag.add(new Accessory(new Coordinates(7.36196, 65.09847), R.drawable.bag4));
        bag.add(new Accessory(new Coordinates(6.74847, 65.20788), R.drawable.bag5));
        return bag;
    }
}
