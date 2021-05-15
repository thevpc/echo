package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolNumber;
import net.thevpc.echo.api.tools.AppToolTable;

public class ToolTable extends AppToolBase implements AppToolTable {
    public ToolTable(String id, Application app) {
        super(id, app);
    }

    public ToolTable(Application app) {
        super(app);
    }

}
