package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.tree.TreeTransferHandler;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.TreeNode;
import net.thevpc.echo.spi.peers.AppTreePeer;
import net.thevpc.echo.swing.SwingApplicationUtils;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;
import net.thevpc.echo.swing.raw.TreeItemTreeModel;

import javax.swing.*;
import javax.swing.plaf.LabelUI;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SwingTreePeer implements SwingPeer, AppTreePeer {
    private JTree swingComponent;
    private AppTree<?> appTree;
    private TreeItemTreeModel<?> m;

    public SwingTreePeer() {
    }

    public static TreePath toTreePath(AppTreeNode note) {
        List<AppTreeNode> elems = new ArrayList<>();
        while (note != null) {
            elems.add(0, note);
            note = (AppTreeNode) note.parent().get();
        }
        return new TreePath(elems.toArray());
    }
    public static Object[] toTreePath2Arr(AppTreeNode note) {
        List<AppTreeNode> elems = new ArrayList<>();
        while (note != null) {
            elems.add(0, note);
            note = (AppTreeNode) note.parent().get();
        }
//        if(elems.size()>1){
//            elems.remove(0);
//        }
        return elems.toArray();
    }
//    public static Object[] toTreePath2Arr(AppTreeNode note) {
//        List<AppTreeNode> elems = new ArrayList<>();
//        while (note != null) {
//            elems.add(0, note);
//            note = (AppTreeNode) note.parent().get();
//        }
//        if(elems.size()>1){
//            elems.remove(0);
//        }
//        return elems.toArray();
//    }

//    public static TreePath toTreePath2(AppTreeNode note) {
//        List<AppTreeNode> elems = new ArrayList<>();
//        while (note != null) {
//            elems.add(0, note);
//            note = (AppTreeNode) note.parent().get();
//        }
//        if(elems.size()>0){
//            elems.remove(0);
//        }
//        return new TreePath(elems.toArray());
//    }

    public void install(AppComponent component0) {
        appTree = (AppTree<?>) component0;
        swingComponent = new JTree(m = new TreeItemTreeModel<>(appTree));
        SwingPeerHelper.installComponent(appTree, swingComponent);
        m.bindJTree(swingComponent);
        swingComponent.setRootVisible(appTree.rootVisible().get());
        appTree.rootVisible().onChange(e -> {
            swingComponent.setRootVisible(appTree.rootVisible().get());
        });

        swingComponent.setDragEnabled(true);
        swingComponent.setDropMode(DropMode.ON_OR_INSERT);
        swingComponent.setTransferHandler(new TreeTransferHandler(TreeNode.class, m));
        swingComponent.addTreeSelectionListener(e -> {
            TreePath[] spaths = swingComponent.getSelectionPaths();
            spaths = spaths == null ? new TreePath[0] : spaths;
//            List<TreeNode> nodes = Arrays.stream(spaths)
//                    .map(p -> (TreeNode) p.getLastPathComponent())
//                    .collect(Collectors.toList());
            List<AppTreeNode> after_swingSelection = Arrays.stream(spaths)
                    .map(p -> ((AppTreeNode) p.getLastPathComponent()))
                    .collect(Collectors.toList());
            List<? extends AppTreeNode<?>> before_echoSelection = appTree.selection().toList();
            List<?> collBefore = before_echoSelection.stream().map(x -> x.get()).collect(Collectors.toList());
            List<Object> collAfter = after_swingSelection.stream().map(x -> x.get()).collect(Collectors.toList());
            if (!before_echoSelection.equals(after_swingSelection)) {
                System.out.println("SWING-ECHO......................");
                System.out.println("==> BEFORE " + before_echoSelection.size() + " :: " + collBefore);
                System.out.println("==> AFTER " + after_swingSelection.size() + " :: " + collAfter);
                appTree.selection().setCollection((Collection) after_swingSelection);
            }
        });
        appTree.selection().onChange(e -> {
            List<? extends AppTreeNode<?>> after_echoSelection = appTree.selection().toList();
            List<TreePath> echoSelectionPaths = after_echoSelection.stream().map(x -> toTreePath(x)).collect(Collectors.toList());
            TreePath[] swingPaths = swingComponent.getSelectionPaths();
            swingPaths = swingPaths == null ? new TreePath[0] : swingPaths;

            List<AppTreeNode> before_swingSelection = Arrays.stream(swingPaths)
                    .map(p -> ((AppTreeNode) p.getLastPathComponent()))
                    .collect(Collectors.toList());
            if (!before_swingSelection.equals(after_echoSelection)) {
                System.out.println("ECHO-SWING......................");
                System.out.println("==> BEFORE " + before_swingSelection.size() + " :: " + before_swingSelection.stream().map(x -> x.get()).collect(Collectors.toList()));
                System.out.println("==> AFTER " + after_echoSelection.size() + " :: " + after_echoSelection.stream().map(x -> x.get()).collect(Collectors.toList()));
                swingComponent.setSelectionPaths(echoSelectionPaths.toArray(new TreePath[0]));
            }
        });

        swingComponent.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) {
                    int selRow = swingComponent.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = swingComponent.getPathForLocation(e.getX(), e.getY());
                    if (selRow != -1) {
//                        PangaeaNoteExt selectedNote = ((PangaeaNoteExt) selPath.getLastPathComponent());
                        swingComponent.setSelectionPath(selPath);
                    } else {
                        swingComponent.clearSelection();
//                        tree.setSelectionPath(
//                                null
////                                new TreePath(tree.getModel().getRoot())
//                        );
                    }
                    if (swingComponent.isShowing()) {
                        AppContextMenu cm = appTree.contextMenu().get();
                        if (cm != null) {
                            JPopupMenu jpm = (JPopupMenu) cm.peer().toolkitComponent();
                            jpm.show(swingComponent, e.getX(), e.getY());
                        }
                    }
                } else if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
                    int selRow = swingComponent.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = swingComponent.getPathForLocation(e.getX(), e.getY());
                    if (selRow != -1) {
//                        PangaeaNoteExt selectedNote = ((PangaeaNoteExt) selPath.getLastPathComponent());
                        swingComponent.setSelectionPath(selPath);
                    } else {
                        swingComponent.clearSelection();
//                        tree.setSelectionPath(
//                                null
////                                new TreePath(tree.getModel().getRoot())
//                        );
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        // BACK
//        toolkitComponent.setCellRenderer(new MyDefaultTreeCellRenderer(this));
//        toolkitComponent.setModel(getDefaultTreeModel());


//        toolkitComponent.setRowHeight(15);
        swingComponent.setCellRenderer(new MyDefaultTreeCellRenderer(this));
        appTree.locale().onChange(() -> {
            swingComponent.setCellRenderer(new MyDefaultTreeCellRenderer(this));
        });
        appTree.iconSet().onChange(() -> {
            swingComponent.setCellRenderer(new MyDefaultTreeCellRenderer(this));
        });
        appTree.iconConfig().onChange(() -> {
            swingComponent.setCellRenderer(new MyDefaultTreeCellRenderer(this));
        });

    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

    private static class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer {
        SwingTreePeer peer;
        AppColor initialForeground;
        AppColor initialBackground;
        AppFont initialFont;

        public MyDefaultTreeCellRenderer(SwingTreePeer peer) {
            this.peer = peer;
        }

        public void getTreeCellRendererComponent0(MyAppChoiceItemContext cc) {
            super.getTreeCellRendererComponent(cc.tree, cc.text, cc.selected, cc.expanded, cc.leaf, cc.row, cc.focused);
        }

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
            AppTreeItemRenderer<?> r = peer.appTree.itemRenderer().get();
            TreeNode node = (TreeNode) value;
            MyAppChoiceItemContext cc = new MyAppChoiceItemContext(this, tree, leaf,
                    node, sel, hasFocus, expanded, row,
                    (node == null || node.get() == null) ? "" : node.get().toString()
            );
            if (initialFont == null) {
                initialFont = SwingHelpers.fromAwtFont(getFont(), peer.appTree.app());
            } else {
                setFont(SwingHelpers.toAwtFont(initialFont));
            }
            if (initialForeground == null) {
                initialForeground = SwingHelpers.fromAwtColor(getForeground(), peer.appTree.app());
            } else {
                setForeground(SwingHelpers.toAwtColor(initialForeground));
            }
            if (initialBackground == null) {
                initialBackground = SwingHelpers.fromAwtColor(getBackground(), peer.appTree.app());
            } else {
                setBackground(SwingHelpers.toAwtColor(initialBackground));
            }
            if (r != null) {
                r.render(cc);
            } else {
                cc.renderDefaults();
            }
            return this;
        }

        public void reset() {
            initialFont = null;
            initialForeground = null;
            initialBackground = null;
        }

        @Override
        public void setUI(LabelUI ui) {
            reset();
            super.setUI(ui);
        }
    }

    private static class MyAppChoiceItemContext<T> implements AppTreeItemContext<T> {
        private final MyDefaultTreeCellRenderer myDefaultTreeCellRenderer;
        boolean bold = false;
        boolean italic = false;
        boolean underline = false;
        boolean strikeThrough = false;
        private String text;
        private Object value;
        private Object node;
        private boolean selected;
        private boolean focused;
        private boolean expanded;
        private boolean leaf;
        private int row;
        private JTree tree;
        private AppFont font;

        public MyAppChoiceItemContext(MyDefaultTreeCellRenderer myDefaultTreeCellRenderer,
                                      JTree tree, boolean leaf, TreeNode node, boolean selected, boolean focused, boolean expanded, int row,
                                      String text) {
            this.leaf = leaf;
            this.tree = tree;
            this.node = node;
            this.selected = selected;
            this.focused = focused;
            this.myDefaultTreeCellRenderer = myDefaultTreeCellRenderer;
            this.expanded = expanded;
            this.row = row;
            this.value = node == null ? null : node.get();
            this.text = text;
            this.font = SwingHelpers.fromAwtFont(myDefaultTreeCellRenderer.getFont(), getTree().app());
        }

        @Override
        public Application getApplication() {
            return myDefaultTreeCellRenderer.peer.appTree.app();
        }

        @Override
        public boolean isExpanded() {
            return expanded;
        }

        @Override
        public boolean isLeaf() {
            return leaf;
        }

        @Override
        public AppTree<T> getTree() {
            return (AppTree<T>) myDefaultTreeCellRenderer.peer.appTree;
        }

        @Override
        public T getValue() {
            return (T) value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        @Override
        public AppTreeNode<T> getNode() {
            return (AppTreeNode<T>) node;
        }

        @Override
        public void setText(String text) {
            this.text = text;
            myDefaultTreeCellRenderer.setText(text);
        }

        @Override
        public void setOpaque(boolean opaque) {
            myDefaultTreeCellRenderer.setOpaque(opaque);
        }

        @Override
        public void setTextColor(AppColor color) {
            myDefaultTreeCellRenderer.setForeground(
                    color == null ? null : (Color) color.peer().toolkitColor()
            );
        }

        @Override
        public void setTextFont(AppFont font) {
            if (font != null) {
                this.font = font;
            } else {
                this.font = myDefaultTreeCellRenderer.initialFont;
            }
            SwingApplicationUtils.setComponentFont(myDefaultTreeCellRenderer,
                    font, null, null, underline, strikeThrough);
        }

        @Override
        public void setTextUnderline(boolean underline) {
            this.underline = underline;
            SwingApplicationUtils.setComponentFont(myDefaultTreeCellRenderer,
                    font, null, null, underline, strikeThrough);
        }

        @Override
        public void setTextStrikeThrough(boolean strikeThrough) {
            this.strikeThrough = strikeThrough;
            SwingApplicationUtils.setComponentFont(myDefaultTreeCellRenderer,
                    font, null, null, underline, strikeThrough);
        }

        @Override
        public void setTextStrokeSize(int size) {
            SwingApplicationUtils.setComponentTextStrokeSize(myDefaultTreeCellRenderer, size);
        }

        @Override
        public void setTextAlign(Anchor align) {
            SwingApplicationUtils.setLabelTextAlign(myDefaultTreeCellRenderer, align);
        }

        @Override
        public void setIcon(AppImage icon) {
            myDefaultTreeCellRenderer.setIcon(
                    SwingHelpers.toAwtIcon(icon)
            );
        }

        @Override
        public boolean isSelected() {
            return selected;
        }

        @Override
        public boolean isFocused() {
            return focused;
        }

        @Override
        public AppFont getFont() {
            return SwingHelpers.fromAwtFont(myDefaultTreeCellRenderer.getFont(), getTree().app());
        }

        @Override
        public AppColor getColor() {

            Color f = myDefaultTreeCellRenderer.getForeground();
            return f == null ? null : new net.thevpc.echo.Color(f.getRGB(), true, getTree().app());
        }

        @Override
        public AppColor getBackgroundColor() {
            return null;
        }

        @Override
        public void setBackgroundColor(AppColor c) {
            if (c == null) {
                c = net.thevpc.echo.Color.WHITE(getApplication());
            }
            myDefaultTreeCellRenderer.setBackground(
                    c == null ? null : (Color) c.peer().toolkitColor()
            );
        }

        @Override
        public void renderDefaults() {
            myDefaultTreeCellRenderer.getTreeCellRendererComponent0(this);
        }

        @Override
        public boolean isOpaque() {
            return myDefaultTreeCellRenderer.isOpaque();
        }

        @Override
        public void setBackgroundNonSelectionColor(AppColor c) {
            if (c == null) {
                c = net.thevpc.echo.Color.BLACK(getApplication());
            }
            myDefaultTreeCellRenderer.setBackgroundNonSelectionColor(
                    c == null ? null : new Color(c.rgba())
            );
        }

        @Override
        public void setBackgroundSelectionColor(AppColor c) {
            if (c == null) {
                c = net.thevpc.echo.Color.WHITE(getApplication());
            }
            myDefaultTreeCellRenderer.setBackgroundSelectionColor(
                    c == null ? null : new Color(c.rgba())
            );
        }

        @Override
        public void setTextNonSelectionColor(AppColor c) {
            if (c == null) {
                c = net.thevpc.echo.Color.BLACK(getApplication());
            }
            myDefaultTreeCellRenderer.setTextNonSelectionColor(
                    c == null ? null : new Color(c.rgba())
            );
        }

        @Override
        public void setTextSelectionColor(AppColor c) {
            if (c == null) {
                c = net.thevpc.echo.Color.BLACK(getApplication());
            }
            myDefaultTreeCellRenderer.setTextSelectionColor(
                    c == null ? null : new Color(c.rgba())
            );
        }
    }
}
