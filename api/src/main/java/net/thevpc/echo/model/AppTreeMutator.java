package net.thevpc.echo.model;

import net.thevpc.echo.api.components.AppTreeNode;

public interface AppTreeMutator<T> {
    void addChild(AppTreeNode<T> parent, Object child, int index) ;

    void removeChild(AppTreeNode<T> parent, int childIndex) ;

    AppTreeNode<T> copy(AppTreeNode<T> node) ;
}
