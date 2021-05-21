package net.thevpc.echo;

import net.thevpc.echo.constraints.Layout;

public class HorizontalPane extends Panel{
    public HorizontalPane(Application application) {
        this(null,application);
    }

    public HorizontalPane(String id, Application app) {
        super(id, Layout.HORIZONTAL, app);
    }
}
