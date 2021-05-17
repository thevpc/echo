package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFileChooser;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppFileChooserModel;

public abstract class FileBase extends AppControlBase implements AppFileChooser {
    public FileBase(AppFileChooserModel model, Class<? extends AppFileChooserModel> modelType,
                    Class<? extends AppComponent> componentType,
                    Class<? extends AppComponentPeer> peerType) {
        super(model
                , modelType, componentType, peerType);
    }

    public AppFileChooserModel model() {
        return (AppFileChooserModel) super.model();
    }

}

