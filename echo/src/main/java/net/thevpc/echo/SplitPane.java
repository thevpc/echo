package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppSplitPane;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.spi.peers.AppSplitPanePeer;

public class SplitPane extends ContainerBase<AppComponent>
        implements AppSplitPane {
    private WritableValue<Orientation> orientation = Props.of("orientation").valueOf(Orientation.class, Orientation.HORIZONTAL);

    public SplitPane(String id, Orientation orientation, Application app) {
        super(id, app,
                AppSplitPane.class, AppSplitPanePeer.class,
                AppComponent.class);
        this.orientation.set(orientation == null ? Orientation.HORIZONTAL : Orientation.VERTICAL);
    }

    public SplitPane(Orientation orientation, Application app) {
        this(null, orientation, app);
    }

    public WritableValue<Orientation> orientation() {
        return orientation;
    }
}

