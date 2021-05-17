package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppRadioButton;
import net.thevpc.echo.api.peers.AppRadioButtonPeer;
import net.thevpc.echo.api.tools.AppToggleModel;
import net.thevpc.echo.impl.tools.ToggleModel;

public class RadioButton extends ToggleBase implements AppRadioButton {
    public RadioButton(Application app) {
        this(new ToggleModel(app));
    }
    public RadioButton(AppToggleModel tool) {
        super(tool
                , AppToggleModel.class, AppRadioButton.class, AppRadioButtonPeer.class
        );
    }
    public RadioButton(String id, String group, Application app) {
        this(new ToggleModel(id, group,app));
    }

}

