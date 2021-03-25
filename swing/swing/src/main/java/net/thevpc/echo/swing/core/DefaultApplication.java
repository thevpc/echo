package net.thevpc.echo.swing.core;

import net.thevpc.echo.*;
import net.thevpc.common.i18n.DefaultI18n;
import net.thevpc.common.i18n.I18n;
import net.thevpc.common.iconset.IconSet;
import net.thevpc.common.msg.StringMessage;
import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.AppPropertyBinding;
import net.thevpc.common.props.impl.PropertyContainerSupport;
import net.thevpc.echo.swing.Applications;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import net.thevpc.echo.swing.core.swing.AppToolActionComponent;
import net.thevpc.echo.swing.core.swing.AppToolCheckBoxComponent;
import net.thevpc.echo.swing.core.swing.AppToolFolderComponent;
import net.thevpc.echo.swing.core.swing.AppToolRadioBoxComponent;
import net.thevpc.echo.swing.core.swing.AppToolSeparatorComponent;

public class DefaultApplication implements Application {

    protected WritablePValue<AppState> state = Props.of("state").valueOf(AppState.class, AppState.NONE);
    protected WritablePValue<String> name = Props.of("name").valueOf(String.class, "");
    protected DefaultAppMessages messages = new DefaultAppMessages(this);
    protected DefaultAppLogs logs = new DefaultAppLogs(this);
    protected WritablePList<AppShutdownVeto> shutdownVetos = Props.of("shutdownVetos").listOf(AppShutdownVeto.class);
    protected WritablePLMap<String, IconSet> iconSets = Props.of("iconSets").lmapOf(String.class, IconSet.class, x -> x.getId());
    protected AppIconSet activeIconSet = new DefaultAppIconSet(iconSets);
    protected I18n i18n = new DefaultI18n();
    protected PropertyContainerSupport support = new PropertyContainerSupport("app", this);
    private List<AppToolContainer> rootContainers = new ArrayList<>();
    private GlobalAppTools tools = new GlobalAppTools(this);
    private AppHistory history = new DefaultAppUndoManager(this);
    private Map<String, ButtonGroup> buttonGroups = new HashMap<>();
    private WritablePValue<AppWindow> mainWindow = Props.of("mainWindow").valueOf(AppWindow.class, null);
    private ApplicationBuilderImpl applicationBuilderImpl = new ApplicationBuilderImpl(this);
    private WritablePValue<AppPropertiesTree> activeProperties = Props.of("activeProperties").valueOf(AppPropertiesTree.class, null);
    private WritablePValue<String> currentWorkingDirectory = Props.of("currentWorkingDirectory").valueOf(String.class, null);
    protected AppErrors errors = new DefaultAppErrors(this);
    protected DefaultAppComponentRendererFactory componentRendererFactory = new DefaultAppComponentRendererFactory();
    private List<Semaphore> waitings = new ArrayList<>();


