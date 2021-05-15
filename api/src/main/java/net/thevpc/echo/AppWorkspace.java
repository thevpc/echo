package net.thevpc.echo;

import net.thevpc.common.props.ObservableMap;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppControl;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.api.components.AppWindow;

public interface AppWorkspace extends AppControl {

    boolean dockingEnabled();

    boolean desktopEnabled();

    void tileDesktop(boolean vertical);

    void iconDesktop(boolean iconify);

    void closeAllDesktop();

    AppWindow addWindow(String id, AppComponent component, AppWindowAnchor anchor);

    ObservableMap<String, AppWindow> windows();

    AppWindow getWindow(String id);

    void removeWindow(String id);
}
