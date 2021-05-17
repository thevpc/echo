package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppSpacerModel;

public class SpacerModel extends AppComponentModelBase implements AppSpacerModel {

    private final WritableDouble width = Props.of("width").doubleOf(0.0);
    private final WritableDouble height = Props.of("height").doubleOf(0.0);
    public SpacerModel(String id, Application app) {
        super(id, app,false);
        propagateEvents(width,height);
    }
    public SpacerModel(Application app) {
        this(null, app);
    }

    @Override
    public WritableDouble width() {
        return width;
    }

    @Override
    public WritableDouble height() {
        return height;
    }

    public SpacerModel expandX() {
        width().set(Integer.MAX_VALUE);
        return this;
    }

    public SpacerModel expandX(int x) {
        width().set(x);
        return this;
    }

    public SpacerModel expandY(int y) {
        width().set(y);
        return this;
    }

    public SpacerModel expandY() {
        width().set(Integer.MAX_VALUE);
        return this;
    }
}
