package net.thevpc.echo.api.components;

import net.thevpc.echo.impl.components.AppContainerChild;

public interface AppContainerOne<C extends AppComponent>
        extends AppContainer <C >
{

    AppContainerChild<C> child();

}
