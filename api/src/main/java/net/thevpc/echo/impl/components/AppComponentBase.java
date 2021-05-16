package net.thevpc.echo.impl.components;

import net.thevpc.common.props.*;
import net.thevpc.echo.*;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.peers.AppComponentPeer;

public class AppComponentBase implements AppComponent {
    private AppTool tool;
    protected  AppComponent parent;
    protected WritableValue<Path> path= Props.of("path").valueOf(Path.class, Path.of());
    private WritableValue<Integer> order= Props.of("order").valueOf(Integer.class,null);
    private DefaultAppComponentConstraints constraints=new DefaultAppComponentConstraints("constraints");
    protected AppComponentPeer peer;
    protected Class<? extends AppComponent> componentType;

    public AppComponentBase(AppTool tool) {
        this.tool = tool;
        this.path.set(Path.of(tool.id()));
    }

    @Override
    public String propertyName() {
        return tool==null?null:tool.propertyName();
    }

    @Override
    public PropertyListeners listeners() {
        return tool==null?null:tool.listeners();
    }

    @Override
    public PropertyType propertyType() {
        return tool==null?null:tool.propertyType();
    }

    @Override
    public UserObjects userObjects() {
        return tool==null?null:tool.userObjects();
    }

    @Override
    public AppComponent setOptions(AppComponentOptions options) {
        if(options!=null) {
            order.set(options.order());
        }
        return this;
    }

    @Override
    public AppComponentConstraints constraints() {
        return constraints;
    }

    @Override
    public Class<? extends AppComponent> componentType() {
        return componentType;
    }

    @Override
    public AppComponent parent() {
        return parent;
    }


    @Override
    public AppComponentPeer peer() {
        return peer(true);
    }

    public WritableValue<Path> path() {
        return path;
    }

    public ObservableValue<Integer> order() {
        return order.readOnly();
    }

    @Override
    public AppComponentPeer peer(boolean prepareShowing) {
        if(!prepareShowing){
            return peer;
        }
        if(peer==null){
            AppComponentPeer p = app().toolkit().createComponentPeer(this);
            p.install(this);
            peer=p;
        }
        return peer;
    }

    protected void prepareUnshowing(){
        if(peer!=null){
            AppComponentPeer p = peer();
            p.uninstall();
            peer=p;
        }
    }
    public AppTool tool() {
        return tool;
    }

    public Application app(){
        return tool.app();
    }

    public void internal_setParent(AppComponent parent) {
        this.parent = parent;
    }
}
