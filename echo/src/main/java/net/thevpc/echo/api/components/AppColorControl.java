package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppColor;

public interface AppColorControl extends AppControl {
    WritableValue<AppColor> value();
}
