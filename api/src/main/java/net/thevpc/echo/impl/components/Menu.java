package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.Application;
import net.thevpc.echo.DefaultAppComponentOptions;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppMenu;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;

public class Menu extends AppContainerBase<AppComponent, AppTool> implements AppMenu {
    public Menu(AppToolFolder tool) {
        super(tool,AppComponent.class, AppTool.class);
    }
    public Menu(Application app) {
        super(new ToolFolder(app), AppComponent.class, AppTool.class);
    }

    @Override
    public boolean isActionable() {
        return false;
    }

    @Override
    public void show(Object source, int x, int y) {

    }
    @Override
    public AppComponent createPreferredComponent(AppTool tool, String name, Path absolutePath, AppComponentOptions options) {
        if(tool instanceof AppToolFolder){
            options= DefaultAppComponentOptions.copy(options).componentTypeIfNull(AppMenu.class);
        }
        return super.createPreferredComponent(tool, name, absolutePath, options);
    }
}

