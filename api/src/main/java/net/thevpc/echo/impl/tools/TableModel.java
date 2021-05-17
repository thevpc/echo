package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppTableModel;

public class TableModel extends AppComponentModelBase implements AppTableModel {
    public TableModel(String id, Application app) {
        super(id, app);
        propagateEvents();
    }

    public TableModel(Application app) {
        this(null,app);
    }

}
