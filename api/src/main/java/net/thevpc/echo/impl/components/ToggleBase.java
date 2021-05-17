package net.thevpc.echo.impl.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppToggle;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppToggleModel;

public class ToggleBase extends AppControlBase implements AppToggle {
    public ToggleBase(AppToggleModel tool, Class<? extends AppToggleModel> modelType,
                      Class<? extends AppComponent> componentType,
                      Class<? extends AppComponentPeer> peerType) {
        super(tool
                , modelType, componentType, peerType);
    }

    public AppToggleModel model() {
        return (AppToggleModel) super.model();
    }

    public WritableString group() {
        return model().group();
    }

    public WritableBoolean selected() {
        return model().selected();
    }
}

