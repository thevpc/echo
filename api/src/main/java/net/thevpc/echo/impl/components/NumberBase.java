package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppNumberControl;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class NumberBase<T extends Number> extends ControlBase implements AppNumberControl<T> {
    private WritableValue<T> value;
    private WritableValue<T> min;
    private WritableValue<T> max;
    private WritableValue<T> step;
    private WritableValue<T> minorTicks;
    private WritableValue<T> majorTicks;
    private WritableBoolean snapToTicks = Props.of("snapToTicks").booleanOf(false);
    private Class<T> numberType;

    public NumberBase(String id,
                      Class<T> numberType,
                      Application app,
                      Class<? extends AppNumberControl<T>> componentType,
                      Class<? extends AppComponentPeer> peerType) {
        super(id, app, componentType, peerType);
        this.numberType = numberType;
        value = Props.of("value").valueOf(numberType);
        min = Props.of("min").valueOf(numberType);
        max = Props.of("max").valueOf(numberType);
        step = Props.of("step").valueOf(numberType);
        minorTicks = Props.of("minorTicks").valueOf(numberType);
        majorTicks = Props.of("majorTicks").valueOf(numberType);
        propagateEvents(value, min, max, step, step, minorTicks, majorTicks);
    }

    @Override
    public WritableValue<T> value() {
        return value;
    }

    @Override
    public WritableValue<T> min() {
        return min;
    }

    @Override
    public WritableValue<T> max() {
        return max;
    }

    @Override
    public WritableValue<T> step() {
        return step;
    }

    @Override
    public WritableValue<T> minorTicks() {
        return minorTicks;
    }

    @Override
    public WritableValue<T> majorTicks() {
        return majorTicks;
    }

    @Override
    public WritableBoolean snapToTicks() {
        return snapToTicks;
    }

    @Override
    public Class<T> valueType() {
        return numberType;
    }
}

