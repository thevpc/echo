package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppTable;
import net.thevpc.echo.api.peers.AppTablePeer;
import net.thevpc.echo.api.tools.AppTableModel;
import net.thevpc.echo.impl.tools.TableModel;

public class Table extends AppControlBase implements AppTable {
    public Table(AppTableModel tool) {
        super(tool
                , AppTableModel.class, AppTable.class, AppTablePeer.class
        );
    }

    public Table(Application app) {
        this(new TableModel(app));
    }

    public AppTableModel model() {
        return (AppTableModel) super.model();
    }

}

