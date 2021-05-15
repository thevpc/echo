package net.thevpc.echo.swing.helpers.tree;

public interface LazyTreeBackend {
    LazyTreeNode getRoot();

    LazyTreeNode[] getChildren(LazyTreeNode parent);
}
