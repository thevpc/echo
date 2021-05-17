package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppText;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppTextModel;

public class TextBase extends AppControlBase implements AppText {
    public TextBase(AppTextModel tool,
                    Class<? extends AppTextModel> modelType,
                    Class<? extends AppText> componentType,
                    Class<? extends AppComponentPeer> peerType) {
        super(tool
                , modelType, componentType, peerType
        );
    }

    public AppTextModel model() {
        return (AppTextModel) super.model();
    }

}