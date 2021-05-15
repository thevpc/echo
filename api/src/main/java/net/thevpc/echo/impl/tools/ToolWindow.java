/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.WritableStr;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.tools.AppToolWindow;
import net.thevpc.echo.impl.components.AppComponentBase;
import net.thevpc.echo.props.AppProps;
import net.thevpc.echo.props.WritableImage;

/**
 *
 * @author thevpc
 */
public class ToolWindow extends AppToolBase implements AppToolWindow {

    protected WritableBoolean active = Props.of("activated").booleanOf(true);
    protected WritableBoolean closable = Props.of("closable").booleanOf(true);
    protected WritableBoolean iconifiable = Props.of("iconifiable").booleanOf(true);
    protected WritableValue<AppWindowAnchor> anchor = Props.of("anchor").valueOf(AppWindowAnchor.class, AppWindowAnchor.CONTENT);
    protected WritableValue<AppComponent> component = Props.of("component").valueOf(AppComponent.class, null);
    protected AppWindowStateSetValue state = new AppWindowStateSetValue("state");
    protected Application app;

    public ToolWindow(String id, AppWindowAnchor anchor, AppComponent component, Application app) {
        super(id,app);
        this.app = app;
        this.component.set(component);
        this.anchor.set(anchor);
    }

    @Override
    public WritableValue<AppWindowAnchor> anchor() {
        return anchor;
    }

    @Override
    public WritableBoolean closable() {
        return closable;
    }

    @Override
    public WritableBoolean iconifiable() {
        return iconifiable;
    }

    @Override
    public WritableValue<AppComponent> component() {
        return component;
    }

    @Override
    public WritableBoolean active() {
        return active;
    }

    @Override
    public AppWindowStateSetValue state() {
        return state;
    }

    @Override
    public void close() {
        state.add(AppWindowState.CLOSING);
    }

}
