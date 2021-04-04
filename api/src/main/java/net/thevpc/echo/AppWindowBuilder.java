package net.thevpc.echo;

import net.thevpc.common.props.WritableValue;

public interface AppWindowBuilder {
    WritableValue<AppLayoutWindowFactory> windowFactory();

    WritableValue<AppLayoutMenuBarFactory> menuBarFactory();

    WritableValue<AppLayoutStatusBarFactory> statusBarFactory();

    WritableValue<AppLayoutToolBarFactory> toolBarFactory();

    WritableValue<AppLayoutWorkspaceFactory> workspaceFactory();

    AppWindow createWindow(String path, Application application);

}
