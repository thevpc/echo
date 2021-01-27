package net.thevpc.echo.swing.core.swing;

public interface LazyTreeBackend {
    LazyTreeNode getRoot();

    LazyTreeNode[] getChildren(LazyTreeNode parent);
}
