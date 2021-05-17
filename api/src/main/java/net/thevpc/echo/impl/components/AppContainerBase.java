package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.impl.tools.ContainerModel;

/**
 *
 * @param <C> child component type
 * @param <T> child tool type
 */
public abstract class AppContainerBase<T extends AppComponentModel, C extends AppComponent>
        extends AppComponentBase
        implements AppContainer<T, C> {

    protected AppContainerChildren<T, C> children;
    protected Class<?> childComponentType;
    protected Class<?> childModelType;
//    private PropertyContainerSupport containerSupport;

    //    private BindingNode root;
    public AppContainerBase(AppContainerModel folder,
                            Class<? extends AppComponentModel> modelType,
                            Class<? extends AppComponent> componentType,
                            Class<? extends AppComponentPeer> peerType,
                            Class<? extends T> childModelType,
                            Class<? extends C> childComponentType
    ) {
        super(folder,modelType,componentType,peerType);
        this.childComponentType=childComponentType;
        this.childModelType=childModelType;

        path().set(Path.of("/"));
        children = new AppContainerChildrenImpl<T, C>("children", childModelType, childComponentType, this);
//        containerSupport = new PropertyContainerSupport(path.toString(), this);
    }

    public AppContainerChildren<T, C> children() {
        return children;
    }

    @Override
    public AppContainerModel model() {
        return (AppContainerModel) super.model();
    }

    public AppComponent createPreferredChild(String name, Path absolutePath) {
        throw new IllegalArgumentException("unable to resolve default child component " +
                "named "+name+" for " +
                "parent of type "+getClass().getName());
    }
}
