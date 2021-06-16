package net.thevpc.echo;

import net.thevpc.echo.api.components.AppNumberSlider;
import net.thevpc.echo.impl.components.NumberBase;
import net.thevpc.echo.spi.peers.AppNumberSliderPeer;

public class NumberSlider<T extends Number> extends NumberBase<T> implements AppNumberSlider<T> {
    public NumberSlider(String id,
                        Class<T> numberType,
                        Application app) {
        super(id, numberType, app, (Class) AppNumberSlider.class,AppNumberSliderPeer.class);
    }
    public NumberSlider(Class<T> numberType,
                        Application app) {
        this(null, numberType, app);
    }
}

