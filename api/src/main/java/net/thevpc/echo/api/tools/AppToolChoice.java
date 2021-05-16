package net.thevpc.echo.api.tools;


import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppAction;

public interface AppToolChoice<T> extends AppTool {
    WritableList<T> values();

    WritableBoolean multipleValues();
}
