package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.AppBounds;
import net.thevpc.echo.AppDimension;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.api.tools.AppWindowModel;
import net.thevpc.echo.impl.tools.WindowModel;

public class Window extends AppContainerBase<AppComponentModel, AppComponent> implements AppWindow {
    public Window(String id, Str title, AppWindowAnchor anchor, AppComponent component) {
        this(new WindowModel(id, title, anchor, component, component.app()));
    }

    public Window(String id, Str title, AppWindowAnchor anchor, Application app) {
        this(new WindowModel(id, title, anchor, null, app));
    }

    public Window(String id, Str title, AppWindowAnchor anchor, AppComponent component, Application app) {
        this(new WindowModel(id, title, anchor, component, app));
    }

    public Window(AppWindowModel tool) {
        super(tool,
                AppContainerModel.class, AppWindow.class, AppWindowPeer.class,
                AppComponentModel.class, AppComponent.class);
    }

    public Window(Application app) {
        this(new WindowModel(null, null, AppWindowAnchor.CENTER, null, app));
    }

    @Override
    public AppWindowModel model() {
        return (AppWindowModel) super.model();
    }

    public void centerOnDesktop() {
        app().toolkit().runUI(() -> {
            if (parent instanceof AppDesktop) {
                AppDimension dsize = ((AppDesktop) parent).size();
                AppBounds wbound = bounds();
                double x = (dsize.getWidth() - wbound.getWidth()) / 2;
                double y = (dsize.getHeight() - wbound.getHeight()) / 2;
                resize(x, y, dsize.getWidth(), dsize.getHeight());
            }
        });
    }

    @Override
    public void close() {
        this.model().close();
    }

    @Override
    public void resize(double x, double y, double w, double h) {
        app().toolkit().runUI(() -> {
            ((AppWindowPeer) peer()).resize(x, y, w, h);
        });
    }

    public AppBounds bounds() {
        return ((AppWindowPeer) peer()).bounds();
    }
}

