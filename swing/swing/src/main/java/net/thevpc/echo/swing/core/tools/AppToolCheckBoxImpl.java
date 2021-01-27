package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.echo.AppToolCheckBox;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritablePValue;

public class AppToolCheckBoxImpl extends AbstractAppTool implements AppToolCheckBox {
    private String id;
    private WritablePValue<String> group = Props.of("group").valueOf(String.class, null);
    private WritablePValue<Boolean> selected = Props.of("selected").valueOf(Boolean.class, false);


    public AppToolCheckBoxImpl(String id, String group) {
        super(id);
        this.id = id;
    }


    @Override
    public String id() {
        return id;
    }

    @Override
    public WritablePValue<String> group() {
        return group;
    }

    @Override
    public WritablePValue<Boolean> selected() {
        return selected;
    }
}
