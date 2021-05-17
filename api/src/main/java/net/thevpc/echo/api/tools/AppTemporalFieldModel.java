package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;

import java.time.temporal.Temporal;

public interface AppTemporalFieldModel<T extends Temporal> extends AppComponentModel {
    WritableValue<T> value();
}
