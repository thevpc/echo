package net.thevpc.echo;

import net.thevpc.echo.api.components.AppChoiceList;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.components.ChoiceBase;
import net.thevpc.echo.spi.peers.AppChoiceListPeer;

public class ChoiceList<T> extends ChoiceBase<T> implements AppChoiceList<T> {

    public ChoiceList(Class<T> itemType, Application app) {
        this(null, itemType, app);
    }

    public ChoiceList(String id, Class<T> itemType, Application app) {
        super(id, itemType, true, app,
                (Class<? extends AppComponent>) AppChoiceList.class, AppChoiceListPeer.class);
    }


}

