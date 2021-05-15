package net.thevpc.echo.api.components;

import net.thevpc.common.props.PropertyContainer;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.components.AppContainerChildren;

public interface AppContainer<C extends AppComponent,T extends AppTool> extends PropertyContainer, AppComponent {

    AppToolFolder tool();

//    AppComponent get(AppPath path);
//
    AppContainerChildren<C,T> children();

//    AppComponent add(AppTool tool, AppPath relativePath, AppComponentOptions options);

//    AppComponent remove(AppPath relativePath);

}
