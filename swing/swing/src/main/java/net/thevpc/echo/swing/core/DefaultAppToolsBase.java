package net.thevpc.echo.swing.core;

import net.thevpc.common.props.*;
import net.thevpc.echo.AppToolRadioBox;
import net.thevpc.echo.AppToolSeparator;
import net.thevpc.echo.Application;
import net.thevpc.echo.ItemPath;
import net.thevpc.echo.AppTools;
import net.thevpc.echo.AbstractAppAction;
import net.thevpc.echo.AppToolCheckBox;
import net.thevpc.echo.AppToolFolder;
import net.thevpc.echo.AppToolAction;
import net.thevpc.echo.AppToolContainer;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.AppState;
import net.thevpc.echo.AppComponent;
import net.thevpc.echo.AppTool;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javax.swing.Action;

import net.thevpc.echo.swing.core.tools.AppToolActionImpl;
import net.thevpc.echo.swing.core.tools.AppToolCheckBoxImpl;
import net.thevpc.echo.swing.core.tools.AppToolFolderImpl;
import net.thevpc.echo.swing.core.tools.AppToolRadioBoxImpl;
import net.thevpc.echo.swing.core.tools.AppToolSeparatorImpl;

public abstract class DefaultAppToolsBase implements AppTools {

    public WritablePList<AppComponent> components = Props.of("components").listOf(AppComponent.class);
    protected Map<String, ToolInfo> toolsMap = new HashMap<>();
    protected Application application;
    protected ToolMapResolverPropertyListener toolMapResolverAppPropertyListener = new ToolMapResolverPropertyListener();
    private WritablePList<AppTool> toolsList = Props.of("tools").listOf(AppTool.class);
    private java.util.List tools0 = new ArrayList<>();

