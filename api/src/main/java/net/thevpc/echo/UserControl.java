package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppUserControl;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.spi.peers.AppUserControlPeer;

public class UserControl extends ControlBase implements AppUserControl{
    private WritableValue<Object> renderer= Props.of("renderer").valueOf(Object.class);
    public UserControl(String id, Object toolkitControl,Application app) {
        super(id,app,
                AppUserControl.class,
                AppUserControlPeer.class
        );
        this.renderer.set(toolkitControl);
        propagateEvents(this.renderer);
    }

    public UserControl(Application app) {
        this(null,null,app);
    }

    public void setPeer(AppUserControlPeer peer) {
        this.peer=peer;
    }
    @Override
    public WritableValue<Object> renderer() {
        return renderer;
    }

}

