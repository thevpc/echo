package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.echo.AppToolCheckBox;
import net.thevpc.common.props.Props;
import net.thevpc.echo.Application;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppTools;

public class AppToolCheckBoxImpl extends AbstractAppTool implements AppToolCheckBox {

    private WritableValue<String> group = Props.of("group").valueOf(String.class, null);
    private WritableValue<Boolean> selected = Props.of("selected").valueOf(Boolean.class, false);

    public AppToolCheckBoxImpl(String id, String group, Application app, AppTools tools) {
        super(id, app, tools);
    }

    @Override
    public WritableValue<String> group() {
        return group;
    }

    @Override
    public WritableValue<Boolean> selected() {
        return selected;
    }
}
