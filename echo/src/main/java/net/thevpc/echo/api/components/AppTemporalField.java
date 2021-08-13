package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableValue;

import java.time.temporal.Temporal;

public interface AppTemporalField<T extends Temporal> extends AppControl{
    Class<T> valueType();
    WritableValue<T> value();
}
