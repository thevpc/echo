package net.thevpc.echo;

import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.spi.peers.AppTreePeer;


public class Tree<T> extends TreeBase<T> implements AppTree<T> {
    public Tree(String id,Class itemType,Application app) {
        super(id, itemType,AppTree.class, AppTreePeer.class,app);
    }

    public Tree(Class componentType,Application app) {
        this(null,componentType,app);
    }
}

