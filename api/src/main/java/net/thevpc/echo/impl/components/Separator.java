package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppSeparator;
import net.thevpc.echo.api.tools.AppToolSeparator;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.impl.tools.ToolSeparator;

public class Separator extends AppComponentBase implements AppSeparator {
    public Separator(AppToolSeparator tool) {
        super(tool);
    }
    public Separator(Application app) {
        super(new ToolSeparator(app));
    }

    public AppToolSeparator tool() {
        return (AppToolSeparator) super.tool();
    }

}

