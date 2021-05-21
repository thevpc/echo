package net.thevpc.echo;

import net.thevpc.echo.impl.TreeNode;

import java.util.List;

public interface TreeNodeChildrenFactory<T> {
    List<T> getChildren(T parent) ;
}
