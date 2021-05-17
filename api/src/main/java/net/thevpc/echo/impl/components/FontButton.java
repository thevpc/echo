package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.api.peers.AppFontChooserPeer;
import net.thevpc.echo.api.tools.AppFontChooserModel;
import net.thevpc.echo.impl.tools.FontChooserModel;

public class FontButton extends FontBase implements AppFontChooser {
    public FontButton(AppFontChooserModel tool) {
        super(tool
                , AppFontChooserModel.class, AppFontChooser.class, AppFontChooserPeer.class
        );
    }
    public FontButton(Application app) {
        this(new FontChooserModel(app));
    }


    public AppFontChooserModel model() {
        return (AppFontChooserModel) super.model();
    }

}

