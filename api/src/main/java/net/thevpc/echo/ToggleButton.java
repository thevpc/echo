package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppToggleButton;
import net.thevpc.echo.impl.components.ToggleBase;
import net.thevpc.echo.spi.peers.AppToggleButtonPeer;

public class ToggleButton extends ToggleBase implements AppToggleButton {
    public ToggleButton(String id, Str txt, String group, Application app) {
        super(id, txt, group, app, AppToggleButton.class, AppToggleButtonPeer.class);
    }

    public ToggleButton(String id, String group, Application app) {
        this(id, null, group, app);
    }

}

