package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.echo.api.components.AppControl;
import net.thevpc.echo.api.tools.AppToolToggle;

public interface AppToggle extends AppControl {
    AppToolToggle tool();
}
