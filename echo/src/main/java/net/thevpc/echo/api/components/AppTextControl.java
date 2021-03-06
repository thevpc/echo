package net.thevpc.echo.api.components;

import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.WritableTextStyle;

public interface AppTextControl extends AppControl {
    WritableString textContentType();
    WritableStr text();
    WritableTextStyle textStyle();
}
