package net.thevpc.echo.impl.tools;

import net.thevpc.echo.api.tools.AppToolSeparator;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.Application;

public class ToolSeparator extends AppToolBase implements AppToolSeparator {

    private final WritableDouble width = Props.of("width").doubleOf(0.0);
    private final WritableDouble height = Props.of("height").doubleOf(0.0);
    public ToolSeparator(String id,Application app) {
        super(id, app,false);
    }
    public ToolSeparator(Application app) {
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

}
