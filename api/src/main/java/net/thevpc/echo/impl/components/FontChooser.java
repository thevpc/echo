package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.api.peers.AppFontChooserPeer;
import net.thevpc.echo.api.tools.AppFontChooserModel;
import net.thevpc.echo.impl.tools.FontChooserModel;

public class FontChooser extends FontBase implements AppFontChooser {
    public FontChooser(AppFontChooserModel tool) {
        super(tool
                , AppFontChooserModel.class, AppFontChooser.class, AppFontChooserPeer.class

        );
    }
    public FontChooser(Application app) {
        this(new FontChooserModel(app));
    }

    public AppFontChooserModel model() {
        return (AppFontChooserModel) super.model();
    }

}

