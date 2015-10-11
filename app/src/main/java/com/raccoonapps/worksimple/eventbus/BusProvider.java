package com.raccoonapps.worksimple.eventbus;

import com.squareup.otto.Bus;

/**
 * Created by sanyok on 10.10.15.
 */
public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
    }
}
