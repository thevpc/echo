package net.thevpc.echo;

import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.impl.components.WindowContainerBase;
import net.thevpc.echo.spi.peers.AppDesktopPeer;

import java.util.List;
import java.util.stream.Collectors;

public class Desktop extends WindowContainerBase implements AppDesktop {
    public Desktop(String id,Application app) {
        super(id,app, AppDesktop.class, AppDesktopPeer.class);
    }

    public Desktop(Application app) {
        this(null,app);
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
    public Dimension size() {
        return peer().size();
    }

    @Override
    public void tileDesktop(boolean vertical) {
        List<AppWindow> allframes =
                children.toList().stream().filter(x ->
                        !x.state().is(WindowState.CLOSING)
                                && !x.state().is(WindowState.CLOSED)
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
        Dimension size = peer().size();

        double w = size.getWidth() / cols;
        double h = size.getHeight() / rows;
        int x = 0;
        int y = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
                AppWindow f = allframes.get((i * cols) + j);

                if (!f.state().is(WindowState.CLOSED) && f.state().is(WindowState.ICONIFIED)) {
                    f.state().remove(WindowState.ICONIFIED);
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
            if (appWindow.iconifiable().get()) {
                if (appWindow.state().is(WindowState.ICONIFIED) != iconify) {
                    if (iconify) {
                        appWindow.state().add(WindowState.ICONIFIED);
                    } else {
                        appWindow.state().remove(WindowState.ICONIFIED);
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

