package net.thevpc.echo.impl.components;

import net.thevpc.echo.api.components.AppChoice;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppChoiceModel;
import net.thevpc.echo.api.tools.AppComponentModel;

public class ChoiceBase<T> extends AppControlBase implements AppChoice<T> {
    public ChoiceBase(AppChoiceModel<T> tool
            , Class<? extends AppChoiceModel> modelType,
                      Class<? extends AppComponent> componentType,
                      Class<? extends AppComponentPeer> peerType
                  ) {
        super(tool,modelType,componentType,peerType);
    }
//    public ChoiceBase(Class<T> componentType,Application app) {
//        super(new ChoiceModel<T>(componentType,app));
//    }

    public AppChoiceModel<T> model() {
        return (AppChoiceModel<T>) super.model();
    }

}

