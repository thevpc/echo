package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.*;

import javax.swing.*;

public class AppToolRadioBoxComponent implements AppComponentRenderer {
    @Override
    public Object createGuiComponent(AppComponentRendererContext context) {
        Object parentGuiElement=context.getParentGuiElement();
        AppComponent appComponent=context.getAppComponent();
        Application application=context.getApplication();
        if (appComponent instanceof AppToolComponent) {
            AppToolComponent b = (AppToolComponent) appComponent;
            AppToolRadioBox tool = (AppToolRadioBox) b.tool();
            if (parentGuiElement instanceof JToolBar
                    || parentGuiElement instanceof JToolbarGroup
                    || parentGuiElement instanceof JStatusBarGroup
                    || parentGuiElement instanceof JMenuBar
            ) {
                JRadioButton m = new JRadioButton();
                SwingApplicationsHelper.prepareAbstractButton(m, b, application,true);
                return m;
            }
            if (parentGuiElement instanceof JMenu
                    || parentGuiElement instanceof JPopupMenu
                    ) {
                JRadioButtonMenuItem m = new JRadioButtonMenuItem();
                SwingApplicationsHelper.prepareAbstractButton(m, b, application,true);
                return m;
            }
        }
        return null;
    }
}