    public DefaultApplication() {
        support.add(name);
        support.add(state.readOnly());
        builder().mainWindowBuilder().get().windowFactory().set(Applications.Windows.Default());
        builder().mainWindowBuilder().get().menuBarFactory().set(Applications.MenuBars.Default());
        builder().mainWindowBuilder().get().statusBarFactory().set(Applications.StatusBars.Default());
        builder().mainWindowBuilder().get().toolBarFactory().set(Applications.ToolBars.Default());
        builder().mainWindowBuilder().get().workspaceFactory().set(Applications.Workspaces.Default());

        componentRendererFactory.setToolRenderer(
                AppToolSeparator.class, new AppToolSeparatorComponent());
        componentRendererFactory.setToolRenderer(
                AppToolFolder.class, new AppToolFolderComponent());
        componentRendererFactory.setToolRenderer(
                AppToolRadioBox.class, new AppToolRadioBoxComponent());
        componentRendererFactory.setToolRenderer(
                AppToolCheckBox.class, new AppToolCheckBoxComponent());
        componentRendererFactory.setToolRenderer(
                AppToolAction.class, new AppToolActionComponent());

        mainWindow.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppWindow o = event.getOldValue();
                if (o != null) {
                    o.state().vetos().removeIf(x -> x instanceof WinPropertyVetoImpl);
                    removeRootContainer(o);
                }
                AppWindow n = event.getNewValue();
                if (n != null) {
                    n.state().vetos().add(new WinPropertyVetoImpl(DefaultApplication.this, n));
                    addRootContainer(n);
                }
            }

        });
        state.vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent e) {
                AppState a = e.getNewValue();
//                if (a == AppState.CLOSING) {
//                    DefaultAppEvent evt = new DefaultAppEvent(DefaultApplication.this, state);
//                    for (AppShutdownVeto v : shutdownVetos) {
//                        v.vetoableChange(evt);
//                    }
//                }
//                if (a == AppState.CLOSED) {
//                    DefaultAppEvent evt = new DefaultAppEvent(DefaultApplication.this, state);
//                    for (AppShutdownVeto v : shutdownVetos) {
//                        v.vetoableChange(evt);
//                    }
//                }
            }
        });
        state.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                if (event.getNewValue() == AppState.CLOSING) {
                    state.set(AppState.CLOSED);
                }
            }

            @Override
            public int order() {
                return Integer.MAX_VALUE;
            }

        });
    }

    public WritablePValue<String> currentWorkingDirectory() {
        return currentWorkingDirectory;
    }

    @Override
    public WritablePValue<AppPropertiesTree> activeProperties() {
        return activeProperties;
    }

    @Override
    public WritablePList<AppShutdownVeto> shutdownVetos() {
        return shutdownVetos;
    }

    @Override
    public WritablePLMap<String, IconSet> iconSets() {
        return iconSets;
    }

    @Override
    public AppIconSet iconSet() {
        return activeIconSet;
    }

    @Override
    public I18n i18n() {
        return i18n;
    }

    @Override
    public PValue<String> name() {
        return name;
    }

    @Override
    public PValue<AppState> state() {
        return state.readOnly();
    }

    @Override
    public Application start() {
        if (state().get() == AppState.NONE) {
            initImpl();
            state.set(AppState.INIT);
            state.set(AppState.STARTING);
            startImpl();
            state.set(AppState.STARTED);
            logs().add(new StringMessage(Level.INFO, "Application Started"));
        }
        return this;
    }

    @Override
    public Application shutdown() {
        switch (state.get()) {
            case CLOSED: {
                break;
            }
            default: {
                state.set(AppState.CLOSED);
                break;
            }
        }
        return this;
    }

    @Override
    public AppTools tools() {
        return tools;
    }

    @Override
    public WritablePValue<AppWindow> mainWindow() {
        return mainWindow;
    }

    @Override
    public AppNode[] nodes() {
        return rootContainers
                .stream().map(x -> x.rootNode()).toArray(AppNode[]::new);
    }

    @Override
    public ApplicationBuilder builder() {
        return applicationBuilderImpl;
    }

    @Override
    public AppHistory history() {
        return history;
    }

    @Override
    public AppMessages messages() {
        return messages;
    }

    @Override
    public AppLogs logs() {
        return logs;
    }

