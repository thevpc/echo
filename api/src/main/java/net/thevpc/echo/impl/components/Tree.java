package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.api.tools.AppToolTree;
import net.thevpc.echo.impl.tools.ToolTree;

public class Tree extends AppComponentBase implements AppTree {
    public Tree(AppToolTree tool) {
        super(tool);
    }

    public Tree(Application app) {
        super(new ToolTree(app));
    }

    public AppToolTree tool() {
        return (AppToolTree) super.tool();
    }

}

