package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolSeparator;
import net.thevpc.echo.api.tools.AppToolSpacer;

public class ToolSpacer extends AppToolBase implements AppToolSpacer {

    private final WritableDouble width = Props.of("width").doubleOf(0.0);
    private final WritableDouble height = Props.of("height").doubleOf(0.0);
    public ToolSpacer(String id, Application app) {
        super(id, app,false);
    }
    public ToolSpacer(Application app) {
        super(null, app,false);
    }

    @Override
    public WritableDouble width() {
        return width;
    }

    @Override
    public WritableDouble height() {
        return height;
    }

    public ToolSpacer expandX() {
        width().set(Integer.MAX_VALUE);
        return this;
    }

    public ToolSpacer expandX(int x) {
        width().set(x);
        return this;
    }

    public ToolSpacer expandY(int y) {
        width().set(y);
        return this;
    }

    public ToolSpacer expandY() {
        width().set(Integer.MAX_VALUE);
        return this;
    }
}
