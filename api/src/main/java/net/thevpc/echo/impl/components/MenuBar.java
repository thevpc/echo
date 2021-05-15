package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.DefaultAppComponentOptions;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppMenu;
import net.thevpc.echo.api.components.AppMenuBar;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;

public class MenuBar extends AppContainerBase<AppComponent, AppTool> implements AppMenuBar {
    public MenuBar(AppToolFolder tool) {
        super(tool,AppComponent.class, AppTool.class);
    }
    public MenuBar(Application app) {
        super(new ToolFolder(app), AppComponent.class, AppTool.class);
    }

    @Override
    public AppComponent createPreferredComponent(AppTool tool, String name, AppPath absolutePath, AppComponentOptions options) {
        if(tool instanceof AppToolFolder){
            options= DefaultAppComponentOptions.copy(options).componentTypeIfNull(AppMenu.class);
        }
        return super.createPreferredComponent(tool, name, absolutePath, options);
    }
}

