package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppToggle;
import net.thevpc.echo.api.peers.AppColorChooserPeer;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppColorChooserModel;
import net.thevpc.echo.api.tools.AppToggleModel;

public class ColorBase extends AppControlBase implements AppColorChooser {
    public ColorBase(AppColorChooserModel model, Class<? extends AppColorChooserModel> modelType,
                     Class<? extends AppComponent> componentType,
                     Class<? extends AppComponentPeer> peerType) {
        super(model
                , modelType, componentType, peerType);
    }

    public AppColorChooserModel model() {
        return (AppColorChooserModel) super.model();
    }

}

