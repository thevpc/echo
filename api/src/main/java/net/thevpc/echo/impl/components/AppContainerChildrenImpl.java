package net.thevpc.echo.impl.components;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.tools.AppComponentModel;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppContainerChildrenImpl<T extends AppComponentModel, C extends AppComponent> extends WritableListImpl<C>
        implements AppContainerChildren<T, C> {
    private AppContainer base;
    private Class componentType;
    private Class modelType;

    public AppContainerChildrenImpl(String name, Class<? extends T> modelType, Class<? extends C> componentType, AppContainer base) {
        super(name, PropertyType.of(componentType));
        this.base = base;
        this.componentType = componentType;
        this.modelType = modelType;
    }

    public Class getComponentType() {
        return componentType;
    }

    public Class getModelType() {
        return modelType;
    }

    @Override
    protected boolean addImpl(int index, C child) {
        if (!child.path().get().parent().startsWith(base.path().get())) {
            String name = null;
            if (child.path().get() == null || child.path().get().isEmpty()) {
                name = child.model().id();
                if (name == null) {
                    name = UUID.randomUUID().toString();
                }
            } else {
                name = child.path().get().name();
            }
            child.path().set(base.path().get().append(name));
            //throw new IllegalArgumentException("cannot add " + child.path().get() + " to " + base.path().get());
        }
        super.addImpl(index, child);
        base.app().toolkit().runUI(()-> {

            base.peer();
            if (child instanceof AppComponentBase) {
                AppComponentBase cc = (AppComponentBase) child;
                cc.internal_setParent(base);
            }
            child.peer();
            base.peer().addChild(child, index);
        });
        return true;
    }

    @Override
    protected C removeImpl(int index) {
        C c = super.removeImpl(index);
        AppComponentBase cc = (AppComponentBase) c;
        base.app().toolkit().runUI(()-> {
            cc.prepareUnshowing();
            base.peer().removeChild(cc, index);
        });
        return c;
    }

    @Override
    public C get(String id) {
        return stream().filter(x->x.model().id().equals(id)).findFirst().orElse(null);
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
                    if (child.path().get().name().equals(first)) {
                        goodParent = (AppContainer) child;
                        break;
                    }
                }
                if (goodParent == null) {
                    AppContainerBase cb=(AppContainerBase) parentContainer;
                    AppComponent cc = cb.createPreferredChild(first, curr);
                    cc.model().title().set(Str.i18n(curr.toString()));
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
            relativePath=relativePath.parent().append(component.model().id());
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
//        C component = (C) ((AppContainerBase)(base)).createPreferredComponent(tool,
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
        component.path().set(base.path().get().append(name));

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
//        System.out.println("add "+name+" (order "+order+") at index "+index+"/"+u);
        add(index,component);
        return component;
    }

//    @Override
//    public C add(int index, T tool, String name, AppComponentOptions options) {
//        if (name.indexOf('/') > 0) {
//            throw new IllegalArgumentException("use add(AppComponentModel,Path) instead");
//        }
//        C component = (C) ((AppContainerBase)(base)).createPreferredComponent(tool,
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
            if (child.path().propertyName().equals(name)) {
                return remove(i);
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

    protected AppComponent createComponentCopy(AppComponent component){
        Class componentType=component.getClass();
        AppComponentModel model=component.model();
        Constructor c = resolveDefaultComponentConstructor(componentType, model.getClass());
        c.setAccessible(true);
        try {
            return (AppComponent) c.newInstance(model);
        } catch (Exception ex) {
            throw new IllegalArgumentException("invalid constructor "+componentType.getSimpleName()+"("+model.getClass().getSimpleName()+")",ex);
        }
    }

    protected Constructor resolveDefaultComponentConstructor(Class componentType,Class modelClass){
        List<Constructor> possibilities=new ArrayList<>();
        for (Constructor cc : componentType.getDeclaredConstructors()) {
            if(cc.getParameterCount()==1){
                Class t1 = cc.getParameterTypes()[0];
                if(t1.isAssignableFrom(modelClass)){
                    possibilities.add(cc);
                }
            }
        }
        if(possibilities.isEmpty()){
            throw new IllegalArgumentException("Missing constructor "+componentType.getSimpleName()+"("+modelClass.getSimpleName()+")");
        }
        if(possibilities.size()>1){
            throw new IllegalArgumentException("Ambiguous constructors "+componentType.getSimpleName()+"("+modelClass.getSimpleName()+") : "+possibilities);
        }
        return possibilities.get(0);
    }

    public List<AppComponent> addAll(AppComponent component, Path relativePath, Path... all) {
        List<AppComponent> a = new ArrayList<>();
        add(component, relativePath);
        for (Path path : all) {
            AppComponent copy = createComponentCopy(component);
            add(copy, path);
            a.add(copy);
        }
        return a;
    }

//    public List<AppComponent> addAll(AppComponentModel tool, Path relativePath, Path... all) {
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

}
