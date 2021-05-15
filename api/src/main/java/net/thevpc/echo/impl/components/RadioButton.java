package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppRadioButton;
import net.thevpc.echo.api.tools.AppToolToggle;

public class RadioButton extends Toggle implements AppRadioButton {
    public RadioButton(Application app) {
        super(app);
    }
    public RadioButton(AppToolToggle tool) {
        super(tool);
    }

}

