package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.impl.tools.ToolText;
import net.thevpc.echo.api.peers.AppComponentPeer;

public class Label extends AppComponentBase implements AppLabel {
    public Label(Application app) {
        super(new ToolText(app));
    }

    public Label(AppToolText app) {
        super(app);
    }


    public AppToolText tool() {
        return (AppToolText) super.tool();
    }

}

