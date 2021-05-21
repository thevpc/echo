package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppContainerChildren;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.components.AppContainerOne;
import net.thevpc.echo.spi.peers.AppComponentPeer;

public class ContainerOneBase<C extends AppComponent>
        extends ContainerBase<C> implements AppContainerOne<C> {
    public ContainerOneBase(String id, Application app, String childName, boolean multi,
                            Class<? extends AppComponent> componentType,
                            Class<? extends AppComponentPeer> peerType,
                            Class<? extends C> childComponentType) {
        super(id,app, componentType, peerType, childComponentType,false);
        children = new AppContainerChildrenOne<C>(childName,
                multi,
                "children", childComponentType, this);
    }

    @Override
    public AppContainerChild<C> child() {
        return (AppContainerChild<C>) children();
    }

    @Override
    public AppContainerChildren<C> children() {
        return super.children();
    }

    protected static class AppContainerChildrenOne<C extends AppComponent>
            extends AppContainerChildrenImpl<C> implements AppContainerChild<C> {
        private String childName;
        /**
         * multi if we have side components
         * think of corners of scrollbar. the main is "content" but still we have others!
         */
        private boolean multi;

        public AppContainerChildrenOne(String childName, boolean multi,String name, Class<? extends C> componentType, AppContainer base) {
            super(name, componentType, base);
            this.childName = childName;
            this.multi = multi;
        }

        public void set(C value) {
            set(value,childName);
        }

        public C get() {
            return get(childName);
        }

        protected boolean addImpl(int index, C child) {
            if(!multi) {
                if (index != 0) {
                    throw new IllegalArgumentException("unsupported index " + index + " <> 0");
                }
            }
            return super.addImpl(index, child);
        }

        protected C removeAtImpl(int index) {
            if(!multi) {
                if (index != 0) {
                    return null;
                }
            }
            return super.removeAtImpl(index);
        }

        public C add(C component, String name) {
            if(!multi) {
                if (!childName.equals(name)) {
                    throw new IllegalArgumentException("unsupported child " + name + ". Only " + childName + " exists");
                }
            }
            return super.add(component,name);
        }

        public C add(int index, C component, String name) {
            if(!multi) {
                if (index != 0) {
                    throw new IllegalArgumentException("unsupported index " + index + " <> 0");
                }
                if (!childName.equals(name)) {
                    throw new IllegalArgumentException("unsupported child " + name + ". Only " + childName + " exists");
                }
            }
            return super.add(component,name);
        }
    }
}
