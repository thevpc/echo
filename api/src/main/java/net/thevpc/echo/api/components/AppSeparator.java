package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableDouble;

public interface AppSeparator extends AppControl {
    WritableDouble width();

    WritableDouble height();
}
