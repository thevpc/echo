package net.thevpc.echo.impl.components;

import net.thevpc.common.props.Path;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.Separator;
import net.thevpc.echo.api.AppContainerChildren;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppContainerChildrenImpl<
        C extends AppComponent> extends WritableListImpl<C>
        implements AppContainerChildren<C> {
    private AppContainer base;
    private Class componentType;

    public AppContainerChildrenImpl(String name, Class<? extends C> componentType, AppContainer base) {
        super(name, PropertyType.of(componentType));
        this.base = base;
        this.componentType = componentType;
        base.events().addDelegate(this,()-> Path.of(base.propertyName()));
    }

    public Class getComponentType() {
        return componentType;
    }


    @Override
    protected boolean addImpl(int index, C child) {
        base.app().toolkit().runUIAndWait(()-> {
            if (!child.path().get().parent().startsWith(base.path().get())) {
                String name = null;
                if (child.path().get() == null || child.path().get().isEmpty()) {
                    name = child.id();
                    if (name == null) {
                        name = UUID.randomUUID().toString();
                    }
                } else {
                    name = child.path().get().name();
                }
                child.path().set(base.path().get().append(name));
                //throw new IllegalArgumentException("cannot add " + child.path().get() + " to " + base.path().get());
            }
            int size0=size();
            super.addImpl(index, child);

            base.peer();
            ComponentBase cc = (ComponentBase) child;
            cc.parent=base;
            child.peer();
            base.peer().addChild(child, index);
        });
        return true;
    }

    @Override
    protected C removeAtImpl(int index) {
        C c = super.removeAtImpl(index);
        ComponentBase cc = (ComponentBase) c;
        base.app().toolkit().runUIAndWait(()-> {
            cc.prepareUnshowing();
            base.peer().removeChild(cc, index);
        });
        return c;
    }

    public int indexOf(String pathName){
        for (int i = 0; i < size(); i++) {
            AppComponent child = (AppComponent) base.children().get(i);
            String name = child.path().get().name();
            if (name!=null && name.equals(pathName)) {
                return i;
            }
        }
        return -1;
    }

//    @Override
//    public C get(String id) {
//        return stream().filter(x->x.id().equals(id)).findFirst().orElse(null);
//    }
//
    public C get(String pathName) {
        int old = indexOf(pathName);
        return old<0?null:get(old);
    }
    public void set(C component,String name) {
        int old = indexOf(name);
        if(old>=0){
            if(component==null){
                removeAt(old);
            }else{
                component.userObjects().put("preferredName",name);
                set(old,component);
            }
        }else{
            if(component!=null) {
                add(component, name);
            }
        }
    }

    public AppComponent get(Path relativePath) {
        AppContainer parentContainer = base;
        if (relativePath.isEmpty()) {
            return base;
        } else if (relativePath.size() == 1) {
            for (int i = 0; i < size(); i++) {
                AppComponent child = (AppComponent) parentContainer.children().get(i);
                if (child.path().get().name().equals(relativePath.get(0))) {
                    return child;
                }
            }
            return null;
        } else {
            AppComponent n = get(Path.of(relativePath.first()));
            if (n instanceof AppContainer) {
                return ((AppContainer) n).children().get(relativePath.skipFirst());
            }
            return null;
        }
    }

    public AppContainer ensureParentExists(Path relativePath) {
        if (relativePath == null) {
            throw new NullPointerException();
        }
        AppContainer parentContainer = base;
        Path this_path = base.path().get();

        AppContainer goodParent = null;
        if (relativePath.isEmpty()) {
            //will generate a random name
            relativePath = Path.of(UUID.randomUUID().toString());
        } else {
            Path curr=this_path;
            while (!relativePath.isEmpty()) {
                String first = relativePath.first();
                curr=curr.append(first);
                goodParent = null;
                for (Object ochild : parentContainer.children()) {
                    AppComponent child = (AppComponent) ochild;
                    if (child.path().get().name()!=null && child.path().get().name().equals(first)) {
                        goodParent = (AppContainer) child;
                        break;
                    }
                }
                if (goodParent == null) {
                    ContainerBase cb=(ContainerBase) parentContainer;
                    AppComponent cc = cb.createPreferredChild(first, curr);
                    parentContainer = (AppContainer) parentContainer.children().add(cc, first);
                } else {
                    parentContainer = goodParent;
                }
                relativePath = relativePath.skipFirst();
            }
        }
        return parentContainer;
    }

    public AppComponent add(AppComponent component, Path relativePath) {

        String theName = relativePath.name();
        if(relativePath.last().equals("*") || relativePath.isEmpty()){
            relativePath=relativePath.parent().append(component.id());
            theName=component.id();
        }
        AppContainer parentContainer = ensureParentExists(relativePath.parent());
        return parentContainer.children().add(component, theName);
    }

//    public AppComponent add(AppComponentModel tool, Path relativePath, AppComponentOptions options) {
//        if(relativePath.last().equals("*") || relativePath.isEmpty()){
//            relativePath=relativePath.parent().append(tool.id());
//        }
//        AppContainer parentContainer = ensureParentExists(relativePath.parent());
//        String theName = relativePath.name();
//
//        int u = parentContainer.children().size();
//        int index = -1;
//        Integer order = options == null ? null : options.order();
//        for (int i = 0; i < u; i++) {
//            AppComponent child = (AppComponent) parentContainer.children().get(i);
//            Integer o = child.order().get();
//            if (order == null || o == null || o <= order) {
//                index = i;
//            }
//        }
//        if (index < 0) {
//            index = parentContainer.children().size();
//        }
//        return parentContainer.children().add(index, tool, theName, options);
//    }

//    @Override
//    public C add(T tool, String name, AppComponentOptions options) {
//        if (name.indexOf('/') > 0) {
//            throw new IllegalArgumentException("use add(AppComponentModel,Path) instead");
//        }
//        C component = (C) ((ContainerBase)(base)).createPreferredComponent(tool,
//                name, base.path().get().append(name),
//                options
//        );
//        add(component);
//        return component;
//    }

    public C add(C component, String name) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use add(AppComponentModel,Path) instead");
        }

        int u = size();
        int index = -1;
        Integer order = component.order().get();
        for (int i = 0; i < u; i++) {
            AppComponent child = get(i);
            Integer o = child.order().get();
            if (order == null || o == null || o <= order) {
                index = i+1;
            }
        }
        if (index < 0) {
            index = u;
        }
        component.userObjects().put("preferredName",name);
        add(index,component);
        return component;
    }

    @Override
    protected void firePropertyUpdated(PropertyEvent e) {
        AppComponent nv = e.newValue();
        if(nv!=null) {
            String preferredName =(String) nv.userObjects().get("preferredName");
            if(preferredName!=null) {
                nv.path().set(base.path().get().append(preferredName));
            }
        }
        super.firePropertyUpdated(e);
    }
    //    @Override
