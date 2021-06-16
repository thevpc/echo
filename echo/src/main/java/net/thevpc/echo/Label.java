package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.impl.components.TextBase;
import net.thevpc.echo.spi.peers.AppLabelPeer;

public class Label extends TextBase implements AppLabel {
    public Label(Application app) {
        this(null,null,app);
    }

    public Label(Str text, Application app) {
        this(null,text,app);
    }

    public Label(String id,Str text, Application app) {
        super(id,text,app, AppLabel.class, AppLabelPeer.class);
    }

}

