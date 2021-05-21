package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;

public interface AppNumberControl<T extends Number> extends AppControl {
    WritableValue<T> value();
    WritableValue<T> min();
    WritableValue<T> max();
    WritableValue<T> step();
    WritableValue<T> minorTicks();
    WritableValue<T> majorTicks();
    WritableBoolean snapToTicks();
    Class<T> valueType();
}
