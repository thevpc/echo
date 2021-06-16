package net.thevpc.echo.api.components;

import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableIndexedNode;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.WritableTextStyle;
import net.thevpc.echo.iconset.WritableImage;

public interface AppTreeNode<T> extends WritableIndexedNode<T> {
    WritableValue<AppTreeNode<T>> parent();

    WritableBoolean expanded();

    AppTree<T> tree();
}
