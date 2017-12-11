package mdev.mlaocthtione.bus;

import com.squareup.otto.Bus;

/**
 * Created by MDEV on 3/2/2559.
 */
public final class BusProvider {
    private static Bus bus;
    public static Bus getInstance(){
        if (bus == null){
            bus = new Bus();
        }
        return bus;
    }
}
