package net.thevpc.echo.swing.helpers.tree;

import java.util.Stack;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

public class LazyTree {

    public static Object resolveLazyTreeNodeValue(Object t) {
        LazyTreeNode o = resolveLazyTreeNode(t);
        if (o != null) {
            return o.getValue();
        }
        return null;
    }

    public static LazyTreeNode resolveLazyTreeNode(Object t) {
        if (t instanceof DefaultMutableTreeNode) {
            Object uo = ((DefaultMutableTreeNode) t).getUserObject();
            if (uo instanceof FileSystemNode) {
                return ((FileSystemNode) uo).getNodeNameInfo();
            }
        }
        return null;
    }

    public static void reload(JTree t) {
        MyDefaultTreeModel m = (MyDefaultTreeModel) t.getModel();
        m.setRoot(m.helper.file(m.helper.lazyTreeBckend.getRoot(), true));
        m.reload();
    }

    public static void reload(JTree tree, TreePath path) {
        MyDefaultTreeModel m = (MyDefaultTreeModel) tree.getModel();
        Object t = path.getLastPathComponent();
        if (t instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode inode = (DefaultMutableTreeNode) t;
            Object uo = inode.getUserObject();
            if (uo instanceof FileSystemNode) {
                FileSystemNode k = (FileSystemNode) uo;
                LazyTreeNode y = k.getNodeNameInfo();
                k.dirty = true;
                m.helper.reloadNode(inode, true);
                m.reload(inode);
            }
        }
    }

    private DefaultMutableTreeNode reloadNode(DefaultMutableTreeNode y, boolean force) {
        FileSystemNode userObject = (FileSystemNode) y.getUserObject();
        if (force || userObject.isDirty()) {
            y.removeAllChildren();
        }
        return y;
    }

    public static void reload(JTree tree, LazyTreeNode userObject) {
        MyDefaultTreeModel m = (MyDefaultTreeModel) tree.getModel();
        Stack<Object> stack = new Stack<>();
        stack.push(m.getRoot());
        while (!stack.isEmpty()) {
            Object t = stack.pop();
            if (t instanceof DefaultMutableTreeNode) {
                DefaultMutableTreeNode inode = (DefaultMutableTreeNode) t;
                Object uo = inode.getUserObject();
                if (uo instanceof FileSystemNode) {
                    FileSystemNode k = (FileSystemNode) uo;
                    LazyTreeNode y = k.getNodeNameInfo();
                    if (y == userObject) {
                        k.dirty = true;
                        m.reload(inode);
                        return;
                    } else {
                        if (!((FileSystemNode) uo).dirty) {
                            int cc = inode.getChildCount();
                            for (int i = 0; i < cc; i++) {
                                stack.push(inode.getChildAt(i));
                            }
                        }
                    }
                }
            }
        }
    }

    public static JTree of(JTree t, LazyTreeBackend lazyTreeBackend) {
        if (t == null) {
            t = new JTree();
        }
        new FileSystemTreeHelper(lazyTreeBackend).install(t);
        return t;
    }

    public static class FileSystemNode {

        private boolean dirty;
        private LazyTreeNode nodeNameInfo;

        public FileSystemNode(boolean dirty, LazyTreeNode lazyTreeNode) {
            this.dirty = dirty;
            this.nodeNameInfo = lazyTreeNode;
        }

        public LazyTreeNode getNodeNameInfo() {
            return nodeNameInfo;
        }

        public boolean isDirty() {
            return dirty;
        }

        public FileSystemNode setDirty(boolean dirty) {
            this.dirty = dirty;
            return this;
        }

        @Override
        public String toString() {
            return String.valueOf(nodeNameInfo.getName());
        }
    }

    private static class MyDefaultTreeModel extends DefaultTreeModel {

        private FileSystemTreeHelper helper;

        public MyDefaultTreeModel(FileSystemTreeHelper helper, LazyTreeNode rootObject) {
            super(helper.file(rootObject, true));
            this.helper = helper;
        }

        public String getCurrentPath() {
            FileSystemNode userObject = (FileSystemNode) ((DefaultMutableTreeNode) getRoot()).getUserObject();
            return userObject.getNodeNameInfo().getPath();
        }

        public void setRoot(LazyTreeNode rootObject) {
            setRoot(helper.file(rootObject, true));
        }

        public void invalidatePath(String path) {

        }
    }

    public static class FileSystemTreeHelper {

        private LazyTreeBackend lazyTreeBckend;

        public FileSystemTreeHelper(LazyTreeBackend lazyTreeBckend) {
            this.lazyTreeBckend = lazyTreeBckend;
        }

        private DefaultMutableTreeNode file(LazyTreeNode info, boolean load) {
            FileSystemNode file = new FileSystemNode(!load, info);
            DefaultMutableTreeNode n = new DefaultMutableTreeNode(file) {
                @Override
                public boolean isLeaf() {
                    return !info.isFolder();
                }
            };
            n.setAllowsChildren(info.isFolder());
            if (load) {
                reloadNode(n, true);
            }
            return n;
        }

        public DefaultMutableTreeNode reloadNode(DefaultMutableTreeNode y, boolean force) {
            FileSystemNode userObject = (FileSystemNode) y.getUserObject();
            if (force || userObject.isDirty()) {
                y.removeAllChildren();
                LazyTreeNode[] u = lazyTreeBckend.getChildren(userObject.getNodeNameInfo());
                for (LazyTreeNode s : u) {
                    y.add(file(s, false));
                }
                userObject.setDirty(false);
            }
            return y;
        }

        public void install(JTree tree) {
            tree.setModel(new MyDefaultTreeModel(this, lazyTreeBckend.getRoot()));
            tree.addTreeWillExpandListener(new TreeWillExpandListener() {
                @Override
                public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                    reloadNode((DefaultMutableTreeNode) event.getPath().getLastPathComponent(), false);
                }

                @Override
                public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

                }
            });

        }
    }
}
