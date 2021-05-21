package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.impl.components.FontBase;
import net.thevpc.echo.spi.peers.AppFontChooserPeer;

public class FontChooser extends FontBase implements AppFontChooser {
    public FontChooser(Application app) {
        this(null,app);
    }

    public FontChooser(String id,Application app) {
        super(id,app, AppFontChooser.class, AppFontChooserPeer.class);
    }



    @Override
    public boolean showDialog(AppComponent owner) {
        return false;
    }
}

