package net.thevpc.echo.impl.components;

import net.thevpc.echo.AppDimension;
import net.thevpc.echo.AppWindowState;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppDesktopPeer;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

import java.util.List;
import java.util.stream.Collectors;

public class Desktop extends WindowContainerBase implements AppDesktop {
    public Desktop(AppContainerModel folder) {
        super(folder, AppContainerModel.class, AppDesktop.class, AppDesktopPeer.class);
    }

    public Desktop(Application app) {
        this(new ContainerModel(app));
    }

    @Override
    public AppDesktopPeer peer() {
        return (AppDesktopPeer) super.peer();
    }

    @Override
    public AppDesktopPeer peer(boolean prepareShowing) {
        return (AppDesktopPeer) super.peer(prepareShowing);
    }

    @Override
    public AppDimension size() {
        return peer().size();
    }

    @Override
    public void tileDesktop(boolean vertical) {
        List<AppWindow> allframes =
                children.toList().stream().filter(x ->
                        !x.model().state().is(AppWindowState.CLOSING)
                                && !x.model().state().is(AppWindowState.CLOSED)
                )
                        .collect(Collectors.toList());
        int count = allframes.size();
        if (count == 0) {
            return;
        }

        // Determine the necessary grid size
        int sqrt = (int) Math.sqrt(count);
        int rows = sqrt;
        int cols = sqrt;
        if (rows * cols < count) {
            cols++;
            if (rows * cols < count) {
                rows++;
            }
        }

        // Define some initial values for size & location.
        AppDimension size = peer().size();

        double w = size.getWidth() / cols;
        double h = size.getHeight() / rows;
        int x = 0;
        int y = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
                AppWindow f = allframes.get((i * cols) + j);

                if (!f.model().state().is(AppWindowState.CLOSED) && f.model().state().is(AppWindowState.ICONIFIED)) {
                    f.model().state().remove(AppWindowState.ICONIFIED);
                }
                f.resize(x, y, w, h);

//                desk.getDesktopManager().beginDraggingFrame(f);
//                desk.getDesktopManager().resizeFrame(f, x, y, w, h);
//                desk.getDesktopManager().endResizingFrame(f);
                x += w;
            }
            y += h; // start the next row
            x = 0;
        }
    }

    @Override
    public void iconDesktop(boolean iconify) {
        for (AppWindow appWindow : children.toList()) {
            if (appWindow.model().iconifiable().get()) {
                if (appWindow.model().state().is(AppWindowState.ICONIFIED) != iconify) {
                    if (iconify) {
                        appWindow.model().state().add(AppWindowState.ICONIFIED);
                    } else {
                        appWindow.model().state().remove(AppWindowState.ICONIFIED);
                    }
                }
            }
        }
    }

    @Override
    public void closeAllDesktop() {
        for (AppWindow appWindow : children.toList()) {
            appWindow.close();
        }
    }
}

