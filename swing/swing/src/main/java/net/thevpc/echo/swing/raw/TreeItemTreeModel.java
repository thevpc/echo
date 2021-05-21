package net.thevpc.echo.swing.raw;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.PropertyUpdate;
import net.thevpc.common.swing.tree.AbstractTreeModel;
import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.api.components.AppTreeNode;
import net.thevpc.echo.impl.TreeNode;
import net.thevpc.echo.model.AppTreeMutator;
import net.thevpc.echo.swing.peers.SwingTreePeer;

import javax.swing.*;

public class TreeItemTreeModel<T> extends AbstractTreeModel {
    private AppTree<T> tree;
    private JTree jtree;

    public TreeItemTreeModel(AppTree<T> tree,JTree jtree) {
        this.tree=tree;
        this.jtree=jtree;
        tree.root().listeners().addPropagated(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                Object owner = event.property().userObjects().get("owner");
                if(event.property().propertyName().equals("children")
                        && owner instanceof AppTreeNode){
                    AppTreeNode p=(AppTreeNode) owner;
                    if(p!=null){
                        fireTreeStructureChanged(tree,SwingTreePeer.toTreePath(p));
                    }
                }
                //fireTreeStructureChanged(jtree,null);
            }
        });
    }

    @Override
    public Object getRoot() {
        return tree.root().get();
    }

    @Override
    public boolean isLeaf(Object node) {
        return node==null || ((AppTreeNode)node).children().isEmpty();
    }

    @Override
    public Object getChild(Object parent, int index) {
        if(parent==null){
            return getRoot();
        }
        return ((AppTreeNode)parent).children().get(index);
    }

    @Override
    public int getChildCount(Object parent) {
        if(parent==null){
            return 1;
        }
        return ((AppTreeNode)parent).children().size();
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        if(parent==null){
            return child==getRoot()?0:-1;
        }
        return ((AppTreeNode)parent).children().findFirstIndexOf(child);
    }

    @Override
    public Object getParent(Object target) {
        if(target instanceof AppTreeNode){
            return ((AppTreeNode<?>) target).parent().get();
        }
        return null;
    }

    @Override
    protected void insertNodeIntoImpl(Object parent, Object newChild, int index) {
        AppTreeMutator<T> g = tree.mutator().get();
        if(g!=null){
            g.addChild((AppTreeNode<T>) parent,(AppTreeNode<T>) newChild,index);
        }
    }

    @Override
    protected void removeNodeFromParentImpl(Object parent, int index) {
        AppTreeMutator<T> g = tree.mutator().get();
        if(g!=null){
            g.removeChild((AppTreeNode<T>) parent,index);
        }
    }

    @Override
    public Object copyNode(Object node) {
        AppTreeMutator<T> g = tree.mutator().get();
        if(g!=null){
            return g.copy((AppTreeNode<T>) node);
        }
        return null;
    }
}
