package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.AppLayoutToolBarFactory;
import net.thevpc.echo.AppToolBar;
import net.thevpc.echo.AppWindow;
import net.thevpc.echo.Application;
import net.thevpc.common.props.Props;

import javax.swing.*;
import net.thevpc.common.props.WritableValue;

public class JAppToolBarGroup extends AppToolContainerImpl implements AppToolBar, JComponentSupplier {

    private WritableValue<Boolean> visible = Props.of("visible").valueOf(Boolean.class, false);

    public JAppToolBarGroup(String rootPath, Application application) {
        super(rootPath, new JToolbarGroup(), application);
        SwingApplicationsHelper.bindVisible((JComponent) rootGuiElement, visible);
    }

    @Override
    public WritableValue<Boolean> visible() {
        return visible;
    }

    public static AppLayoutToolBarFactory factory() {
        return new AppLayoutToolBarFactory() {
            @Override
            public AppToolBar createToolBar(String path, AppWindow window, Application application) {
                return new JAppToolBarGroup(path, application);
            }
        };
    }

    public JComponent component() {
        return (JComponent) super.component();
    }

}
