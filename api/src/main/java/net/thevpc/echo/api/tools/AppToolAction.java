package net.thevpc.echo.api.tools;


import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppAction;

public interface AppToolAction extends AppTool {
    AppActionValue action();
}
