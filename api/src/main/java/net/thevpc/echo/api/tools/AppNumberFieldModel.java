package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;

public interface AppNumberFieldModel extends AppComponentModel {
    WritableValue<Number> value();
}
