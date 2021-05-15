package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.impl.tools.ToolFolder;

public class Window extends AppContainerBase<AppComponent, AppTool> implements AppWindow {
    public Window(AppToolWindow tool) {
        super(tool, AppComponent.class, AppTool.class);
    }
    public Window(Application app) {
        super(new ToolFolder(app), AppComponent.class, AppTool.class);
    }

    @Override
    public AppToolWindow tool() {
        return (AppToolWindow) super.tool();
    }

    public void centerOnDesktop() {
        ((AppWindowPeer) peer()).centerOnDesktop();
    }

    @Override
    public void close() {
        tool().close();
    }
}

