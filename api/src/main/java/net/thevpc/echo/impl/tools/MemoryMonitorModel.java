package net.thevpc.echo.impl.tools;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppMemoryMonitorModel;

public class MemoryMonitorModel extends AppComponentModelBase implements AppMemoryMonitorModel {
    public MemoryMonitorModel(Application app) {
        super(null, app);
        propagateEvents();
    }
}
