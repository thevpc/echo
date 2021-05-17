package net.thevpc.echo.impl.tools;

import net.thevpc.echo.api.tools.AppSeparatorModel;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.Application;

public class SeparatorModel extends AppComponentModelBase implements AppSeparatorModel {

    private final WritableDouble width = Props.of("width").doubleOf(0.0);
    private final WritableDouble height = Props.of("height").doubleOf(0.0);
    public SeparatorModel(String id, Application app) {
        super(id, app,false);
        propagateEvents(width,height);
    }
    public SeparatorModel(Application app) {
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

}
