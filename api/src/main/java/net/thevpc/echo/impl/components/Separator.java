package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppSeparator;
import net.thevpc.echo.api.peers.AppSeparatorPeer;
import net.thevpc.echo.api.tools.AppSeparatorModel;
import net.thevpc.echo.impl.tools.SeparatorModel;

public class Separator extends AppControlBase implements AppSeparator {
    public Separator(AppSeparatorModel tool) {
        super(tool
                , AppSeparatorModel.class, AppSeparator.class, AppSeparatorPeer.class
        );
    }
    public Separator(Application app) {
        this(new SeparatorModel(app));
    }

    public AppSeparatorModel model() {
        return (AppSeparatorModel) super.model();
    }

}

