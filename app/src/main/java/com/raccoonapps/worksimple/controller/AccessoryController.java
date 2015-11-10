package com.raccoonapps.worksimple.controller;

import com.raccoonapps.worksimple.components.AccessoryWrapper;

import java.util.ArrayList;

/**
 * Created by sanyok on 09.11.15.
 */
public class AccessoryController {

    private static final AccessoryController CONTROLLER = new AccessoryController();

    private static ArrayList<AccessoryWrapper> accessoryWrappers = new ArrayList<>();
    private static int layers = 0;


    public static AccessoryController getInstance() {
        return CONTROLLER;
    }



    public static AccessoryWrapper getAccessoryWrapper(int index) {
        return accessoryWrappers.get(index);
    }

    public static boolean contain(int number, int from, int to) {
        for (double i = from; i<=to; i++) {
            if (number == i)
                return true;
        }
        return false;
    }

    public static void add(AccessoryWrapper wrapper) {
        accessoryWrappers.add(wrapper);
        wrapper.setLayer(layers++);
    }

    public static void remove(int tag) {
        for (int i=0; i<accessoryWrappers.size(); i++) {
            if (accessoryWrappers.get(i).getTag() == tag)
                accessoryWrappers.remove(i);
        }
    }

    public static int size() {
        return accessoryWrappers.size();
    }

    private AccessoryController() {
    }
}
