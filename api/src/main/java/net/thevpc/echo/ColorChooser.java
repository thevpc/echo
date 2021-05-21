package net.thevpc.echo;

import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.impl.components.ColorBase;
import net.thevpc.echo.spi.peers.AppColorChooserPeer;

public class ColorChooser extends ColorBase implements AppColorChooser {
    public ColorChooser(Application app) {
        this(null, app);
    }

    public ColorChooser(String id, Application app) {
        super(id, app, AppColorChooser.class, AppColorChooserPeer.class);
    }

}

