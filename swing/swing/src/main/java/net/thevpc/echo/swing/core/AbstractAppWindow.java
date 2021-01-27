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

    public WritablePValue<String> title = Props.of("title").valueOf(String.class, null);
    public WritablePValue<ImageIcon> icon = Props.of("icon").valueOf(ImageIcon.class, null);
    public WritablePValue<AppWindowDisplayMode> displayMode = Props.of("displayMode")
            .adjust(e -> {
                if (e.getNewValue() == null) {
                    return AppWindowDisplayMode.NORMAL;
                }
                return null;
            })
            .valueOf(AppWindowDisplayMode.class, AppWindowDisplayMode.NORMAL);
    public AppWindowStateSetValue state = new AppWindowStateSetValue("state");
    private WritablePValue<AppMenuBar> menuBar = Props.of("menuBar").valueOf(AppMenuBar.class, null);
    private WritablePValue<AppStatusBar> statusBar = Props.of("statusBar").valueOf(AppStatusBar.class, null);
    private WritablePValue<AppToolBar> toolBar = Props.of("toolBar").valueOf(AppToolBar.class, null);
    private WritablePValue<AppWorkspace> workspace = Props.of("workspace").valueOf(AppWorkspace.class, null);
    private Application application;
    private PropertyContainerSupport s;
    private DefaultAppToolsBase tools;
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
            public ItemPath getPath() {
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
        };
        tools = new DefaultAppToolsBase(application) {
            @Override
            public <T extends AppTool> void addTool(AppToolComponent<T> tool) {
                String first = tool.path().first();
                AppToolComponent subBinding = AppToolComponent.of(tool.tool(), tool.path().skipFirst().toString(), tool.order(), tool.renderer());
                Set<String> available = new HashSet<>();
                for (AppNode node : nodes()) {
                    AppToolContainer c = (AppToolContainer) node.getComponent();
                    ItemPath path = c.rootNode().getPath();
                    available.add(path.first());
                    if (path.first().equals(first)) {
                        c.tools().addTool(subBinding);
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
    public WritablePValue<AppWindowDisplayMode> displayMode() {
        return displayMode;
    }

    @Override
    public Application application() {
        return application;
    }

    @Override
    public WritablePValue<AppMenuBar> menuBar() {
        return menuBar;
    }

    @Override
    public WritablePValue<AppStatusBar> statusBar() {
        return statusBar;
    }

    @Override
    public WritablePValue<AppToolBar> toolBar() {
        return toolBar;
    }

    @Override
    public WritablePValue<AppWorkspace> workspace() {
        return workspace;
    }

    @Override
    public WritablePValue<String> title() {
        return title;
    }

    @Override
    public WritablePValue<ImageIcon> icon() {
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
