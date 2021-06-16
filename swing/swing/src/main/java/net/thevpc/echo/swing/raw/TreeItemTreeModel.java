package net.thevpc.echo.swing.raw;

import net.thevpc.common.swing.tree.AbstractTreeModel;
import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.api.components.AppTreeCallBack;
import net.thevpc.echo.api.components.AppTreeIndexedChild;
import net.thevpc.echo.api.components.AppTreeNode;
import net.thevpc.echo.model.AppTreeMutator;

import javax.swing.*;
import java.util.Arrays;

public class TreeItemTreeModel<T> extends AbstractTreeModel {
    private AppTree<T> tree;
    private JTree jtree;
    private AppTreeCallBack<T> callback=new AppTreeCallBack<T>() {
        @Override
        public void nodesWereUpdated(AppTreeNode<T> parent, AppTreeIndexedChild<T>... children) {
            TreeItemTreeModel<T> t=TreeItemTreeModel.this;
            t.nodesChanged(parent, Arrays.stream(children).mapToInt(x->x.index()).toArray());
        }
        @Override
        public void nodesWereInserted(AppTreeNode<T> parent, AppTreeIndexedChild<T>... children) {
            TreeItemTreeModel<T> t=TreeItemTreeModel.this;
            t.nodesWereInserted(parent, Arrays.stream(children).mapToInt(x->x.index()).toArray());
        }

        @Override
        public void nodesWereDeleted(AppTreeNode<T> parent, AppTreeIndexedChild<T>... children) {
            TreeItemTreeModel<T> t=TreeItemTreeModel.this;
            t.nodesWereRemoved(parent,
                    Arrays.stream(children).mapToInt(x->x.index()).toArray(),
                    Arrays.stream(children).map(x->x.child()).toArray()
            );
        }
    };

    public <T> AppTreeCallBack<T> callback() {
        return (AppTreeCallBack<T>) callback;
    }

    public TreeItemTreeModel(AppTree<T> tree0) {
        this.tree=tree0;
    }

    public void bindJTree(JTree jtree0){
        this.jtree=jtree0;
        tree.root().events().addPropagated(event -> {
            if(event.immediate()){
                fireTreeStructureChanged(jtree, null);
            }else {
                AppTreeNode node = null;
                if(event.property() instanceof AppTreeNode){
                    node=event.property();
                }
                if(node==null){
                    Object owner2 = event.property().userObjects().get("owner");
                    if(event.property().propertyName().equals("children")
                            && owner2 instanceof AppTreeNode) {
                        node = (AppTreeNode) owner2;
                    }
                }
                if (node!=null) {
                    switch (event.eventType()){
                        case ADD:{
//                            fireTreeNodesInserted(jtree, SwingTreePeer.toTreePath(node).getPath(),
//                                    new int[]{event.index()},
//                                    new Object[]{event.newValue()}
//                            );
                            //jtree.updateUI();
//                            fireTreeStructureChanged(jtree,new Object[]{getRoot()},null,null);
                            break;
                        }
                        case UPDATE:{
//                            fireTreeNodesChanged(jtree, SwingTreePeer.toTreePath2Arr(p),
//                                    new int[]{event.index()},
//                                    new Object[]{event.newValue()}
//                            );
//                            jtree.updateUI();
                            //fireTreeStructureChanged(jtree,new Object[]{getRoot()},null,null);
                            break;
                        }
                        case REMOVE:{
//                            Object p = node.parent().get();
//                            if(p!=null){
//                                fireTreeStructureChanged(jtree,
//                                        SwingTreePeer.toTreePath2Arr((AppTreeNode) p)
//                                        ,null,null);
//                            }
//                            fireTreeNodesRemoved(jtree, SwingTreePeer.toTreePath2Arr(node),
//                                    new int[]{event.index()},
//                                    new Object[]{event.oldValue()}
//                                    );
                            //jtree.updateUI();
//                            fireTreeStructureChanged(jtree,new Object[]{getRoot()},null,null);
                            break;
                        }
                        case REFRESH:{
                            //jtree.updateUI();
                            nodeChanged(node);
                            break;
                        }
                    }
                }
            }
            //fireTreeStructureChanged(jtree,null);
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
            g.addChild((AppTreeNode<T>) parent,(AppTreeNode<T>) newChild,index, callback);
        }
    }

    @Override
    protected void removeNodeFromParentImpl(Object parent, int index) {
        AppTreeMutator<T> g = tree.mutator().get();
        if(g!=null){
            g.removeChild((AppTreeNode<T>) parent,index, callback);
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
