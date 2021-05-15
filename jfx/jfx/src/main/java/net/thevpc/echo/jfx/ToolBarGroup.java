package net.thevpc.echo.jfx;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class ToolBarGroup extends HBox {
    public ToolBarGroup() {
        HBox.setHgrow(this, Priority.ALWAYS);
    }
}
