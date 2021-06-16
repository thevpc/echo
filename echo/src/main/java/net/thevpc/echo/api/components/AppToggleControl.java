package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableString;

public interface AppToggleControl extends AppTextControl {
    WritableString group();

    WritableBoolean selected();
}
