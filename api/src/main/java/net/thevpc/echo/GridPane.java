package net.thevpc.echo;

import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.constraints.ColumnCount;

public class GridPane extends Panel {

    public GridPane(Application app) {
        this(null, -1, app);
    }

    public GridPane(int columns, Application app) {
        this(null, columns, app);
    }

    public GridPane(String id, Application app) {
        this(id, -1, app);
    }

    public GridPane(String id, int columns, Application app) {
        super(id, Layout.GRID, app);
        if (columns > 0) {
            parentConstraints().add(new ColumnCount(columns));
        }
    }
}
