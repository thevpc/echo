package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.tools.*;
import net.thevpc.echo.api.peers.AppFramePeer;
import net.thevpc.echo.impl.tools.FrameModel;

public class Frame extends AppContainerBase<AppComponentModel, AppComponent> implements AppFrame {
    private WritableValue<AppMenuBar> menuBar = Props.of("menuBar").valueOf(AppMenuBar.class, null);
    private WritableValue<AppToolBarGroup> statusBar = Props.of("statusBar").valueOf(AppToolBarGroup.class, null);
    private WritableValue<AppToolBarGroup> toolBar = Props.of("toolBar").valueOf(AppToolBarGroup.class, null);
    private WritableValue<AppComponent> workspace = Props.of("workspace").valueOf(AppComponent.class, null);

    public Frame(Application app) {
        this(new FrameModel(app));
    }

    public Frame(AppFrameModel tool) {
        super(tool,
                AppContainerModel.class, AppFrame.class, AppFramePeer.class,
                AppComponentModel.class, AppComponent.class);
        menuBar().listeners().add(new NameBoundPropertyListener<>("menuBar", children));
        statusBar().listeners().add(new NameBoundPropertyListener<>("statusBar", children));
        toolBar().listeners().add(new NameBoundPropertyListener<>("toolBar", children));
        workspace().listeners().add(new NameBoundPropertyListener<>("workspace", children));
    }

    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        switch (name){
            case "menuBar":{
                return new MenuBar(app());
            }
            case "statusBar":
            case "toolBar":
            {
                return new ToolBarGroup(app());
            }
            case "workspace":{
                return new DockPane(app());
            }
        }
        throw new IllegalArgumentException("unsupported child "+name+" in Frame");
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
    public WritableValue<AppComponent> workspace() {
        return workspace;
    }


    @Override
    public AppFrameModel model() {
        return (AppFrameModel) super.model();
    }

    @Override
    public void centerOnDefaultMonitor() {
        ((AppFramePeer)peer()).centerOnDefaultMonitor();
    }

    @Override
    public void close() {
        this.model().state().add(AppWindowState.CLOSING);
    }

    public WritableBoolean active(){
        return model().active();
    }

    public WritableBoolean closable(){
        return model().closable();
    }

    public WritableBoolean iconifiable(){
        return model().iconifiable();
    }

    public AppWindowStateSetValue state(){
        return model().state();
    }

    public WritableValue<AppWindowDisplayMode> displayMode(){
        return model().displayMode();
    }
}

