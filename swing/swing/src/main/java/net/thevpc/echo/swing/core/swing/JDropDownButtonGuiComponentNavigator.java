package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.swing.core.GuiComponentNavigator;

import javax.swing.*;
import net.thevpc.common.swing.button.JDropDownButton;

class JDropDownButtonGuiComponentNavigator implements GuiComponentNavigator<JDropDownButton> {

    public JDropDownButtonGuiComponentNavigator() {

    }

    @Override
    public int getItemCount(NodeSupplierContext context) {
        JDropDownButton parent=context.getParentGuiComponent();
        return parent.getItemCount();
    }

    @Override
    public Object getItemAt(int index, NodeSupplierContext context) {
        JDropDownButton parent=context.getParentGuiComponent();
        return parent.getItem(index);
    }

    @Override
    public Object addChildItem(int index, AppToolComponent b, NodeSupplierContext context) {
        JDropDownButton parent=context.getParentGuiComponent();
        JComponent item = context.createGuiComponent(b);
        parent.add(item, index);
        return item;
    }

}
