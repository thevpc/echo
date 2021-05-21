package net.thevpc.echo;

import net.thevpc.echo.impl.TreeNode;

public interface TreeNodeFactory<T> {
    TreeNode<T> createNode(T x) ;
}
