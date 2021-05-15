package net.thevpc.echo.swing.helpers.tree;

public class LazyTreeNode {
    private String path;
    private String name;
    private Object value;
    private boolean folder;

    public LazyTreeNode(String name, Object value, String path, boolean folder) {
        this.name = name;
        this.value = value;
        this.folder = folder;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public boolean isFolder() {
        return folder;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
