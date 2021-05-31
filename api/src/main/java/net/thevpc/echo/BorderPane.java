package net.thevpc.echo;

import net.thevpc.echo.constraints.ContainerGrow;
import net.thevpc.echo.constraints.Layout;

public class BorderPane extends Panel{
    public BorderPane(Application application) {
        this(null,application);
    }

    public BorderPane(String id, Application app) {
        super(id, Layout.BORDER, app);
    }
}
