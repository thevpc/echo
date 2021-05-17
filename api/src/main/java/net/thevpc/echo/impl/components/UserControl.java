package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppUserControl;
import net.thevpc.echo.api.peers.AppUserControlPeer;
import net.thevpc.echo.api.tools.AppUserControlModel;
import net.thevpc.echo.impl.tools.AppComponentModelBase;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.impl.tools.UserControlModel;

public class UserControl extends AppControlBase implements AppUserControl{
    public UserControl(AppUserControlModel model) {
        super(model,
                AppUserControlModel.class,
                AppUserControl.class,
                AppUserControlPeer.class
        );
    }
    public UserControl(Application app) {
        this(new UserControlModel(app));
    }

    public void setPeer(AppUserControlPeer peer) {
        this.peer=peer;
    }
}

