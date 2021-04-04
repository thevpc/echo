package net.thevpc.echo.swing.core;

import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.AppPropertyBinding;
import net.thevpc.common.props.impl.PropertyContainerSupport;
import net.thevpc.echo.Application;
import net.thevpc.echo.AppComponentRenderer;
import net.thevpc.echo.ItemPath;
import net.thevpc.echo.AppTools;
import net.thevpc.echo.AppMenuBar;
import net.thevpc.echo.AppStatusBar;
import net.thevpc.echo.AppWindow;
import net.thevpc.echo.AppToolBar;
import net.thevpc.echo.AppWorkspace;
import net.thevpc.echo.AppWindowDisplayMode;
import net.thevpc.echo.AppToolContainer;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.AppNode;
import net.thevpc.echo.AppWindowStateSetValue;
import net.thevpc.echo.AppComponent;
import net.thevpc.echo.AppTool;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class AbstractAppWindow implements AppWindow {

    public WritableValue<String> title = Props.of("title").valueOf(String.class, null);
    public WritableValue<ImageIcon> icon = Props.of("icon").valueOf(ImageIcon.class, null);
    public WritableValue<AppWindowDisplayMode> displayMode = Props.of("displayMode")
            .adjust(e -> {
                if (e.getNewValue() == null) {
                    return AppWindowDisplayMode.NORMAL;
                }
                return e.getNewValue();
            })
            .valueOf(AppWindowDisplayMode.class, AppWindowDisplayMode.NORMAL);
    public AppWindowStateSetValue state = new AppWindowStateSetValue("state");
    private WritableValue<AppMenuBar> menuBar = Props.of("menuBar").valueOf(AppMenuBar.class, null);
    private WritableValue<AppStatusBar> statusBar = Props.of("statusBar").valueOf(AppStatusBar.class, null);
    private WritableValue<AppToolBar> toolBar = Props.of("toolBar").valueOf(AppToolBar.class, null);
    private WritableValue<AppWorkspace> workspace = Props.of("workspace").valueOf(AppWorkspace.class, null);
    private Application application;
    private PropertyContainerSupport s;
    private AbstractAppToolsBase tools;
    private List<AppToolContainer> rootContainers = new ArrayList<>();
    private ItemPath rootPath;
    private AppNode root;

    public AbstractAppWindow(ItemPath rootPath, Application application) {
        this.application = application;
        this.s = new PropertyContainerSupport(rootPath.toString(), this);
        this.rootPath = rootPath;
        this.root = new AppNode() {
            @Override
            public AppComponent getComponent() {
                return AbstractAppWindow.this;
            }

            @Override
            public int getOrder() {
                return 0;
            }

            @Override
            public ItemPath path() {
                return rootPath;
            }

            @Override
            public AppNode[] getChildren() {
                List<AppNode> nn = new ArrayList<>();
                for (AppToolContainer r : rootContainers) {
                    nn.add(r.rootNode());
                }
                return nn.toArray(new AppNode[0]);
            }

            @Override
            public AppNode get(ItemPath path) {
                if (path.isEmpty()) {
                    return this;
                }
                for (AppToolContainer rootContainer : rootContainers) {
                    if (path.startsWith(rootContainer.rootNode().path())) {
                        return rootContainer.rootNode().get(path);
                    }
                }
                return null;
            }

        };
        tools = new AbstractAppToolsBase(application) {
            @Override
            public <T extends AppTool> void addTool(AppToolComponent<T> tool) {
//                String first = tool.path().first();
                Set<String> available = new HashSet<>();
//                if (tool.path().startsWith(rootNode().getPath())) {
                for (AppNode node : nodes()) {
                    AppToolContainer c = (AppToolContainer) node.getComponent();
                    available.add(c.path().toString());
                    if (tool.path().startsWith(node.path())) {
                        c.tools().addTool(tool);
                        return;
                    }
                }

                throw new IllegalArgumentException("Unable to resolve to a valid path '" + tool.path() + "' . root nodes start with one of : " + available);
            }

            @Override
            public <T extends AppTool> void removeTool(AppToolComponent<T> tool) {

            }
        };

        menuBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                removeRootContainer(event.getOldValue());
                addRootContainer(event.getNewValue());
            }
        });
        statusBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                removeRootContainer(event.getOldValue());
                addRootContainer(event.getNewValue());
            }
        });
        toolBar().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                removeRootContainer(event.getOldValue());
                addRootContainer(event.getNewValue());
            }
        });
//        workspace().listeners().add(new PropertyListener() {
//            @Override
//            public void propertyUpdated(PropertyEvent event) {
//                removeRootContainer(event.getOldValue());
//                removeRootContainer(event.getNewValue());
//            }
//        });
    }

    @Override
    public WritableValue<AppWindowDisplayMode> displayMode() {
        return displayMode;
    }

    @Override
    public Application application() {
        return application;
    }

    @Override
    public WritableValue<AppMenuBar> menuBar() {
        return menuBar;
    }

    @Override
    public WritableValue<AppStatusBar> statusBar() {
        return statusBar;
    }

    @Override
    public WritableValue<AppToolBar> toolBar() {
        return toolBar;
    }

    @Override
    public WritableValue<AppWorkspace> workspace() {
        return workspace;
    }

    @Override
    public WritableValue<String> title() {
        return title;
    }

    @Override
    public WritableValue<ImageIcon> icon() {
        return icon;
    }

    @Override
    public AppWindowStateSetValue state() {
        return state;
    }

    @Override
    public AppPropertyBinding[] getProperties() {
        return s.getProperties();
    }

    @Override
    public PropertyListeners listeners() {
        return s.listeners();
    }

    protected void addRootContainer(AppToolContainer c) {
        if (c != null) {
            rootContainers.add(c);
            tools.addRootContainer(c);
        }
    }

    protected void removeRootContainer(AppToolContainer c) {
        if (c != null) {
            rootContainers.remove(c);
            tools.removeRootContainer(c);
        }
    }

    //    @Override
    public AppNode[] nodes() {
        return rootContainers
                .stream().map(x -> x.rootNode()).toArray(AppNode[]::new);
    }

    @Override
    public ItemPath path() {
        return rootPath;
    }

    @Override
    public AppComponentRenderer renderer() {
        return null;
    }

    @Override
    public AppNode rootNode() {
        return root;
    }

    @Override
    public AppTools tools() {
        return tools;
    }
}
