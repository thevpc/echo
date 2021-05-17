package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppFont;

public interface AppFontChooserModel extends AppComponentModel {
    WritableValue<AppFont> value();
}
