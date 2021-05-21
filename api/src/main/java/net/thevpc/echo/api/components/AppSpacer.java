package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableDouble;


public interface AppSpacer extends AppControl {
    WritableDouble width();

    WritableDouble height();
}
