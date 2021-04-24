package net.thevpc.echo.swing.core.swing;

import javax.swing.JComponent;

import net.thevpc.echo.AppLayoutStatusBarFactory;
import net.thevpc.echo.AppStatusBar;
import net.thevpc.echo.AppWindow;
import net.thevpc.echo.Application;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;

public class JStatusBarGroupStatusBar extends AppToolContainerImpl implements AppStatusBar, JComponentSupplier {

    private WritableValue<Boolean> visible = Props.of("visible").valueOf(Boolean.class, false);

    public JStatusBarGroupStatusBar(String rootPath, Application application) {
        super(rootPath, new JStatusBarGroup(), application);
        SwingApplicationsUtils.bindVisible((JComponent) rootGuiElement, visible);
    }

    @Override
    public WritableValue<Boolean> visible() {
        return visible;
    }

    public static AppLayoutStatusBarFactory factory() {
        return new AppLayoutStatusBarFactory() {
            @Override
            public AppStatusBar createStatusBar(String path, AppWindow window, Application application) {
                return new JStatusBarGroupStatusBar(path, application);
            }
        };
    }

    public JStatusBarGroup component() {
        return (JStatusBarGroup) super.component();
    }
}
