package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.*;

import javax.swing.*;
import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;

public class AppToolToggleComponent implements AppComponentRenderer {

    @Override
    public Object createGuiComponent(AppComponentRendererContext context) {
        Object parentGuiElement=context.getParentGuiElement();
        AppComponent appComponent=context.getAppComponent();
        Application application=context.getApplication();
        if (appComponent instanceof AppToolComponent) {
            AppToolComponent b = (AppToolComponent) appComponent;
            AppToolToggle tool = (AppToolToggle) b.tool();
            if (parentGuiElement instanceof JToolBar
                    || parentGuiElement instanceof JToolbarGroup
                    || parentGuiElement instanceof JStatusBarGroup) {
                AbstractButton m=null;
                switch(tool.buttonType()){
                    case BUTTON:{
                        m=new JToggleButton();
                        break;
                    }
                    case CHECK:{
                        m=new JCheckBox();
                        break;
                    }
                    case RADIO:{
                        m=new JRadioButton();
                        break;
                    }
                    default:{
                        throw new IllegalArgumentException("unsupported button type "+tool.buttonType());
                    }
                }
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, false);
                return m;
            }
            if (parentGuiElement instanceof JMenuBar) {
                JCheckBox m = new JCheckBox();
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, true);
                return m;
            }
            if (parentGuiElement instanceof JMenu
                    || parentGuiElement instanceof JPopupMenu
                    || parentGuiElement instanceof JDropDownButton
                    || parentGuiElement instanceof JDropDownLabel
                    ) {
                AbstractButton m=null;
                switch(tool.buttonType()){
                    case BUTTON:{
                        m=new JToggleButton();
                        break;
                    }
                    case CHECK:{
                        m=new JCheckBoxMenuItem();
                        break;
                    }
                    case RADIO:{
                        m=new JRadioButtonMenuItem();
                        break;
                    }
                    default:{
                        throw new IllegalArgumentException("unsupported button type "+tool.buttonType());
                    }
                }
                SwingApplicationsUtils.prepareAbstractButton(m, b, application, true);
                return m;
            }
        }
        return null;
    }
}
