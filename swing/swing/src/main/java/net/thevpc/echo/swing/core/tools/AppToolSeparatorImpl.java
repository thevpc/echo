package net.thevpc.echo.swing.core.tools;

import net.thevpc.echo.AbstractAppTool;
import net.thevpc.echo.AppToolSeparator;
import net.thevpc.echo.ItemPath;
import net.thevpc.common.props.Props;
import net.thevpc.echo.Application;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.AppTools;

public class AppToolSeparatorImpl extends AbstractAppTool implements AppToolSeparator {

    private final WritableValue<Integer> width = Props.of("width").valueOf(Integer.class, 0);
    private final WritableValue<Integer> height = Props.of("height").valueOf(Integer.class, 0);
    private ItemPath path;

    public AppToolSeparatorImpl(String path, Application app, AppTools tools) {
        super(ItemPath.of(path).toString(), app,tools,false);
        this.path = ItemPath.of(path);
    }

    @Override
    public WritableValue<Integer> width() {
        return width;
    }

    @Override
    public WritableValue<Integer> height() {
        return height;
    }

    @Override
    public ItemPath path() {
        return path;
    }
}
