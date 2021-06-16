package net.thevpc.echo;

import net.thevpc.echo.api.components.AppMemoryMonitor;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.spi.peers.AppMemoryMonitorPeer;

public class MemoryMonitor extends ControlBase implements AppMemoryMonitor {
    public MemoryMonitor(String id,Application app) {
        super(id,app, AppMemoryMonitor.class, AppMemoryMonitorPeer.class);
    }
    public MemoryMonitor(Application app) {
        this(null,app);
    }

}

