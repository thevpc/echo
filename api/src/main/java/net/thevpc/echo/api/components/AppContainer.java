package net.thevpc.echo.api.components;

import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.components.AppContainerChildren;

public interface AppContainer<T extends AppComponentModel, C extends AppComponent> extends AppComponent {

    AppContainerModel model();

//    AppComponent get(Path path);
//
    AppContainerChildren<T, C> children();

//    AppComponent add(AppComponentModel tool, Path relativePath, AppComponentOptions options);

//    AppComponent remove(Path relativePath);

}
