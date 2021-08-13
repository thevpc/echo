package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.api.components.AppColorControl;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class ColorBase extends ControlBase implements AppColorControl {
    private WritableValue<AppColor> value= Props.of("value").valueOf(AppColor.class);
    public ColorBase(String id, Application app,
                    Class<? extends AppComponent> componentType,
                    Class<? extends AppComponentPeer> peerType) {
        super(id
                , app, componentType, peerType);
        propagateEvents(value);
    }

    @Override
    public WritableValue<AppColor> value() {
        return value;
    }


}

