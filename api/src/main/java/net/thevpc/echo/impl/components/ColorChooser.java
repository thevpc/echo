package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.api.peers.AppColorChooserPeer;
import net.thevpc.echo.api.tools.AppColorChooserModel;
import net.thevpc.echo.impl.tools.ColorChooserModel;

public class ColorChooser extends ColorBase implements AppColorChooser {
    public ColorChooser(AppColorChooserModel model) {
        super(model
                , AppColorChooserModel.class, AppColorChooser.class, AppColorChooserPeer.class
        );
    }

    public ColorChooser(Application app) {
        this(new ColorChooserModel(app));
    }

    public AppColorChooserModel model() {
        return (AppColorChooserModel) super.model();
    }

}

