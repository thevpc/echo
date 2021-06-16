package net.thevpc.echo;

import net.thevpc.echo.api.components.AppNumberField;
import net.thevpc.echo.impl.components.NumberBase;
import net.thevpc.echo.spi.peers.AppNumberFieldPeer;

public class NumberField<T extends Number> extends NumberBase<T> implements AppNumberField<T> {
    public NumberField(String id,
                        Class<T> numberType,
                        Application app) {
        super(id, numberType, app, (Class) AppNumberField.class, AppNumberFieldPeer.class);
    }
    public NumberField(Class<T> numberType,
                        Application app) {
        this(null, numberType, app);
    }
}

