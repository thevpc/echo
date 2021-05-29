package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableListSelection;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.TreeNodeChildrenFactory;
import net.thevpc.echo.TreeNodeFactory;
import net.thevpc.echo.impl.TreeNode;
import net.thevpc.echo.model.AppTreeMutator;

public interface AppTree<T> extends AppControl {

    WritableValue<AppTreeNode<T>> root();

    WritableListSelection<AppTreeNode<T>> selection();

    WritableBoolean rootVisible();

    Class itemType();

    WritableValue<AppTreeMutator<T>> mutator();

    AppTreeNode<T> findNode(Object[] path);

    WritableValue<AppTreeItemRenderer<T>> itemRenderer();

    TreeNode<T> nodeOf(T value);

    TreeNode<T> nodeOf(T value, TreeNodeFactory<T> nf, TreeNodeChildrenFactory<T> cf);

}
