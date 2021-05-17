package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppColor;

public interface AppColorChooserModel extends AppComponentModel {
    WritableValue<AppColor> value();
}
