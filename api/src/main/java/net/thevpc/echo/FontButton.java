package net.thevpc.echo;

import net.thevpc.echo.api.components.AppFontButton;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.impl.components.FontBase;
import net.thevpc.echo.spi.peers.AppFontChooserPeer;

public class FontButton extends FontBase implements AppFontButton {
    public FontButton(Application app) {
        this(null, app);
    }

    public FontButton(String id, Application app) {
        super(id, app, AppFontChooser.class, AppFontChooserPeer.class);
    }

}

