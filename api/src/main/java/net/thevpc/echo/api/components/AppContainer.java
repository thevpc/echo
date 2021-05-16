package net.thevpc.echo.api.components;

import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.components.AppContainerChildren;

public interface AppContainer<C extends AppComponent,T extends AppTool> extends AppComponent {

    AppToolFolder tool();

//    AppComponent get(Path path);
//
    AppContainerChildren<C,T> children();

//    AppComponent add(AppTool tool, Path relativePath, AppComponentOptions options);

//    AppComponent remove(Path relativePath);

}
