package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFontControl;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class FontBase extends ControlBase implements AppFontControl {
    private WritableValue<AppFont> selection = Props.of("selection").valueOf(AppFont.class);

    public FontBase(String id,Application app,
                    Class<? extends AppComponent> componentType,
                    Class<? extends AppComponentPeer> peerType) {
        super(id,app, componentType, peerType);
        propagateEvents(selection);
    }

    @Override
    public WritableValue<AppFont> selection() {
        return selection;
    }

}

