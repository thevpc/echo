package net.thevpc.echo.api.components;

public interface AppTreeCallBack<T> {
    void nodesWereUpdated(AppTreeNode<T> parent, AppTreeIndexedChild<T>... children);

    void nodesWereInserted(AppTreeNode<T> parent, AppTreeIndexedChild<T>... children);

    void nodesWereDeleted(AppTreeNode<T> parent, AppTreeIndexedChild<T>... children);
}
