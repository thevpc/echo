package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppTable;
import net.thevpc.echo.api.tools.AppToolTable;
import net.thevpc.echo.api.tools.AppToolToggle;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.impl.tools.ToolTable;

public class Table extends AppComponentBase implements AppTable {
    public Table(AppToolTable tool) {
        super(tool);
    }

    public Table(Application app) {
        super(new ToolTable(app));
    }

    public AppToolTable tool() {
        return (AppToolTable) super.tool();
    }

}

