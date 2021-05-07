package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.common.props.Props;
import net.thevpc.echo.Application;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppToolButtonType;
import net.thevpc.echo.AppTools;
import net.thevpc.echo.AppToolToggle;

public class AppToolCheckBoxImpl extends AbstractAppTool implements AppToolToggle {

    private WritableValue<String> group = Props.of("group").valueOf(String.class, null);
    private WritableValue<Boolean> selected = Props.of("selected").valueOf(Boolean.class, false);
    private AppToolButtonType buttonType;

    public AppToolCheckBoxImpl(String id, AppToolButtonType buttonType, String group, Application app, AppTools tools) {
        super(id, app, tools);
        this.buttonType = buttonType;
    }

    @Override
    public AppToolButtonType buttonType() {
        return buttonType;
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
