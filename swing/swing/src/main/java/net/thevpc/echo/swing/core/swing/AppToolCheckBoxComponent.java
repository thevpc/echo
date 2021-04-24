package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.*;
import net.thevpc.echo.*;

import javax.swing.*;

public class AppToolCheckBoxComponent implements AppComponentRenderer {

    @Override
    public Object createGuiComponent(AppComponentRendererContext context) {
        Object parentGuiElement=context.getParentGuiElement();
        AppComponent appComponent=context.getAppComponent();
        Application application=context.getApplication();
        if (appComponent instanceof AppToolComponent) {
            AppToolComponent b = (AppToolComponent) appComponent;
            AppToolCheckBox tool = (AppToolCheckBox) b.tool();
            if (parentGuiElement instanceof JToolBar
                    || parentGuiElement instanceof JToolbarGroup
                    || parentGuiElement instanceof JStatusBarGroup) {
                JToggleButton m = new JToggleButton();
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, false);
                return m;
            }
            if (parentGuiElement instanceof JMenuBar) {
                JCheckBox m = new JCheckBox();
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, true);
                return m;
            }
            if (parentGuiElement instanceof JMenu
                    || parentGuiElement instanceof JPopupMenu) {
                JCheckBoxMenuItem m = new JCheckBoxMenuItem();
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, true);
                return m;
            }
        }
        return null;
    }
}
