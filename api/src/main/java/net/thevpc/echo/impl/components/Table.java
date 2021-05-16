package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppTable;
import net.thevpc.echo.api.tools.AppToolTable;
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