//    protected void setStatusBar(AppStatusBar statusBar) {
//        if (state().get().ordinal() >= AppState.STARTED.ordinal()) {
//            throw new IllegalArgumentException("Already started");
//        }
//        removeRootContainer(this.statusBar);
//        this.statusBar = statusBar;
//        addRootContainer(this.statusBar);
//    }
//
//    protected void setMenuBar(AppMenuBar menuBar) {
//        if (state().get().ordinal() >= AppState.STARTED.ordinal()) {
//            throw new IllegalArgumentException("Already started");
//        }
//        removeRootContainer(this.menuBar);
//        this.menuBar = menuBar;
//        addRootContainer(this.menuBar);
//    }
//
//    protected void setToolBar(AppToolBar toolBar) {
//        if (state().get().ordinal() >= AppState.STARTED.ordinal()) {
//            throw new IllegalArgumentException("Already started");
//        }
//        removeRootContainer(this.toolBar);
//        this.toolBar = toolBar;
//        addRootContainer(this.toolBar);
//    }
    public ButtonGroup getButtonGroup(String name) {
        ButtonGroup p = buttonGroups.get(name);
        if (p == null) {
            p = new ButtonGroup();
            buttonGroups.put(name, p);
        }
        return p;
    }

    protected void initImpl() {
        AppWindowBuilder wb = builder().mainWindowBuilder().get();
        if (wb == null) {
            throw new IllegalArgumentException("missing AppWindowBuilder");
        }
        AppWindow window = wb.createWindow("mainWindow", this);
        mainWindow().set(window);
        window.state().listeners().add(event -> {
            AppWindowStateSet s = event.getNewValue();
            if (s.is(AppWindowState.CLOSING)) {
                if (state.get().ordinal() < AppState.CLOSING.ordinal()) {
                    state.set(AppState.CLOSING);
                }
            } else if (s.is(AppWindowState.CLOSED)) {
                if (state.get().ordinal() < AppState.CLOSING.ordinal()) {
                    state.set(AppState.CLOSING);
                } else {
                    state.set(AppState.CLOSED);
                }
            }
        });
        state.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppState s = (AppState) event.getNewValue();
                if (s == AppState.CLOSING) {
                    state.set(AppState.CLOSED);
                }else if (s == AppState.CLOSED) {
                    onFreeWaiters();
                }
            }

            @Override
            public int order() {
                return Integer.MAX_VALUE;
            }

        });
    }

    @Override
    public AppPropertyBinding[] getProperties() {
        return support.getProperties();
    }

    @Override
    public PropertyListeners listeners() {
        return support.listeners();
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

    protected void startImpl() {
        mainWindow().get().state().add(AppWindowState.OPENED);
    }

    private class ApplicationBuilderImpl implements ApplicationBuilder {

        private final WritablePValue<AppWindowBuilder> windowBuilder = Props.of("windowBuilder").valueOf(AppWindowBuilder.class, new DefaultAppWindowBuilder());

        public ApplicationBuilderImpl(DefaultApplication a) {
            PropertyVeto already_started_veto = new PropertyVeto() {
                @Override
                public void vetoableChange(PropertyEvent e) {
                    if (a.state().get().ordinal() >= AppState.STARTED.ordinal()) {
                        throw new IllegalArgumentException("Application is already started");
                    }
                }
            };
            windowBuilder.vetos().add(already_started_veto);
        }

        @Override
        public PValue<AppWindowBuilder> mainWindowBuilder() {
            return windowBuilder.readOnly();
        }
    }

    private static class WinPropertyVetoImpl implements PropertyVeto {

        private final AppWindow n;
        private final DefaultApplication a;

        public WinPropertyVetoImpl(DefaultApplication a, AppWindow n) {
            this.n = n;
            this.a = a;
        }

        @Override
        public void vetoableChange(PropertyEvent e) {
            AppWindowStateSet a1 = e.getOldValue();
            AppWindowStateSet a2 = e.getNewValue();
            if (a2.is(AppWindowState.CLOSING) && !a1.is(AppWindowState.CLOSING)) {
                DefaultAppEvent evt = new DefaultAppEvent(this.a, n);
                for (AppShutdownVeto v : this.a.shutdownVetos) {
                    v.vetoableChange(evt);
                }
            }
//            if (a.is(AppWindowState.CLOSED)) {
//                DefaultAppEvent evt = new DefaultAppEvent(this.a, n);
//                for (AppShutdownVeto v : this.a.shutdownVetos) {
//                    v.vetoableChange(evt);
//                }
//            }
        }
    }

    @Override
    public void runFront(Runnable run) {
        run = _catchError(run);
        if (SwingUtilities.isEventDispatchThread()) {
            run.run();
        } else {
            SwingUtilities.invokeLater(run);
        }
    }

    @Override
    public void runBack(Runnable run) {
        run = _catchError(run);
        if (SwingUtilities.isEventDispatchThread()) {
            //may be should use executor!
            new Thread(run).start();
        } else {
            run.run();
        }
    }

    @Override
    public AppErrors errors() {
        return errors;
    }

    private Runnable _catchError(Runnable run) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    run.run();
                } catch (Exception ex) {
                    errors().add(ex);
                }
            }
        };
    }

    @Override
    public AppComponentRendererFactory componentRendererFactory() {
        return componentRendererFactory;
    }

    /**
     * should be called when the window is destructed
     */
    private void onFreeWaiters() {
        for (Iterator<Semaphore> it = waitings.iterator(); it.hasNext();) {
            Semaphore waiting = it.next();
            waiting.release();
            it.remove();
        }
    }

    @Override
    public void waitFor() {
        try {
            Semaphore sem = new Semaphore(1);
            sem.acquire();
            waitings.add(sem);
            
            sem.acquire();
        } catch (InterruptedException ex) {
            //
        }
    }

}
