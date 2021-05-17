package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppMemoryMonitor;
import net.thevpc.echo.api.peers.AppMemoryMonitorPeer;
import net.thevpc.echo.api.tools.AppMemoryMonitorModel;
import net.thevpc.echo.impl.tools.MemoryMonitorModel;

public class MemoryMonitor extends AppComponentBase implements AppMemoryMonitor {
    public MemoryMonitor(AppMemoryMonitorModel tool) {
        super(tool,
                AppMemoryMonitorModel.class, AppMemoryMonitor.class, AppMemoryMonitorPeer.class
        );
    }
    public MemoryMonitor(Application app) {
        this(new MemoryMonitorModel(app));
    }

}

