package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.*;

import javax.swing.*;
import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;

public class AppToolFolderComponent implements AppComponentRenderer {

    @Override
    public Object createGuiComponent(AppComponentRendererContext context) {
        Object parentGuiElement = context.getParentGuiElement();
        AppComponent appComponent = context.getAppComponent();
        Application application = context.getApplication();
        if (appComponent instanceof AppToolComponent) {
            AppToolComponent b = (AppToolComponent) appComponent;
            AppToolFolder tool = (AppToolFolder) b.tool();
            if (parentGuiElement instanceof JStatusBarGroup) {
                JToolBar m = new JToolBar();
                m.setFloatable(false);
                return m;
            }
            if (parentGuiElement instanceof JToolbarGroup) {
                JToolBar m = new JToolBar();
                m.setFloatable(false);
                return m;
            }
            if (parentGuiElement instanceof JToolBar) {
                JDropDownButton m = new JDropDownButton();
                m.setQuickActionDelay(0);//by default disable quickaction!
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, false);
                return m;
            }
            if (parentGuiElement instanceof JMenu
                    || parentGuiElement instanceof JMenuBar
                    || parentGuiElement instanceof JPopupMenu
                    || parentGuiElement instanceof JDropDownButton
                    || parentGuiElement instanceof JDropDownLabel) {
                JMenu m = new JMenu();
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, true);
                return m;
            }
        }
        return null;
    }
}
