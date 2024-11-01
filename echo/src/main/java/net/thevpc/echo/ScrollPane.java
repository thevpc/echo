package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppScrollPane;
import net.thevpc.echo.impl.components.ContainerOneBase;
import net.thevpc.echo.spi.peers.AppComponentPeer;
import net.thevpc.echo.spi.peers.AppScrollPanePeer;

public class ScrollPane
        extends ContainerOneBase<AppComponent>
        implements AppScrollPane {
    public ScrollPane(Application app) {
        this(null, null, app);
    }

    public ScrollPane(AppComponent content) {
        this(null, content, content.app());
    }

    public ScrollPane(String id, AppComponent content, Application app) {
        super(id, app, "content", true,
                AppScrollPane.class,
                AppScrollPanePeer.class, AppComponent.class);
        if (content != null) {
            child().set(content);
        }
    }

    public int getMaxX() {
        AppScrollPanePeer peer1 = (AppScrollPanePeer) peer();
        return peer1.getMaxX();
    }

    public int getMaxY() {
        AppScrollPanePeer peer1 = (AppScrollPanePeer) peer();
        return peer1.getMaxY();
    }

    public void scrollTo(Float x, Float y) {
        AppScrollPanePeer peer1 = (AppScrollPanePeer) peer();
        peer1.scrollTo(x,y);
    }

    public void scrollTo(Integer x, Integer y) {
        AppScrollPanePeer peer1 = (AppScrollPanePeer) peer();
        peer1.scrollTo(x,y);
    }
}
