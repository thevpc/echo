package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppTreeModel;

public class TreeModel extends AppComponentModelBase implements AppTreeModel {
    public TreeModel(String id, Application app) {
        super(id, app);
        propagateEvents();
    }

    public TreeModel(Application app) {
        this(null,app);
    }

}
