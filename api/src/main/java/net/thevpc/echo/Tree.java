package net.thevpc.echo;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.WritableListSelectionImpl;
import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.api.components.AppTreeItemRenderer;
import net.thevpc.echo.api.components.AppTreeNode;
import net.thevpc.echo.impl.TreeNode;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.model.AppTreeMutator;
import net.thevpc.echo.spi.peers.AppTreePeer;

import java.util.List;
import java.util.Objects;

public class Tree<T> extends TreeBase<T> implements AppTree<T> {
    public Tree(String id,Class itemType,Application app) {
        super(id, itemType,AppTree.class, AppTreePeer.class,app);
    }

    public Tree(Class componentType,Application app) {
        this(null,componentType,app);
    }
}

