package net.thevpc.echo.api.components;

public class AppTreeIndexedChild<T> {
    private AppTreeNode<T> child;
    private int index;

    public AppTreeIndexedChild(AppTreeNode<T> child, int index) {
        this.child = child;
        this.index = index;
    }

    public AppTreeNode<T> child() {
        return child;
    }

    public int index() {
        return index;
    }
}
