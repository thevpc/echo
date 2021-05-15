package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.api.AppPath;

public interface AppToolSeparator extends AppTool {
    WritableDouble width();

    WritableDouble height();
}
