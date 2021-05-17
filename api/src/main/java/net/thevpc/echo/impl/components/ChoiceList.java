package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppChoiceList;
import net.thevpc.echo.api.peers.AppChoiceListPeer;
import net.thevpc.echo.api.tools.AppChoiceModel;
import net.thevpc.echo.impl.tools.ChoiceModel;

public class ChoiceList<T> extends ChoiceBase<T> implements AppChoiceList<T> {
    public ChoiceList(AppChoiceModel<T> tool) {
        super(tool
                , AppChoiceModel.class, AppChoiceList.class, AppChoiceListPeer.class
        );
    }
    public ChoiceList(Class<T> componentType, Application app) {
        this(new ChoiceModel<>(componentType,app));
    }

    public AppChoiceModel<T> model() {
        return (AppChoiceModel<T>) super.model();
    }

}

