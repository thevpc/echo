package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppRadioButton;
import net.thevpc.echo.impl.components.ToggleBase;
import net.thevpc.echo.spi.peers.AppRadioButtonPeer;

public class RadioButton extends ToggleBase implements AppRadioButton {
    public RadioButton(String id, Str txt, String group, Application app) {
        super(id, txt, group, app, AppRadioButton.class, AppRadioButtonPeer.class);
    }

    public RadioButton(String id, String group, Application app) {
        this(id, null, group, app);
    }

}

