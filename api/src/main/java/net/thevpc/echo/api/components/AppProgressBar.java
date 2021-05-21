package net.thevpc.echo.api.components;

import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.WritableBoolean;

public interface AppProgressBar<T extends Number> extends AppNumberControl<T> {
    WritableBoolean indeterminate();
    WritableStr text();
    WritableBoolean textPainted();
}
