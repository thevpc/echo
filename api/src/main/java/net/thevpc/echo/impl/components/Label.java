package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.api.peers.AppLabelPeer;
import net.thevpc.echo.api.tools.AppTextModel;
import net.thevpc.echo.impl.tools.TextModel;

public class Label extends AppControlBase implements AppLabel {
    public Label(Application app) {
        this(new TextModel(app));
    }

    public Label(Str text, Application app) {
        this(new TextModel(text,app));
    }
    public Label(AppTextModel tool) {
        super(tool
                , AppTextModel.class, AppLabel.class, AppLabelPeer.class
        );
    }


    public AppTextModel model() {
        return (AppTextModel) super.model();
    }

}

