package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.api.components.AppSpacer;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.spi.peers.AppSpacerPeer;

public class Spacer extends ControlBase implements AppSpacer {
    private final WritableDouble width = Props.of("width").doubleOf(0.0);
    private final WritableDouble height = Props.of("height").doubleOf(0.0);

    public Spacer(Application app) {
        this(null, app);
    }

    public Spacer(String id, Application app) {
        super(id, app, AppSpacer.class, AppSpacerPeer.class);
        propagateEvents(width, height);
    }

    @Override
    public WritableDouble width() {
        return width;
    }

    @Override
    public WritableDouble height() {
        return height;
    }


    public Spacer expandX() {
        width().set(Integer.MAX_VALUE);
        return this;
    }

    public Spacer expandX(int x) {
        width().set(x);
        return this;
    }

    public Spacer expandY(int y) {
        width().set(y);
        return this;
    }

    public Spacer expandY() {
        width().set(Integer.MAX_VALUE);
        return this;
    }
}

