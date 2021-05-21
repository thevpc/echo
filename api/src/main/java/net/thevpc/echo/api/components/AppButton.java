package net.thevpc.echo.api.components;

import net.thevpc.echo.api.AppActionValue;

public interface AppButton extends AppTextControl {
    AppActionValue action();
}
