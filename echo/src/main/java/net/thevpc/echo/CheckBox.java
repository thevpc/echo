package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppCheckBox;
import net.thevpc.echo.impl.components.ToggleBase;
import net.thevpc.echo.spi.peers.AppCheckBoxPeer;

public class CheckBox extends ToggleBase implements AppCheckBox {
    public CheckBox(String id, Str txt, String group, Application app) {
        super(id, txt, group, app, AppCheckBox.class, AppCheckBoxPeer.class);
    }

    public CheckBox(String id, Str txt, Application app) {
        this(id, txt, null, app);
    }

    public CheckBox(String id, Application app) {
        this(id, null, app);
    }
    public CheckBox(Application app) {
        this(null, null, app);
    }
    public CheckBox(Str text, Application app) {
        this(null, text, app);
    }

}

