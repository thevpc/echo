package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppColorButton;
import net.thevpc.echo.impl.components.ColorBase;
import net.thevpc.echo.spi.peers.AppColorButtonPeer;

public class ColorButton extends ColorBase implements AppColorButton {
    public ColorButton(Application app) {
        this(null, app);
    }

    public ColorButton(String id, Application app) {
        super(id, app, AppColorButton.class, AppColorButtonPeer.class);
        if (id != null) {
            String aid = "Action." + id+".tooltip";
            tooltip().set(Str.i18n(aid));
        }
    }

}

