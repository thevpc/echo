package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppChoiceList;
import net.thevpc.echo.api.components.AppComboBox;
import net.thevpc.echo.api.peers.AppChoiceListPeer;
import net.thevpc.echo.api.tools.AppChoiceModel;
import net.thevpc.echo.impl.tools.ChoiceModel;

public class ComboBox<T> extends ChoiceBase<T> implements AppComboBox<T> {
    public ComboBox(AppChoiceModel<T> tool) {
        super(tool
                , AppChoiceModel.class, AppChoiceList.class, AppChoiceListPeer.class
        );
    }
    public ComboBox(Class<T> componentType, Application app) {
        this(new ChoiceModel<T>(componentType,app));
    }

    public AppChoiceModel<T> model() {
        return (AppChoiceModel<T>) super.model();
    }

}

