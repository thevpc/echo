package net.thevpc.echo.swing.core.swing;

import java.io.File;
import java.util.Comparator;
import java.util.stream.Stream;

public class DefaultLazyTreeBackend implements LazyTreeBackend {
    private boolean ignoreHidden;
    private File root;
    private String rootName;

    public DefaultLazyTreeBackend(File root, String rootName, boolean ignoreHidden) {
        this.ignoreHidden = ignoreHidden;
        this.root = root;
        this.rootName = rootName;
    }

    @Override
    public LazyTreeNode getRoot() {
        return new LazyTreeNode(
                rootName == null ? root.getName() : rootName,
                root,
                "/",
                root.isDirectory()
        );
    }

    @Override
    public LazyTreeNode[] getChildren(LazyTreeNode parent) {
        File file = (File) parent.getValue();
        File[] files = file.listFiles();
        if (files == null) {
            return new LazyTreeNode[0];
        }
        return Stream.of(files)
                .filter(x -> !ignoreHidden || !x.isHidden())
                .sorted(Comparator.comparingInt((File x) -> x.isDirectory() ? 0 : 1).thenComparing(File::getPath))
                .map(x -> new LazyTreeNode(
                        x.getName(),
                        x,
                        parent.getPath().endsWith("/") ? (parent.getPath() + x.getName()) : (parent.getPath() + '/' + x.getName()),
                        x.isDirectory()
                )).toArray(LazyTreeNode[]::new);
    }
}
