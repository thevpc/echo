package net.thevpc.echo;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.WritableListSelectionImpl;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTree;
import net.thevpc.echo.api.components.AppTreeItemRenderer;
import net.thevpc.echo.api.components.AppTreeNode;
import net.thevpc.echo.impl.TreeNode;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.model.AppTreeMutator;
import net.thevpc.echo.spi.peers.AppComponentPeer;

import java.util.List;
import java.util.Objects;

public class TreeBase<T> extends ControlBase implements AppTree<T> {
    private final WritableValue<AppTreeItemRenderer<T>> itemRenderer;
    private Class itemType;
    private WritableValue<AppTreeNode<T>> root;
    private WritableBoolean rootVisible=Props.of("rootVisible").booleanOf(true);
    private WritableValue<AppTreeMutator<T>> morph;
    private WritableListSelection<AppTreeNode<T>> selection;
    private WritableValue<TreeNodeChildrenFactory<T>> childrenFactory;
    private WritableValue<TreeNodeFactory<T>> nodeFactory;

    public TreeBase(String id, Class itemType, Class<? extends AppComponent> componentType,
                    Class<? extends AppComponentPeer> peerType,Application app) {
        super(id,app, componentType, peerType);
        this.itemType =itemType;
        itemRenderer= Props.of("itemRenderer").valueOf(
                PropertyType.of(AppTreeItemRenderer.class,itemType),
                null
        );
        this.itemType= itemType;
        morph=Props.of("morph").valueOf(
                PropertyType.of(AppTreeMutator.class, this.itemType),null
        );
        root=Props.of("root").valueOf(
                PropertyType.of(AppTreeNode.class, this.itemType),null
        );
        nodeFactory=Props.of("nodeFactory").valueOf(
                PropertyType.of(TreeNodeFactory.class, this.itemType),null
        );
        childrenFactory=Props.of("childrenFactory").valueOf(
                PropertyType.of(TreeNodeChildrenFactory.class, this.itemType),null
        );
        selection=new WritableListSelectionImpl<>("selection", PropertyType.of(this.itemType));
        propagateEvents(root,rootVisible,morph);
    }


    @Override
    public WritableValue<AppTreeItemRenderer<T>> itemRenderer() {
        return itemRenderer;
    }

    public WritableValue<TreeNodeChildrenFactory<T>> childrenFactory() {
        return childrenFactory;
    }

    public WritableValue<TreeNodeFactory<T>> nodeFactory() {
        return nodeFactory;
    }

    public TreeNode<T> nodeOf(T value){
        TreeNodeFactory<T> nf = nodeFactory().get();
        TreeNodeChildrenFactory<T> cf = childrenFactory().get();
        return nodeOf(value,nf,cf);
    }

    public TreeNode<T> nodeOf(T value,TreeNodeFactory<T> nf,TreeNodeChildrenFactory<T> cf){
        TreeNode<T> tNode=null;
        if(nf!=null) {
            tNode=nf.createNode(value);
        }
        if(tNode==null){
            tNode=new TreeNode<>(PropertyType.of(itemType()), value, this);
        }
        if(cf!=null){
            List<T> children = cf.getChildren(value);
            if(children!=null) {
                for (T child : children) {
                    tNode.children().add(nodeOf(child, nf, cf));
                }
            }
        }
        return tNode;
    }

    @Override
    public WritableListSelection<AppTreeNode<T>> selection() {
        return selection;
    }

    @Override
    public AppTreeNode<T> findNode(Object[] path) {
        AppTreeNode<T> curr = root().get();
        if(curr==null || path==null){
            return null;
        }
        return (AppTreeNode<T>) curr.findChild(path);
    }

    private AppTreeNode<T> findNode(Object child,ObservableList<AppTreeNode<T>> children) {
        return children.stream().filter(
                (AppTreeNode<T> x)-> Objects.equals(x.get(),child)).findFirst().orElse(null);
    }

    public Class itemType() {
        return itemType;
    }

    public WritableValue<AppTreeNode<T>> root() {
        return root;
    }

    public WritableBoolean rootVisible() {
        return rootVisible;
    }

    public WritableValue<AppTreeMutator<T>> mutator() {
        return morph;
    }

}

