package net.thevpc.echo.api.tools;

import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.WritableValue;

public interface AppToolCustom extends AppTool {
    WritableValue<Object> renderer();
}
