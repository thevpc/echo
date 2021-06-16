package net.thevpc.echo.model;

import net.thevpc.echo.api.components.AppTreeCallBack;
import net.thevpc.echo.api.components.AppTreeNode;

public interface AppTreeMutator<T> {
    void updateChild(AppTreeNode<T> parent, int index, AppTreeCallBack<T> callback) ;
    AppTreeNode<T> addChild(AppTreeNode<T> parent, Object child, int index, AppTreeCallBack<T> callback) ;

    AppTreeNode<T> removeChild(AppTreeNode<T> parent, int childIndex, AppTreeCallBack<T> callBack) ;

    AppTreeNode<T> copy(AppTreeNode<T> node) ;
}
