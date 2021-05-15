package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppButton;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.tools.AppToolAction;
import net.thevpc.echo.impl.tools.ToolAction;
import net.thevpc.echo.api.peers.AppComponentPeer;

public class Button extends AppComponentBase implements AppButton {
    public Button(Application app) {
        super(new ToolAction(app));
    }
    public Button(AppToolAction tool) {
        super(tool);
    }


    public AppToolAction tool() {
        return (AppToolAction) super.tool();
    }

}

