package net.thevpc.echo;

import net.thevpc.echo.constraints.Layout;

public class GridPane extends Panel{
    public GridPane(Application application) {
        this(null,application);
    }

    public GridPane(String id, Application app) {
        super(id, Layout.GRID, app);
    }
}
