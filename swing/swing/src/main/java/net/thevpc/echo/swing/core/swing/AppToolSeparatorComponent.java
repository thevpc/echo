package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.*;
import net.thevpc.echo.*;

import javax.swing.*;
import java.awt.*;

public class AppToolSeparatorComponent implements AppComponentRenderer {
    @Override
    public Object createGuiComponent(AppComponentRendererContext context) {
        Object parentGuiElement=context.getParentGuiElement();
        AppComponent appComponent=context.getAppComponent();
        Application application=context.getApplication();
        if (appComponent instanceof AppToolComponent) {
            AppToolSeparator tool = (AppToolSeparator) ((AppToolComponent) appComponent).tool();
            Integer height = tool.height().get();
            Integer width = tool.width().get();
            if (parentGuiElement instanceof JToolBar
                    || parentGuiElement instanceof JToolbarGroup
                    || parentGuiElement instanceof JStatusBarGroup
                    || parentGuiElement instanceof JMenuBar
            ) {
                if (width == Integer.MAX_VALUE) {
                    return Box.createHorizontalGlue();
                }
                if (height == Integer.MAX_VALUE) {
                    return Box.createVerticalGlue();
                }
                if (height == 0 && width == 0) {
                    return new JToolBar.Separator(null);
                }
                return new JToolBar.Separator(new Dimension(width, height));
            }
            if (parentGuiElement instanceof JMenu
                    || parentGuiElement instanceof JPopupMenu
                    ) {
                if (width == Integer.MAX_VALUE) {
                    return Box.createHorizontalGlue();
                }
                if (height == Integer.MAX_VALUE) {
                    return Box.createVerticalGlue();
                }
                if (height == 0 && width == 0) {
                    return new JPopupMenu.Separator();
                }
                if (height == 0) {
                    return Box.createHorizontalStrut(width);
                }
                if (width == 0) {
                    return Box.createVerticalStrut(height);
                }
                return Box.createRigidArea(new Dimension(width, height));
            }
        }
        return null;
    }
}
