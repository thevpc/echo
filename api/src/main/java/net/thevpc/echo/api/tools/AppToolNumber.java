package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;

public interface AppToolNumber extends AppTool {
    WritableValue<Number> value();
}
