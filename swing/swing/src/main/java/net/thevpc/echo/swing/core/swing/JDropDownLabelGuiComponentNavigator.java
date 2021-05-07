package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.swing.core.GuiComponentNavigator;

import javax.swing.*;
import net.thevpc.common.swing.label.JDropDownLabel;

class JDropDownLabelGuiComponentNavigator implements GuiComponentNavigator<JDropDownLabel> {

    public JDropDownLabelGuiComponentNavigator() {

    }

    @Override
    public int getItemCount(NodeSupplierContext context) {
        JDropDownLabel parent=context.getParentGuiComponent();
        return parent.getItemCount();
    }

    @Override
    public Object getItemAt(int index, NodeSupplierContext context) {
        JDropDownLabel parent=context.getParentGuiComponent();
        return parent.getItem(index);
    }

    @Override
    public Object addChildItem(int index, AppToolComponent b, NodeSupplierContext context) {
        JDropDownLabel parent=context.getParentGuiComponent();
        JComponent item = context.createGuiComponent(b);
        parent.add(item, index);
        return item;
    }

}
