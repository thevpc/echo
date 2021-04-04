package net.thevpc.echo.swing.core;

import net.thevpc.common.props.Props;
import net.thevpc.echo.AppWindow;
import net.thevpc.echo.AppWindowBuilder;
import net.thevpc.echo.AppToolBar;
import net.thevpc.echo.Application;
import net.thevpc.echo.AppWorkspace;
import net.thevpc.echo.AppLayoutWorkspaceFactory;
import net.thevpc.echo.AppLayoutToolBarFactory;
import net.thevpc.echo.AppMenuBar;
import net.thevpc.echo.AppLayoutStatusBarFactory;
import net.thevpc.echo.AppLayoutWindowFactory;
import net.thevpc.echo.AppStatusBar;
import net.thevpc.echo.AppLayoutMenuBarFactory;
import net.thevpc.echo.ItemPath;
import net.thevpc.common.props.WritableValue;

public class DefaultAppWindowBuilder implements AppWindowBuilder {
    private final WritableValue<AppLayoutWindowFactory> windowFactory = Props.of("windowFactory").valueOf(AppLayoutWindowFactory.class, null);
    private final WritableValue<AppLayoutMenuBarFactory> menuBarFactory = Props.of("menuBarFactory").valueOf(AppLayoutMenuBarFactory.class, null);
    private final WritableValue<AppLayoutStatusBarFactory> statusBarFactory = Props.of("statusBarFactory").valueOf(AppLayoutStatusBarFactory.class, null);
    private final WritableValue<AppLayoutToolBarFactory> toolBarFactory = Props.of("toolBarFactory").valueOf(AppLayoutToolBarFactory.class, null);
    private final WritableValue<AppLayoutWorkspaceFactory> workspaceFactory = Props.of("workspaceFactory").valueOf(AppLayoutWorkspaceFactory.class, null);

    @Override
    public WritableValue<AppLayoutWindowFactory> windowFactory() {
        return windowFactory;
    }

    @Override
    public WritableValue<AppLayoutMenuBarFactory> menuBarFactory() {
        return menuBarFactory;
    }

    @Override
    public WritableValue<AppLayoutStatusBarFactory> statusBarFactory() {
        return statusBarFactory;
    }

    @Override
    public WritableValue<AppLayoutToolBarFactory> toolBarFactory() {
        return toolBarFactory;
    }

    @Override
    public WritableValue<AppLayoutWorkspaceFactory> workspaceFactory() {
        return workspaceFactory;
    }

    @Override
    public AppWindow createWindow(String path, Application application) {
        AppLayoutWindowFactory wf = windowFactory().get();
        if (wf == null) {
            throw new IllegalArgumentException("missing window factory");
        }
        AppWindow window = wf.createWindow(path, application);
        if (window == null) {
            throw new IllegalArgumentException("invalid window factory. returned null window");
        }

        AppLayoutMenuBarFactory m = menuBarFactory().get();
        if (m != null) {
            AppMenuBar bar = m.createMenuBar(ItemPath.of(path).child("menuBar").toString(), window, application);
            if (bar != null) {
                window.menuBar().set(bar);
            }
        }
        AppLayoutStatusBarFactory s = statusBarFactory().get();
        if (s != null) {
            AppStatusBar bar = s.createStatusBar(ItemPath.of(path).child("statusBar").toString(), window, application);
            if (bar != null) {
                window.statusBar().set(bar);
            }
        }
        AppLayoutToolBarFactory t = toolBarFactory().get();
        if (t != null) {
            AppToolBar bar = t.createToolBar(ItemPath.of(path).child("toolBar").toString(), window, application);
            if (bar != null) {
                window.toolBar().set(bar);
            }
        }
        AppLayoutWorkspaceFactory w = workspaceFactory().get();
        if (w != null) {
            AppWorkspace bar = w.createWorkspace(window);
            if (bar != null) {
                window.workspace().set(bar);
            }
        }
        return window;
    }
}
