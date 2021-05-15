package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppSpacer;
import net.thevpc.echo.api.tools.AppToolSpacer;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.impl.tools.ToolSeparator;

public class Spacer extends AppComponentBase implements AppSpacer {
    public Spacer(AppToolSpacer tool) {
        super(tool);
    }
    public Spacer(Application app) {
        super(new ToolSeparator(app));
    }

    public AppToolSpacer tool() {
        return (AppToolSpacer) super.tool();
    }

}

