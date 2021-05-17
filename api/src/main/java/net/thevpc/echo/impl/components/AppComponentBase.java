package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.iconset.WritableImage;

public class AppComponentBase implements AppComponent {
    protected AppComponent parent;
    protected WritableValue<Path> path = Props.of("path").valueOf(Path.class, Path.of());
    protected AppComponentPeer peer;
    protected Class<? extends AppComponent> componentType;
    protected Class<? extends AppComponentPeer> peerType;
    protected Class<? extends AppComponentModel> modelType;
    private WritableValue<Integer> order = Props.of("order").valueOf(Integer.class, null);
    private DefaultAppComponentConstraints constraints = new DefaultAppComponentConstraints("constraints");
    private AppComponentModel tool;

    public AppComponentBase(AppComponentModel tool,
                            Class<? extends AppComponentModel> modelType,
                            Class<? extends AppComponent> componentType,
                            Class<? extends AppComponentPeer> peerType) {
        this.tool = tool;
        this.modelType = modelType;
        this.componentType = componentType;
        this.peerType = peerType;
        this.path.set(Path.of(tool.id()));
    }

    @Override
    public String propertyName() {
        return tool == null ? null : tool.propertyName();
    }

    @Override
    public PropertyListeners listeners() {
        return tool == null ? null : tool.listeners();
    }

    @Override
    public PropertyType propertyType() {
        return tool == null ? null : tool.propertyType();
    }

    @Override
    public UserObjects userObjects() {
        return tool == null ? null : tool.userObjects();
    }

    @Override
    public AppComponentConstraints constraints() {
        return constraints;
    }

    @Override
    public AppComponent setOptions(AppComponentOptions options) {
        if (options != null) {
            order.set(options.order());
        }
        return this;
    }

    @Override
    public AppComponent parent() {
        return parent;
    }

    public AppComponentModel model() {
        return tool;
    }

    public WritableValue<Path> path() {
        return path;
    }

    public ObservableValue<Integer> order() {
        return order.readOnly();
    }

    public Application app() {
        return tool.app();
    }

    @Override
    public AppComponentPeer peer() {
        return peer(true);
    }

    @Override
    public AppComponentPeer peer(boolean prepareShowing) {
        if (!prepareShowing) {
            return peer;
        }
        if (peer == null) {
            AppComponentPeer p = (AppComponentPeer) app().toolkit().createComponentPeer(this);
            p.install(this);
            peer = p;
        }
        return peer;
    }

    @Override
    public Class<? extends AppComponentModel> modelType() {
        return modelType;
    }

    @Override
    public Class<? extends AppComponent> componentType() {
        return componentType;
    }

    @Override
    public Class<? extends AppComponentPeer> peerType() {
        return peerType;
    }

    protected void prepareUnshowing() {
        if (peer != null) {
            app().toolkit().runUI(() -> {
                AppComponentPeer p = peer();
                p.uninstall();
                peer = null;
            });
        }
    }

    public String id() {
        return model().id();
    }

    public WritableStr title() {
        return model().title();
    }

    public WritableImage smallIcon() {
        return model().smallIcon();
    }

    public WritableImage largeIcon() {
        return model().largeIcon();
    }

    public WritableString accelerator() {
        return model().accelerator();
    }

    public WritableInt mnemonic() {
        return model().mnemonic();
    }

    public WritableBoolean visible() {
        return model().visible();
    }

    public WritableStr tooltip() {
        return model().tooltip();
    }

    public void internal_setParent(AppComponent parent) {
        this.parent = parent;
    }
}
