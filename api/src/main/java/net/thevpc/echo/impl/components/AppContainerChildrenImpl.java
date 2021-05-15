package net.thevpc.echo.impl.components;

import net.thevpc.common.props.PropertyType;
import net.thevpc.common.props.impl.WritableListImpl;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.impl.tools.ToolFolder;
import net.thevpc.echo.impl.tools.ToolSeparator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppContainerChildrenImpl<C extends AppComponent, T extends AppTool> extends WritableListImpl<C>
        implements AppContainerChildren<C, T> {
    private AppContainer base;
    private Class componentType;
    private Class toolType;

    public AppContainerChildrenImpl(String name, Class<? extends C> componentType, Class<? extends T> toolType, AppContainer base) {
        super(name, PropertyType.of(componentType));
        this.base = base;
        this.componentType = componentType;
        this.toolType = toolType;
    }

    public Class getComponentType() {
        return componentType;
    }

    public Class getToolType() {
        return toolType;
    }

    @Override
    protected boolean addImpl(int index, C child) {
        if (!child.path().get().parent().startsWith(base.path().get())) {
            String name = null;
            if (child.path().get() == null || child.path().get().isEmpty()) {
                name = child.tool().id();
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

        base.peer();
        if(child instanceof AppComponentBase) {
            AppComponentBase cc = (AppComponentBase) child;
            cc.internal_setParent(base);
        }
        child.peer();
        base.peer().addChild(child, index);
        return true;
    }

    @Override
    protected C removeImpl(int index) {
        C c = super.removeImpl(index);
        AppComponentBase cc = (AppComponentBase) c;
        cc.prepareUnshowing();
        base.peer().removeChild(cc, index);
        return c;
    }

    public AppComponent get(AppPath relativePath) {
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
            AppComponent n = get(AppPath.of(relativePath.first()));
            if (n instanceof AppContainer) {
                return ((AppContainer) n).children().get(relativePath.skipFirst());
            }
            return null;
        }
    }

    public AppComponent add(AppComponent component, AppPath relativePath) {
        if (relativePath != null) {
            component.path().set(base.path().get().append(relativePath));
        }
        return add((AppTool) null, relativePath, null);
    }

    public AppComponent add(AppTool tool, AppPath relativePath, AppComponentOptions options) {
        if(relativePath.last().equals("*")){
            relativePath=relativePath.parent().append(tool.id());
        }
        AppContainer parentContainer = base;
        AppPath this_path = base.path().get();

        AppContainer goodParent = null;
        if (relativePath.isEmpty()) {
            //will generate a random name
            relativePath = AppPath.of(UUID.randomUUID().toString());
        } else {
            while (relativePath.size() > 1) {
                String first = relativePath.first();
                goodParent = null;
                for (Object ochild : parentContainer.children()) {
                    AppComponent child = (AppComponent) ochild;
                    if (child.path().get().name().equals(first)) {
                        goodParent = (AppContainer) child;
                        break;
                    }
                }
                if (goodParent == null) {
                    ToolFolder ff = new ToolFolder(base.app());
                    parentContainer = (AppContainer) parentContainer.children().add(ff, first, null);
                } else {
                    parentContainer = goodParent;
                }
                relativePath = relativePath.skipFirst();
            }
        }
        String theName = relativePath.name();

        int u = parentContainer.children().size();
        int index = -1;
        Integer order = options == null ? null : options.order();
        for (int i = 0; i < u; i++) {
            AppComponent child = (AppComponent) parentContainer.children().get(i);
            Integer o = child.order().get();
            if (order == null || o == null || o <= order) {
                index = i;
            }
        }
        if (index < 0) {
            index = parentContainer.children().size();
        }
        return parentContainer.children().add(index, tool, theName, options);
    }

    @Override
    public C add(T tool, String name, AppComponentOptions options) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use add(AppTool,AppPath) instead");
        }
        C component = (C) ((AppContainerBase)(base)).createPreferredComponent(tool,
                name, base.path().get().append(name),
                options
        );
        add(component);
        return component;
    }

    public C add(C component, String name) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use add(AppTool,AppPath) instead");
        }
        component.path().set(base.path().get().append(name));
        add(component);
        return component;
    }

    @Override
    public C add(int index, T tool, String name, AppComponentOptions options) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use add(AppTool,AppPath) instead");
        }
        C component = (C) ((AppContainerBase)(base)).createPreferredComponent(tool,
                name, base.path().get().append(name),
                options
        );
        add(index, component, name);
        return component;
    }

    @Override
    public C add(int index, C component, String name) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use add(AppTool,AppPath) instead");
        }
        component.path().set(base.path().get().append(name));
        add(index, component);
        return component;
    }

    public AppComponent remove(String name) {
        if (name.indexOf('/') > 0) {
            throw new IllegalArgumentException("use remove(AppPath) instead");
        }
        for (int i = 0; i < size(); i++) {
            AppComponent child = get(i);
            if (child.path().name().equals(name)) {
                return remove(i);
            }
        }
        return null;
    }

    public AppComponent remove(AppPath relativePath) {
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

    public AppComponent add(AppTool tool, AppPath relativePath) {
        return add(tool, relativePath, null);
    }

    public List<AppComponent> addAll(AppTool tool, AppPath relativePath, AppPath... all) {
        List<AppComponent> a = new ArrayList<>();
        a.add(add(tool, relativePath, null));
        for (AppPath appPath : all) {
            a.add(add(tool, appPath, null));
        }
        return a;
    }

    @Override
    public AppComponent addSeparator(AppPath relativePath) {
        return add(new ToolSeparator(base.app()), relativePath);
    }

}
