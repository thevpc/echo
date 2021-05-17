package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.Action;
import net.thevpc.echo.api.components.AppButton;
import net.thevpc.echo.api.peers.AppButtonPeer;
import net.thevpc.echo.api.tools.AppActionValue;
import net.thevpc.echo.api.tools.AppToolButtonModel;
import net.thevpc.echo.impl.tools.ButtonModel;

public class Button extends AppControlBase implements AppButton {
    public Button(Application app) {
        this(new ButtonModel(app));
    }

    public Button(AppToolButtonModel tool) {
        super(tool, AppToolButtonModel.class, AppButton.class, AppButtonPeer.class);
    }

    public Button(String id, Application app) {
        this(new ButtonModel(id, app));
    }

    public Button(String id, Action a, Application app) {
        this(new ButtonModel(id, a, app));
    }

    public Button(String id, Runnable a, Application app) {
        this(new ButtonModel(id, a, app));
    }

    public AppActionValue action() {
        return model().action();
    }

    public AppToolButtonModel model() {
        return (AppToolButtonModel) super.model();
    }

}

