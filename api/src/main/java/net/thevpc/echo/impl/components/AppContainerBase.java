package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.api.tools.AppToolFolder;

public abstract class AppContainerBase<C extends AppComponent, T extends AppTool>
        extends AppComponentBase
        implements AppContainer<C, T> {

    protected AppContainerChildren<C, T> children;
//    private PropertyContainerSupport containerSupport;

    //    private BindingNode root;
    public AppContainerBase(AppToolFolder folder, Class<? extends C> componentType, Class<? extends T> toolType) {
        super(folder);
        path().set(Path.of("/"));
        children = new AppContainerChildrenImpl<C, T>("children", componentType, toolType, this);
//        containerSupport = new PropertyContainerSupport(path.toString(), this);
    }

    public AppContainerChildren<C, T> children() {
        return children;
    }

    @Override
    public AppToolFolder tool() {
        return (AppToolFolder) super.tool();
    }

    //    @Override
//    public AppControlRenderer renderer() {
//        return null;
//    }
//    @Override
//    public AppPropertyBinding[] getProperties() {
//        return containerSupport.getProperties();
//    }

//    @Override
//    public PropertyListeners listeners() {
//        return containerSupport.listeners();
//    }


    public AppComponent createPreferredComponent(AppTool tool,
                                                 String name, Path absolutePath,
                                                 AppComponentOptions options
    ) {
        return app().toolkit().createComponent(tool, this, name, options);
    }

}
