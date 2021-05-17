package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppToggle;
import net.thevpc.echo.api.peers.AppTogglePeer;
import net.thevpc.echo.api.tools.AppToggleModel;
import net.thevpc.echo.impl.tools.ToggleModel;

public class Toggle extends ToggleBase {
    public Toggle(Application app) {
        this(new ToggleModel(app));
    }

    public Toggle(AppToggleModel tool) {
        super(tool
                , AppToggleModel.class, AppToggle.class, AppTogglePeer.class);
    }

    public AppToggleModel model() {
        return (AppToggleModel) super.model();
    }

}

