package net.thevpc.echo.api.components;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableListSelection;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.TreeNodeChildrenFactory;
import net.thevpc.echo.TreeNodeFactory;
import net.thevpc.echo.model.AppTreeMutator;

public interface AppTree<T> extends AppControl {

    WritableValue<AppTreeNode<T>> root();

    WritableListSelection<AppTreeNode<T>> selection();

    WritableBoolean rootVisible();

    Class itemType();

    WritableValue<AppTreeMutator<T>> mutator();

    AppTreeNode<T> findNode(Object[] path);

    WritableValue<AppTreeItemRenderer<T>> itemRenderer();

    AppTreeNode<T> nodeOf(T value);

    AppTreeNode<T> nodeOf(T value, TreeNodeFactory<T> nf, TreeNodeChildrenFactory<T> cf);

    void updateChild(AppTreeNode<T> parent, int index);

    AppTreeNode<T> addChild(AppTreeNode<T> parent, Object child, int index);

    AppTreeNode<T> removeChild(AppTreeNode<T> parent, int childIndex);

    AppTreeNode<T> copy(AppTreeNode<T> node);

}