    public DefaultAppToolsBase(Application application) {
        this.application = application;
        this.application.state().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppState s = (AppState) event.getNewValue();
                if (s == AppState.INIT) {
                    for (Iterator<AppToolComponent> iterator = tools0.iterator(); iterator.hasNext();) {
                        AppToolComponent tool = iterator.next();
                        iterator.remove();
                        addTool(tool);
                    }
                }
            }
        });
    }

    @Override
    public PList<AppTool> all() {
        return toolsList.readOnly();
    }

    @Override
    public AppTool getTool(String id) {
        ToolInfo o = toolsMap.get(id);
        return o == null ? null : o.tool;
    }

    @Override
    public AppComponent[] getComponents(String id) {
        ToolInfo o = toolsMap.get(id);
        return o == null ? new AppComponent[0] : o.components.values().toArray(new AppComponent[0]);
    }

    @Override
    public <T extends AppTool> AppToolComponent<T> addTool(T tool, String path) {
        AppToolComponent<T> e = AppToolComponent.of(tool, path);
        addTool(e);
        return e;
    }

    @Override
    public AppToolComponent<AppToolFolder> addFolder(String id, String path) {
        AppToolComponent<AppToolFolder> a = AppToolComponent.of(new AppToolFolderImpl(path), path);
        addTool(a);
        return a;
    }

    @Override
    public AppToolComponent<AppToolFolder> addFolder(String path) {
        return addFolder(null, path);
    }

    @Override
    public AppToolComponent<AppToolSeparator> addSeparator(String id, String path) {
        AppToolComponent<AppToolSeparator> a = AppToolComponent.of(new AppToolSeparatorImpl(path), path);
        addTool(a);
        return a;
    }

    @Override
    public AppToolComponent<AppToolSeparator> addSeparator(String path) {
        return addSeparator(null, path);
    }

    @Override
    public AppToolComponent<AppToolAction> addAction(String id, String path) {
        ItemPath ipath = ItemPath.of(path);
        path = ipath.toString();
        if (id == null) {
            id = ipath.toString();
        }
        AppToolAction action = new AppToolActionImpl(id, null);
        action.title().set(ipath.name());
        AppToolComponent<AppToolAction> binding = AppToolComponent.of(action, path);
        addTool(binding);
        return binding;
    }

    @Override
    public AppToolComponent<AppToolRadioBox> addRadio(String id, String path) {
        ItemPath ipath = ItemPath.of(path);
        path = ipath.toString();
        if (id == null) {
            id = path.toString();
        }
        AppToolRadioBox action = new AppToolRadioBoxImpl(id, null);
        action.title().set(ipath.name());
        AppToolComponent<AppToolRadioBox> a = AppToolComponent.of(action, path);
        addTool(a);
        return a;
    }

    @Override
    public AppToolComponent<AppToolCheckBox> addCheck(String id, String path) {
        ItemPath ipath = ItemPath.of(path);
        path = ipath.toString();
        if (id == null) {
            id = path.toString();
        }
        AppToolCheckBox action = new AppToolCheckBoxImpl(id, null);
        action.title().set(ipath.name());
        AppToolComponent<AppToolCheckBox> a = AppToolComponent.of(action, path);
        addTool(a);
        return a;
    }

    @Override
    public AppToolComponent<AppToolAction> addAction(String path) {
        return addAction(null, path);
    }

    @Override
    public AppToolComponent<AppToolRadioBox> addRadio(String path) {
        return addRadio(null, path);
    }

    @Override
    public AppToolComponent<AppToolCheckBox> addCheck(String path) {
        return addCheck(null, path);
    }

    @Override
    public PList<AppComponent> components() {
        return components.readOnly();
    }

    public void addRootContainer(AppToolContainer c) {
        if (c != null) {
            c.tools().components().listeners().add(toolMapResolverAppPropertyListener);
        }
    }

    public void removeRootContainer(AppToolContainer c) {
        if (c != null) {
            c.tools().components().listeners().add(toolMapResolverAppPropertyListener);
        }
    }

    public void addCheck(String id, WritablePValue<Boolean> property, String path, String... paths) {
        AppToolComponent<AppToolCheckBox> c = this.addCheck(path);
        AppToolCheckBox tool = c.tool();
        tool.smallIcon().set(id);
        tool.selected().set(property.get());
        tool.selected().bind(property);
        for (int i = 0; i < paths.length; i++) {
            c.copyTo(this, paths[i]);
        }
    }

    public <T> void addRadio(String id, String group, WritablePValue<T> property, T value, String path, String... paths) {
        AppToolComponent<AppToolRadioBox> c = this.addRadio(path);
        AppToolRadioBox tool = c.tool();
        tool.group().set(group);
        tool.smallIcon().set(id);
        tool.selected().set(Objects.equals(value, property.get()));
        //property.

        tool.selected().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                boolean selected = event.getNewValue();
                if (selected) {
                    property.set(value);
                }
            }
        });
        for (int i = 0; i < paths.length; i++) {
            c.copyTo(this, paths[i]);
        }
    }

    public void addAction(Action al, String path, String... paths) {
        AppToolComponent<AppToolAction> save = this.addAction(path);
        AppToolAction tool = save.tool();
        tool.action().set(al);
        String iconId = (String) al.getValue("SmallIconId");
        tool.smallIcon().set(iconId);
        application.iconSet().id().listeners().add((PropertyEvent event) -> {
            al.putValue("SmallIconId", tool.smallIcon().get());
            al.putValue(Action.SMALL_ICON, application.iconSet().icon(tool.smallIcon().get()).get());
        });
        tool.smallIcon().listeners().add((PropertyEvent event) -> {
            al.putValue("SmallIconId", tool.smallIcon().get());
            al.putValue(Action.SMALL_ICON, application.iconSet().icon(tool.smallIcon().get()).get());
        });
        al.putValue(Action.SMALL_ICON, application.iconSet().icon(tool.smallIcon().get()).get());
        for (int i = 0; i < paths.length; i++) {
            save.copyTo(this, paths[i]);
        }
    }

    public static class ToolInfo {

        private String id;
        private AppTool tool;
        private Map<ItemPath, AppComponent> components;
    }

    private class ToolMapResolverPropertyListener implements PropertyListener {

        private AppToolComponent toAppToolBinding(Object oldValue) {
            AppComponent o = (AppComponent) oldValue;
            if (o != null) {
                if (o instanceof AppToolComponent) {
                    return (AppToolComponent) o;
                }
            }
            return null;
        }

        private void onRemove(Object oldValue) {
            AppToolComponent b = toAppToolBinding(oldValue);
            AppComponent o = (AppComponent) oldValue;
            if (b != null) {
                components.remove(b);
                AppTool t = b.tool();
                ToolInfo p = toolsMap.get(t.id());
                if (p != null) {
                    p.components.remove(b.path());
                    if (p.components.size() == 0) {
                        toolsMap.remove(t.id());
                        toolsList.remove(p.tool);
                    }
                }
            }
        }

        private void onAdd(Object oldValue) {
            AppToolComponent b = toAppToolBinding(oldValue);
            AppComponent o = (AppComponent) oldValue;
            if (b != null) {
                components.add(b);
                AppTool t = b.tool();
                ToolInfo p = toolsMap.get(t.id());
                if (p != null) {
                    p.components.put(b.path(), b);
                } else {
                    p = new ToolInfo();
                    p.id = t.id();
                    p.tool = t;
                    p.components = new HashMap<>();
                    p.components.put(b.path(), b);
                    toolsList.add(p.tool);
                }
            }
        }

        @Override
        public void propertyUpdated(PropertyEvent event) {
            switch (event.getAction()) {
                case ADD: {
                    onAdd(event.getNewValue());
                    break;
                }
                case REMOVE: {
                    onRemove(event.getOldValue());
                    break;
                }
                case UPDATE: {
                    onRemove(event.getOldValue());
                    onAdd(event.getNewValue());
                    break;
                }
            }
        }

    }

    @Override
    public void refresh() {
        for (AppComponent appComponent : components()) {
            if (appComponent instanceof AppToolComponent) {
                AppTool appTool = ((AppToolComponent) appComponent).tool();
                if (appTool instanceof AppToolAction) {
                    ActionListener a = ((AppToolAction) appTool).action().get();
                    if (a instanceof AbstractAppAction) {
                        ((AbstractAppAction) a).refresh();
                    }
                }
            }
        }
        for (AppTool appTool : all()) {
            if (appTool instanceof AppToolAction) {
                ActionListener a = ((AppToolAction) appTool).action().get();
                if (a instanceof AbstractAppAction) {
                    ((AbstractAppAction) a).refresh();
                }
            }
        }
    }

}
