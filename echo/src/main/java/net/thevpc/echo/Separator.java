package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableDouble;
import net.thevpc.echo.api.components.AppSeparator;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.spi.peers.AppSeparatorPeer;

public class Separator extends ControlBase implements AppSeparator {

    private final WritableDouble width = Props.of("width").doubleOf(0.0);
    private final WritableDouble height = Props.of("height").doubleOf(0.0);
    public Separator(String id,Application app) {
        super(id,app, AppSeparator.class, AppSeparatorPeer.class);
        propagateEvents(width,height);
    }
    public Separator(Application app) {
        this(null,app);
    }
    @Override
    public WritableDouble width() {
        return width;
    }

    @Override
    public WritableDouble height() {
        return height;
    }

}

