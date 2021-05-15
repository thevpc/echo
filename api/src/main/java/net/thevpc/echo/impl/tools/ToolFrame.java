package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppWindowDisplayMode;
import net.thevpc.echo.AppWindowStateSetValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.WritableStr;
import net.thevpc.echo.api.tools.AppToolFrame;
import net.thevpc.echo.props.AppProps;
import net.thevpc.echo.props.WritableImage;

public class ToolFrame extends ToolFolder implements AppToolFrame {
    public WritableValue<AppWindowDisplayMode> displayMode = Props.of("displayMode")
            .adjust(e -> {
                if(e.getNewValue() == null){
                    e.doInstead(()->e.getWritableValue().set(AppWindowDisplayMode.NORMAL));
                }
            } )
            .valueOf(AppWindowDisplayMode.class, AppWindowDisplayMode.NORMAL);
    private AppWindowStateSetValue state;
    private WritableBoolean active;
    private WritableBoolean closable;
    private WritableBoolean iconifiable;


    public ToolFrame(String id, Application app) {
        super(id, app);
        init();
    }

    public ToolFrame(Application app) {
        super(app);
        init();
    }

    protected void init() {
        active = AppProps.of("active", app()).booleanOf(true);
        closable = AppProps.of("closable", app()).booleanOf(true);
        iconifiable = AppProps.of("iconifiable", app()).booleanOf(true);
        state = new AppWindowStateSetValue("state");
    }

    @Override
    public WritableBoolean active() {
        return active;
    }

    @Override
    public WritableBoolean closable() {
        return closable;
    }

    @Override
    public WritableBoolean iconifiable() {
        return iconifiable;
    }

    public AppWindowStateSetValue state() {
        return state;
    }

    @Override
    public WritableValue<AppWindowDisplayMode> displayMode() {
        return displayMode;
    }
}
