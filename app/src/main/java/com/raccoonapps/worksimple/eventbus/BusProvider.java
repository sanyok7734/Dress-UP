package com.raccoonapps.worksimple.eventbus;

import com.squareup.otto.Bus;

public class BusProvider {
    private static final Bus BUS = new Bus();
    private static final Bus BUS_GAME = new Bus();

    public static Bus getInstanceMain() {
        return BUS;
    }
    public static Bus getInstanceGame() {
        return BUS_GAME;
    }

    private BusProvider() {
    }
}
