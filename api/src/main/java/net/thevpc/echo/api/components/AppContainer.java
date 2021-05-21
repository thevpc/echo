package net.thevpc.echo.api.components;

import net.thevpc.echo.api.AppContainerChildren;

public interface AppContainer<C extends AppComponent> extends AppComponent {

    AppContainerChildren<C> children();

}
