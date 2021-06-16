package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.spi.peers.AppWindowPeer;

public class Window extends ContainerBase<AppComponent> implements AppWindow {
    protected WritableBoolean closable = Props.of("closable").booleanOf(true);
    protected WritableBoolean iconifiable = Props.of("iconifiable").booleanOf(true);
    protected WritableValue<AppComponent> component = Props.of("component").valueOf(AppComponent.class, null);
    protected WindowStateSetValue state = new WindowStateSetValue("state");

    public Window(String id, Str title, AppComponent component) {
        this(id, title, Anchor.CENTER, component, component.app());
    }

    public Window(String id, Str title, Anchor anchor, AppComponent component) {
        this(id, title, anchor, component, component.app());
    }

    public Window(String id, Str title, Anchor anchor, Application app) {
        this(id, title, anchor, null, app);
    }

    public Window(String id, Str title, Anchor anchor, AppComponent component, Application app) {
        super(id, app, AppWindow.class, AppWindowPeer.class, AppComponent.class);
        this.title().set(title == null ? Str.of("") : title);
        this.component.set(component);
        this.anchor().set(anchor);
        propagateEvents(this.component);
    }

    public Window(Application app) {
        this(null, null, Anchor.CENTER, null, app);
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
    public WindowStateSetValue state() {
        return state;
    }

    @Override
    public void close() {
        state.add(WindowState.CLOSING);
    }

    public void centerOnDesktop() {
        app().toolkit().runUI(() -> {
            if (parent instanceof AppDesktop) {
                Dimension dsize = ((AppDesktop) parent).size();
                Bounds wbound = bounds().get();
                if(wbound!=null) {
                    double x = (dsize.getWidth() - wbound.getWidth()) / 2;
                    double y = (dsize.getHeight() - wbound.getHeight()) / 2;
                    resize(x, y, dsize.getWidth(), dsize.getHeight());
                }
            }
        });
    }

    @Override
    public void resize(double x, double y, double w, double h) {
        app().toolkit().runUI(() -> {
            ((AppWindowPeer) peer()).resize(x, y, w, h);
        });
    }
}

