package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppFontChooserModel;

public class FontBase extends AppControlBase implements AppFontChooser {
    public FontBase(AppFontChooserModel model, Class<? extends AppFontChooserModel> modelType,
                    Class<? extends AppComponent> componentType,
                    Class<? extends AppComponentPeer> peerType) {
        super(model
                , modelType, componentType, peerType);
    }

    public AppFontChooserModel model() {
        return (AppFontChooserModel) super.model();
    }

}

