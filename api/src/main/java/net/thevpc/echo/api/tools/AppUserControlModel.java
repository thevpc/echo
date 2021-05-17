package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;

public interface AppUserControlModel extends AppComponentModel {
    WritableValue<Object> renderer();
}
