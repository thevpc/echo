package net.thevpc.echo;

import net.thevpc.echo.constraints.Layout;

public class FlowPane extends Panel{
    public FlowPane(Application application) {
        this(null,application);
    }

    public FlowPane(String id, Application app) {
        super(id, Layout.FLOW, app);
    }
}
