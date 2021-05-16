package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppPanel;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;
import net.thevpc.echo.impl.tools.ToolFolder;

public class Panel extends AppContainerBase<AppComponent, AppTool> implements AppPanel {
    public Panel(Application application) {
        super(new ToolFolder(application),AppComponent.class,AppTool.class);
    }
    public Panel(AppToolFolder tool) {
        super(tool,AppComponent.class,AppTool.class);
    }
}
