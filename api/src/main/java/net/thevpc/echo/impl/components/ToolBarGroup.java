package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;

public class ToolBarGroup extends AppContainerBase<AppComponent, AppTool> implements AppToolBarGroup {
    public ToolBarGroup(AppToolFolder tool) {
        super(tool,AppComponent.class, AppTool.class);
    }
    public ToolBarGroup(Application app) {
        super(new ToolFolder(app), AppComponent.class, AppTool.class);
    }
    @Override
    public AppComponent createPreferredComponent(AppTool tool, String name, Path absolutePath, AppComponentOptions options) {
        if(options.componentType()==null && tool instanceof AppToolFolder){
            options.componentType(AppToolBar.class);
        }
        return super.createPreferredComponent(tool, name, absolutePath, options);
    }

}

