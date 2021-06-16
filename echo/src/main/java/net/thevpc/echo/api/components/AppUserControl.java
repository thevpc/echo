package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableValue;

public interface AppUserControl extends AppControl{
    WritableValue<Object> renderer();
}
