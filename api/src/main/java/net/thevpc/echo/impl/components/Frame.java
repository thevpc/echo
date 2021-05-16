package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.tools.*;
import net.thevpc.echo.api.peers.AppFramePeer;
import net.thevpc.echo.impl.tools.ToolFrame;

public class Frame extends AppContainerBase<AppComponent, AppTool> implements AppFrame {
    private WritableValue<AppMenuBar> menuBar = Props.of("menuBar").valueOf(AppMenuBar.class, null);
    private WritableValue<AppToolBarGroup> statusBar = Props.of("statusBar").valueOf(AppToolBarGroup.class, null);
    private WritableValue<AppToolBarGroup> toolBar = Props.of("toolBar").valueOf(AppToolBarGroup.class, null);
    private WritableValue<AppWorkspace> workspace = Props.of("workspace").valueOf(AppWorkspace.class, null);

    public Frame(Application app) {
        this(new ToolFrame(app));
    }

    public Frame(AppToolFrame tool) {
        super(tool, AppComponent.class, AppTool.class);
        menuBar().listeners().add(new NameBoundPropertyListener<>("menuBar", children));
        statusBar().listeners().add(new NameBoundPropertyListener<>("statusBar", children));
        toolBar().listeners().add(new NameBoundPropertyListener<>("toolBar", children));
        workspace().listeners().add(new NameBoundPropertyListener<>("workspace", children));
    }

    @Override
    public AppComponent createPreferredComponent(AppTool tool, String name, Path absolutePath, AppComponentOptions options) {
        switch (name){
            case "menuBar":{
                AppComponentOptions o =
                        DefaultAppComponentOptions.copy(options).componentTypeIfNull(AppMenuBar.class)
                        ;
                return super.createPreferredComponent(tool, name, absolutePath, o);
            }
            case "statusBar":
            case "toolBar":
            {
                AppComponentOptions o =
                        DefaultAppComponentOptions.copy(options).componentTypeIfNull(AppToolBar.class)
                        ;
                return super.createPreferredComponent(tool, name, absolutePath, o);
            }
            case "workspace":{
                AppComponentOptions o =
                        DefaultAppComponentOptions.copy(options).componentTypeIfNull(AppWorkspace.class)
                        ;
                return super.createPreferredComponent(tool, name, absolutePath, o);
            }
        }
        return super.createPreferredComponent(tool, name, absolutePath, options);
    }

    @Override
    public WritableValue<AppMenuBar> menuBar() {
        return menuBar;
    }

    @Override
    public WritableValue<AppToolBarGroup> statusBar() {
        return statusBar;
    }

    @Override
    public WritableValue<AppToolBarGroup> toolBar() {
        return toolBar;
    }

    @Override
    public WritableValue<AppWorkspace> workspace() {
        return workspace;
    }


    @Override
    public AppToolFrame tool() {
        return (AppToolFrame) super.tool();
    }

    @Override
    public void centerOnDefaultMonitor() {
        ((AppFramePeer)peer()).centerOnDefaultMonitor();
    }

    @Override
    public void close() {
        tool().state().add(AppWindowState.CLOSING);
    }

}

