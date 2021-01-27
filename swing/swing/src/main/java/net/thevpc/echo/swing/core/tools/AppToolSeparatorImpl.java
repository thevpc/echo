package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.echo.AppToolSeparator;
import net.thevpc.echo.ItemPath;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritablePValue;

public class AppToolSeparatorImpl extends AbstractAppTool implements AppToolSeparator {
    private final WritablePValue<Integer> width = Props.of("width").valueOf(Integer.class, 0);
    private final WritablePValue<Integer> height = Props.of("height").valueOf( Integer.class, 0);
    private ItemPath path;

    public AppToolSeparatorImpl(String path) {
        super(ItemPath.of("menuBar").child(path).toString());
        this.path = ItemPath.of("menuBar").child(path);
    }

    public WritablePValue<Integer> width() {
        return width;
    }

    public WritablePValue<Integer> height() {
        return height;
    }

    @Override
    public ItemPath path() {
        return path;
    }
}
