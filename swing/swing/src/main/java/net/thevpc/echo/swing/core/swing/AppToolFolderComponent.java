package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.*;
import net.thevpc.echo.*;

import javax.swing.*;

public class AppToolFolderComponent implements AppComponentRenderer {
    @Override
    public Object createGuiComponent(AppComponentRendererContext context) {
        Object parentGuiElement=context.getParentGuiElement();
        AppComponent appComponent=context.getAppComponent();
        Application application=context.getApplication();
        if (appComponent instanceof AppToolComponent) {
            AppToolComponent b = (AppToolComponent) appComponent;
            AppToolFolder tool = (AppToolFolder) b.tool();
            if (parentGuiElement instanceof JStatusBarGroup
            ) {
                JToolBar m = new JToolBar();
                m.setFloatable(false);
                return m;
            }
            if (parentGuiElement instanceof JToolbarGroup
            ) {
                JToolBar m = new JToolBar();
                m.setFloatable(false);
                return m;
            }
            if (parentGuiElement instanceof JToolBar) {
//                JToolBar m = new JToolBar();
//                return m;
            }
            if (parentGuiElement instanceof JMenu 
                    || parentGuiElement instanceof JMenuBar
                    || parentGuiElement instanceof JPopupMenu
                    ) {
                JMenu m = new JMenu();
                SwingApplicationsHelper.prepareAbstractButton(m, b, application,true);
                return m;
            }
        }
        return null;
    }
}
