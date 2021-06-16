package net.thevpc.echo;

import net.thevpc.echo.constraints.Layout;

public class VerticalPane extends Panel{
    public VerticalPane(Application application) {
        this(null,application);
    }

    public VerticalPane(String id, Application app) {
        super(id, Layout.VERTICAL, app);
    }
}
