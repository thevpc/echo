package net.thevpc.echo.swing.core;

import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.swing.core.swing.NodeSupplierContext;

public interface GuiComponentNavigator<T> {
    int getItemCount(NodeSupplierContext context);

    Object getItemAt(int index, NodeSupplierContext context);

    Object addChildItem(int index, AppToolComponent b, NodeSupplierContext context);
}
