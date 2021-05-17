package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.api.peers.AppTreePeer;
import net.thevpc.echo.api.tools.AppTreeModel;
import net.thevpc.echo.impl.tools.TreeModel;

public class Tree extends AppControlBase implements AppTree {
    public Tree(AppTreeModel tool) {
        super(tool
                , AppTreeModel.class, AppTree.class, AppTreePeer.class
        );
    }

    public Tree(Application app) {
        this(new TreeModel(app));
    }

    public AppTreeModel model() {
        return (AppTreeModel) super.model();
    }

}

