package net.thevpc.echo;

import net.thevpc.echo.constraints.Layout;

/**
 * children must define anchors like this
 * <code>
 *     child.anchor().set(Anchor.CENTER);
 * </code>
 */
public class BorderPane extends Panel{
    public BorderPane(Application application) {
        this(null,application);
    }

    public BorderPane(String id, Application app) {
        super(id, Layout.BORDER, app);
    }
}
