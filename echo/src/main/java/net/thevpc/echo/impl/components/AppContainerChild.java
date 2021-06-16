package net.thevpc.echo.impl.components;

import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppComponent;

public interface AppContainerChild<C extends AppComponent>
        extends WritableValue<C> {
}
