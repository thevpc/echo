package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.impl.components.Color;

import java.time.temporal.Temporal;

public interface AppToolTemporal<T extends Temporal> extends AppTool {
    WritableValue<T> value();
}
