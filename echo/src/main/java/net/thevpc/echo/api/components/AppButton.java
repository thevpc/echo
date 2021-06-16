package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.AppActionValue;

public interface AppButton extends AppTextControl {

    /**
     * this value is propagated in action event
     *
     * @return value property
     */
    WritableValue<Object> value();

    AppActionValue action();
}
