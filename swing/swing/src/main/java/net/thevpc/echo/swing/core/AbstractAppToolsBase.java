package net.thevpc.echo.swing.core;

import net.thevpc.common.props.*;
import net.thevpc.echo.AppToolSeparator;
import net.thevpc.echo.Application;
import net.thevpc.echo.ItemPath;
import net.thevpc.echo.AppTools;
import net.thevpc.echo.AbstractAppAction;
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
import javax.swing.Action;
import javax.swing.Box;
import net.thevpc.echo.AppComponentRenderer;
import net.thevpc.echo.AppToolActionBuilder;
import net.thevpc.echo.AppToolCheckBoxBuilder;
import net.thevpc.echo.AppToolRadioBoxBuilder;
import net.thevpc.echo.AppToolsConfig;
import net.thevpc.echo.CustomAppTool;

import net.thevpc.echo.swing.core.tools.AppToolActionImpl;
import net.thevpc.echo.swing.core.tools.AppToolFolderImpl;
import net.thevpc.echo.swing.core.tools.AppToolSeparatorImpl;

public abstract class AbstractAppToolsBase implements AppTools {

    public WritableList<AppComponent> components = Props.of("components").listOf(AppComponent.class);
    protected Map<String, ToolInfo> toolsMap = new HashMap<>();
    protected Application application;
    protected ToolMapResolverPropertyListener toolMapResolverAppPropertyListener = new ToolMapResolverPropertyListener();
    private WritableList<AppTool> toolsList = Props.of("tools").listOf(AppTool.class);
    private java.util.List tools0 = new ArrayList<>();
    private AppToolsConfig config = new AppToolsConfig();

    public AbstractAppToolsBase(Application application) {
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

    public AppToolsConfig config() {
        return config;
    }

    @Override
    public ObservableList<AppTool> all() {
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
    public AppToolComponent<AppToolFolder> addFolder(String path) {
        AppToolComponent<AppToolFolder> a = AppToolComponent.of(new AppToolFolderImpl(path, application, this), path);
        addTool(a);
        return a;
    }

    @Override
    public AppToolComponent<AppToolSeparator> addSeparator(String path) {
        AppToolComponent<AppToolSeparator> a = AppToolComponent.of(new AppToolSeparatorImpl(path, application, this), path);
        addTool(a);
        return a;
    }

    @Override
    public ObservableList<AppComponent> components() {
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

    

    public <T> AppToolRadioBoxBuilder addRadio() {
        return new DefaultAppToolRadioBoxBuilder(this);
    }
    
    public <T> AppToolCheckBoxBuilder addCheck() {
        return new DefaultAppToolCheckBoxBuilder(this);
    }

    @Override
    public AppToolActionBuilder addAction() {
        return new DefaultAppToolActionBuilder(this);
    }

    
    public AppToolAction addAction(Action al, String path, String... paths) {
        return addAction().bind(al).path(path).path(paths).tool();
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

    @Override
    public AppTool addCustomTool(String id, AppComponentRenderer renderer, String path, int order) {
        CustomAppTool t = new CustomAppTool(id, application, this);
        addTool(AppToolComponent.of(t, path, order,
                renderer
        ));
        return t;
    }

    @Override
    public AppTool addCustomTool(String path, AppComponentRenderer renderer) {
        return addCustomTool(path, renderer, path, 0);
    }

    @Override
    public AppTool addHorizontalGlue(String path) {
        return addCustomTool(path, x -> Box.createHorizontalGlue());
    }

    @Override
    public AppTool addVerticalGlue(String path) {
        return addCustomTool(path, x -> Box.createVerticalGlue());
    }

    @Override
    public AppTool addVerticalStrut(String path, int height) {
        return addCustomTool(path, x -> Box.createVerticalStrut(height));
    }

    @Override
    public AppTool addHorizontalStrut(String path, int width) {
        return addCustomTool(path, x -> Box.createHorizontalStrut(width));
    }

    /**
     * should show a vertical separator line.but dummy impl could call
     * addHorizontalStrut
     *
     * @param path path
     * @param width width
     */
    @Override
    public AppTool addHorizontalSeparator(String path, int width) {
        return addHorizontalStrut(path, width);
    }

    @Override
    public AppTool addHorizontalSeparator(String path) {
        return addHorizontalSeparator(path, 5);
    }

}
