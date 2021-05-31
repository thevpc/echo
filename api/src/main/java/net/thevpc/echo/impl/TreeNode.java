package net.thevpc.echo.impl;

import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.DefaultPropertyListeners;
import net.thevpc.common.props.impl.WritableIndexedNodeImpl;
import net.thevpc.echo.WritableTextStyle;
import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.api.components.AppTreeNode;
import net.thevpc.echo.iconset.WritableImage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TreeNode<T> extends WritableIndexedNodeImpl<T>
        implements AppTreeNode<T> {

    private WritableValue<AppTreeNode<T>> parent;
//    private WritableStr text = new WritableStr("text");

//    private WritableTextStyle textStyle = new WritableTextStyle("textStyle");
//
//    private WritableImage smallIcon;
    private WritableBoolean expanded = Props.of("expanded").booleanOf(false);
    private AppTree<T> tree;

    public TreeNode(T value, AppTree<T> tree) {
        super("children", PropertyType.of(tree.itemType()));
        this.tree = tree;
        // IMPORTANT!!!
        //do not propagate parent events
        parent = Props.of("parent")
                .valueOf(PropertyType.of(AppTreeNode.class, tree.itemType()), null);

//        smallIcon = new WritableImage("smallIcon", tree.app(), tree);
        propagateEvents(expanded);
//        propagateEvents(text, textStyle, smallIcon);

        children().onChange(e -> {
            switch (e.eventType()) {
                case ADD: {
                    TreeNode n = e.newValue();
                    if (n.parent().get() != TreeNode.this) {
                        n.parent().set(TreeNode.this);
                    }
                    break;
                }
                case UPDATE: {
                    TreeNode n = e.newValue();
                    if (n.parent().get() != TreeNode.this) {
                        n.parent().set(TreeNode.this);
                    }
                    break;
                }
                case REMOVE: {
                    TreeNode n = e.oldValue();
                    if (n.parent().get() == TreeNode.this) {
                        n.parent().set(null);
                    }
                    break;
                }
            }
        });
        parent().onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent e) {
                TreeNode o = e.oldValue();
                if (o != null) {
                    if (o.children().contains(TreeNode.this)) {
                        o.children().remove(TreeNode.this);
                    }
                }
                TreeNode n = e.newValue();
                if (n != null) {
                    if (!n.children().contains(TreeNode.this)) {
                        n.children().add(TreeNode.this);
                    }
                }
            }
        });
        set(value);
    }

    public AppTree<T> tree() {
        return tree;
    }

    @Override
    public WritableValue<AppTreeNode<T>> parent() {
        return parent;
    }

//    @Override
//    public WritableStr text() {
//        return text;
//    }
//
//    @Override
//    public WritableTextStyle textStyle() {
//        return textStyle;
//    }
//
//    @Override
//    public WritableImage icon() {
//        return smallIcon;
//    }

    @Override
    public WritableBoolean expanded() {
        return expanded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeNode<?> treeNode = (TreeNode<?>) o;
        T[] p1 = computeObjectPath();
        T[] p2 = (T[]) ((TreeNode) o).computeObjectPath();
        if (p1.length != p2.length) {
            return false;
        }
        for (int i = 0; i < p1.length; i++) {
            if (p1[i] != p2[i]) { //reference check!
                return false;
            }
        }
        return
//                Objects.equals(text, treeNode.text)
//                && Objects.equals(textStyle, treeNode.textStyle)
//                && Objects.equals(smallIcon, treeNode.smallIcon)

                Objects.equals(expanded, treeNode.expanded)
                && Objects.equals(get(), treeNode.get())
                && Objects.equals(children(), treeNode.children());
    }

    @Override
    public int hashCode() {
        // TreeNode is used as key and as such its hashCode should not be mutated
        return System.identityHashCode(this);
//                Objects.hash(
////                text, textStyle, smallIcon,
//                expanded, get(), children());
    }

    public T[] computeObjectPath() {
        List<T> all = new ArrayList<>();
        AppTreeNode<T> t = this;
        while (t != null) {
            all.add(0, t.get());
            t = t.parent().get();
        }
        Class typeClass = propertyType().getArgs()[0].getTypeClass();
        return all.toArray((T[]) Array.newInstance(typeClass, 0));
    }

    public AppTreeNode<T>[] computeNodePath() {
        List<AppTreeNode<T>> all = new ArrayList<>();
        AppTreeNode<T> t = this;
        while (t != null) {
            all.add(0, t);
            t = t.parent().get();
        }
        return all.toArray(new AppTreeNode[0]);
    }

    public void fireContentUpdated(){
        ((DefaultPropertyListeners)events()).firePropertyUpdated(
                new PropertyEvent(
                        this,null,
                        this,this,
                        Path.of(propertyName()),
                        PropertyUpdate.UPDATE,true
                )
        );
    }
}
