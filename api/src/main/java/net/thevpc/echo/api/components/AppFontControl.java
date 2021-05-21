package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppFont;

public interface AppFontControl extends AppControl {
    WritableValue<AppFont> selection();
}
