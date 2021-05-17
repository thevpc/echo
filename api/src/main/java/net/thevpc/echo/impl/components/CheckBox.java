package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppCheckBox;
import net.thevpc.echo.api.peers.AppCheckBoxPeer;
import net.thevpc.echo.api.tools.AppToggleModel;
import net.thevpc.echo.impl.tools.ToggleModel;

public class CheckBox extends ToggleBase implements AppCheckBox {
    public CheckBox(Application app) {
        this(new ToggleModel(app));
    }
    public CheckBox(AppToggleModel tool) {
        super(tool
                , AppToggleModel.class, AppCheckBox.class, AppCheckBoxPeer.class
        );
    }
    public CheckBox(String id, Application app) {
        this(new ToggleModel(id, null,app));
    }

}

