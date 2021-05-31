package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.common.props.PropertyUpdate;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppContainerChildren;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppComponentPeer;

/**
 *
 * @param <C> child component type
 */
public abstract class ContainerBase<C extends AppComponent>
        extends ComponentBase
        implements AppContainer<C> {

    protected AppContainerChildren<C> children;
    protected Class<?> childComponentType;

    public ContainerBase(String id, Application app,
                         Class<? extends AppComponent> componentType,
                         Class<? extends AppComponentPeer> peerType,
                         Class<? extends C> childComponentType
    ) {
        this(id, app, componentType, peerType,childComponentType,true);
    }

    public ContainerBase(String id, Application app,
                         Class<? extends AppComponent> componentType,
                         Class<? extends AppComponentPeer> peerType,
                         Class<? extends C> childComponentType
            , boolean initChildren
    ) {
        super(id,app,componentType,peerType);
        this.childComponentType=childComponentType;
        //path().set(Path.of("/"));
        if(initChildren) {
            prepareChildren(new AppContainerChildrenImpl<C>("children", childComponentType, this));
        }
    }

    protected void prepareChildren(AppContainerChildren<C> children){
        this.children=children;
        propagateEvents(children);
        this.children.onChange(e->{
            if(e.eventType()== PropertyUpdate.ADD || e.eventType()==PropertyUpdate.UPDATE){
                AppComponent nv = e.newValue();
                Applications.copyResources(ContainerBase.this,nv);
            }
        });
    }

    public AppContainerChildren<C> children() {
        return children;
    }


    public AppComponent createPreferredChild(String name, Path absolutePath) {
        String errorMessage = "unable to resolve default child component " +
                "named " + name + " for " +
                "parent of type " + getClass().getName();
        throw new IllegalArgumentException(errorMessage);
    }
}
