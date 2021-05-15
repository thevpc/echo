package net.thevpc.echo.jfx.raw;

import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class FxToolBarGroup extends HBox {
    public FxToolBarGroup() {
    }

    public FxToolBarGroup(double spacing) {
        super(spacing);
    }

    public FxToolBarGroup(Node... children) {
        super(children);
    }

    public FxToolBarGroup(double spacing, Node... children) {
        super(spacing, children);
    }
}
