package net.thevpc.echo.impl.components;

import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppSpacer;
import net.thevpc.echo.api.peers.AppSpacerPeer;
import net.thevpc.echo.api.tools.AppSpacerModel;
import net.thevpc.echo.impl.tools.SpacerModel;

public class Spacer extends AppControlBase implements AppSpacer {
    public Spacer(AppSpacerModel tool) {
        super(tool
                , AppSpacerModel.class, AppSpacer.class, AppSpacerPeer.class
        );
    }
    public Spacer(Application app) {
        this(new SpacerModel(app));
    }

    public AppSpacerModel model() {
        return (AppSpacerModel) super.model();
    }

    public WritableDouble width() {
        return model().width();
    }

    public WritableDouble height() {
        return model().height();
    }


    public Spacer expandX() {
        width().set(Integer.MAX_VALUE);
        return this;
    }

    public Spacer expandX(int x) {
        width().set(x);
        return this;
    }

    public Spacer expandY(int y) {
        width().set(y);
        return this;
    }

    public Spacer expandY() {
        width().set(Integer.MAX_VALUE);
        return this;
    }
}

