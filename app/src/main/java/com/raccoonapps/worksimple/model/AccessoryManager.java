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
        hair.add(new Accessory(new Coordinates(36.19632, -0.37), R.drawable.hair1));
        hair.add(new Accessory(new Coordinates(36.19632, -0.37), R.drawable.hair2));

        return hair;
    }

}
