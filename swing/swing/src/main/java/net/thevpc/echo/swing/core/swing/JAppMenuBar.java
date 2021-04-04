package net.thevpc.echo.swing.core.swing;


import net.thevpc.echo.AppLayoutMenuBarFactory;
import net.thevpc.echo.AppMenuBar;
import net.thevpc.echo.AppWindow;
import net.thevpc.echo.Application;
import net.thevpc.common.props.Props;

import javax.swing.*;
import net.thevpc.common.props.WritableValue;

public class JAppMenuBar extends AppToolContainerImpl implements AppMenuBar,JMenuBarComponentSupplier {
    private WritableValue<Boolean> visible = Props.of("visible").valueOf(Boolean.class, false);
    public JAppMenuBar(String rootPath, Application application) {
        super(rootPath, new JMenuBar(), application);
        SwingApplicationsHelper.bindVisible((JComponent)rootGuiElement, visible);
    }

    public static AppLayoutMenuBarFactory factory() {
        return new AppLayoutMenuBarFactory() {
            @Override
            public AppMenuBar createMenuBar(String path, AppWindow window, Application application) {
                return new JAppMenuBar(path, application);
            }
        };
    }
    
     @Override
    public WritableValue<Boolean> visible() {
        return visible;
    }

    public JMenuBar component() {
        return (JMenuBar) super.component();
    }
}