//    public C add(int index, T tool, String name, AppComponentOptions options) {
//        if (name.indexOf('/') > 0) {
//            throw new IllegalArgumentException("use add(AppComponentModel,Path) instead");
//        }
//        C component = (C) ((ContainerBase)(base)).createPreferredComponent(tool,
//                name, base.path().get().append(name),
//                options
//        );
//        add(index, component, name);
//        return component;
//    }

    @Override
    public C add(int index, C component, String name) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use add(AppComponentModel,Path) instead");
        }
        component.path().set(base.path().get().append(name));
        add(index, component);
        return component;
    }

    public AppComponent remove(String name) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use remove(Path) instead");
        }
        for (int i = 0; i < size(); i++) {
            AppComponent child = get(i);
            String ll = child.path().get().last();
            if (ll!=null && ll.equals(name)) {
                return removeAt(i);
            }
        }
        return null;
    }

    public AppComponent remove(Path relativePath) {
        if (relativePath.isEmpty()) {
            return null;
        }
        if (relativePath.size() == 1) {
            return remove(relativePath.name());
        }
        AppComponent p = get(relativePath.parent());
        if (p instanceof AppContainer) {
            AppContainer cc = (AppContainer) p;
            return cc.children().remove(relativePath.name());
        }
        return null;
    }

    @Override
    public AppComponent addFolder(Path relativePath) {
        return ensureParentExists(relativePath);
    }

//    public AppComponent add(AppComponentModel tool, Path relativePath) {
//        return add(tool, relativePath, null);
//    }

    public List<AppComponent> addMulti(AppComponent component, Path relativePath, Path... all) {
        List<AppComponent> a = new ArrayList<>();
        add(component, relativePath);
        for (Path path : all) {
            AppComponent copy = component.copy(true);
            add(copy, path);
            a.add(copy);
        }
        return a;
    }

//    public List<AppComponent> addMulti(AppComponentModel tool, Path relativePath, Path... all) {
//        List<AppComponent> a = new ArrayList<>();
//        a.add(add(tool, relativePath, null));
//        for (Path path : all) {
//            a.add(add(tool, path, null));
//        }
//        return a;
//    }

    @Override
    public AppComponent addSeparator(Path relativePath) {
        return add(new Separator(base.app()), relativePath);
    }

    @Override
    public AppComponent addSeparator() {
        return addSeparator(Path.of("*"));
    }
}
