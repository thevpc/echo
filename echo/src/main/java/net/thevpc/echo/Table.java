package net.thevpc.echo;

import net.thevpc.echo.api.components.AppTable;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.spi.peers.AppTablePeer;

public class Table extends ControlBase implements AppTable {
    public Table(String id,Application app) {
        super(id,app, AppTable.class, AppTablePeer.class);
    }

    public Table(Application app) {
        this(null,app);
    }

}

