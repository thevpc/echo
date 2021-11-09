/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 * <p>
 * Copyright [2021] [thevpc] Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 * <br> ====================================================================
 */


package net.thevpc.echo.impl;

import net.thevpc.common.i18n.DefaultI18n;
import net.thevpc.common.i18n.I18n;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.msg.StringMessage;
import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.PropertyBase;
import net.thevpc.echo.*;
import net.thevpc.echo.api.*;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.iconset.DefaultIconsets;
import net.thevpc.echo.iconset.IconSets;
import net.thevpc.echo.iconset.NoIconSet;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.spi.peers.AppComponentPeer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author thevpc
 */
public class DefaultApplication extends PropertyBase implements Application, ChildPropertyResolver {

    protected WritableValue<AppState> state = Props.of("state").valueOf(AppState.class, AppState.NONE);
    protected DefaultApplicationLoader applicationLoader;
    protected WritableStr name;
    protected DefaultAppMessages messages = new DefaultAppMessages(this);
    protected DefaultAppLogs logs = new DefaultAppLogs(this);
    protected WritableList<AppShutdownVeto> shutdownVetos = Props.of("shutdownVetos").listOf(AppShutdownVeto.class);
    protected IconSets iconSets = new DefaultIconsets("iconSets");
    protected I18n i18n = new DefaultI18n("application");
    protected AppErrors errors = new DefaultAppErrors(this);
    //    private AppRootNode appRootNode;
//    protected BindingNodeFactory bindingNodeFactory;
    protected ApplicationStartupConfig applicationStartupConfig;
    protected ClipboardManager clipboardManager;
    //    protected PropertyContainerSupport support = new PropertyContainerSupport("app", this);
    private AppContainer rootContainer;
    private AppHistory history = new DefaultAppUndoManager(this);
    private WritableString plaf = Props.of("plaf").stringOf(null);
    private WritableValue<AppFrame> mainFrame = Props.of("mainFrame").valueOf(AppFrame.class, null);
    private WritableValue<ExecutorService> executorService = Props.of("executorService").valueOf(ExecutorService.class, null);
    private WritableValue<AppPropertiesTree> activeProperties = Props.of("activeProperties").valueOf(AppPropertiesTree.class, null);
    private WritableString currentWorkingDirectory = Props.of("currentWorkingDirectory").stringOf(null);
    private WritableBoolean hideDisabled = Props.of("hideDisabled").booleanOf(false);
    private WritableList<AppFont> fonts = Props.of("fonts").listOf(AppFont.class);
    private List<Semaphore> waitings = new ArrayList<>();
    private ApplicationToolkit toolkit;
    private PrinterService printerService;

    public DefaultApplication() {
        this(null);
    }

    public DefaultApplication(String toolkitId) {
        this(toolkitId, new DefaultApplicationStartupConfig());
    }

    public DefaultApplication(String toolkitId, ApplicationStartupConfig applicationStartupConfig) {
        super("app");
        this.applicationLoader = new DefaultApplicationLoader("loader", this);
        clipboardManager = new DefaultClipboardManager(this);
        ServiceLoader<ApplicationToolkitFactory> psl = ServiceLoader.load(ApplicationToolkitFactory.class, Thread.currentThread().getContextClassLoader());
        SupportSupplier<ApplicationToolkit> best = null;
        int bestLvl = -1;
        List<ApplicationToolkitFactory> tested = new ArrayList<>();
        for (ApplicationToolkitFactory cc : psl) {
            tested.add(cc);
            SupportSupplier<ApplicationToolkit> toolkit = cc.createToolkit(toolkitId);
            if (toolkit != null) {
                int lvl = toolkit.getSupportLevel();
                if (lvl > 0 && lvl > bestLvl) {
                    bestLvl = lvl;
                    best = toolkit;
                }
            }
        }
        if (best != null) {
            toolkit = best.get();
        }
        if (toolkit == null) {
            throw new IllegalArgumentException("missing toolkit factory for " + toolkitId + ". all of " +
                    tested.stream().map(x -> x.getClass().getName()).collect(Collectors.toList())
                    + " do not match");
        }
        ((AbstractApplicationToolkit) toolkit).initialize(this);
        this.name = AppProps.of("name", this).strOf(Str.of(""));
        this.applicationStartupConfig = applicationStartupConfig;
        ((DefaultApplicationStartupConfig) applicationStartupConfig).prepare(this);
        iconSets().add(new NoIconSet("no-icon"));
        i18n().bundles().add("net.thevpc.echo.app");
        i18n().bundles().add("net.thevpc.echo.app-locale-independent");
        rootContainer = new AppRootContainerImpl(this);
        mainFrame.onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppFrame o = event.oldValue();
                if (o != null) {
                    o.state().vetos().removeIf(x -> x instanceof WinPropertyVetoImpl);
                    rootContainer.children().remove(o.path().get());
                }
                AppFrame n = event.newValue();
                if (n != null) {
                    n.state().vetos().add(new WinPropertyVetoImpl(DefaultApplication.this, n));
                    rootContainer.children().add(n, "mainFrame");
                }
            }

        });
        state.vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent e) {
                AppState a = e.newValue();
//                if (a == AppState.CLOSING) {
//                    DefaultAppEvent evt = new DefaultAppEvent(Application.this, state);
//                    for (AppShutdownVeto v : shutdownVetos) {
//                        v.vetoableChange(evt);
//                    }
//                }
//                if (a == AppState.CLOSED) {
//                    DefaultAppEvent evt = new DefaultAppEvent(Application.this, state);
//                    for (AppShutdownVeto v : shutdownVetos) {
//                        v.vetoableChange(evt);
//                    }
//                }
            }
        });
        state.onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                if (event.newValue() == AppState.STARTING) {
                    prepareFactories();
                }
                if (event.newValue() == AppState.CLOSING) {
                    AppFrame mw = mainFrame.get();
                    if (!mw.state().is(WindowState.CLOSING)
                            && !mw.state().is(WindowState.CLOSED)) {
                        mw.close();
                    }
                    state.set(AppState.CLOSED);
                }
            }

            @Override
            public int order() {
                return Integer.MAX_VALUE;
            }
        });
        plaf().onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                String nv = event.newValue();
                if (nv != null) {
                    toolkit.applyPlaf(nv);
                }
            }
        });
        ((DefaultIconsets) iconSets).setBuilderSupplier(() -> new DefaultIconSetSupplier(this));

        mainFrame().onChange(e -> {
            AppFrame ov = e.oldValue();
            if (ov != null) {
                ov.state().events().remove(this::closeAppOnCloseFrame);
                if (components().contains(ov)) {
                    components().remove(ov);
                }
            }
            AppFrame nv = e.newValue();
            if (nv != null) {
                nv.state().onChange(this::closeAppOnCloseFrame);
                if (!components().contains(nv)) {
                    components().add(nv);
                }
            }
        });
        state.onChange(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppState s = (AppState) event.newValue();
                if (s == AppState.CLOSING) {
                    state.set(AppState.CLOSED);
                } else if (s == AppState.CLOSED) {
                    onFreeWaiters();
                }
            }

            @Override
            public int order() {
                return Integer.MAX_VALUE;
            }

        });

        components().onChange(e -> {
            AppComponent c = e.newValue();
            if (c != null) {
                Path cp = c.path().get();
                switch (cp.name()) {
                    case "mainFrame": {
                        if (mainFrame().get() != c) {
                            mainFrame().set((AppFrame) c);
                        }
                        break;
                    }
                }
            }
        });
        propagateEvents(applicationLoader, state, name, mainFrame, messages,
                logs, iconSets, i18n, plaf, activeProperties,
                currentWorkingDirectory, errors, rootContainer,fonts
        );
        fonts().clear();
        fonts().setAll(toolkit().availablefonts());
    }

    @Override
    public WritableList<AppFont> fonts() {
        return fonts;
    }
    
    

    protected void setToolkit(ApplicationToolkit toolkit) {
        this.toolkit = toolkit;
    }

    protected void prepareFactories() {

//        AppWorkspace ws = mainFrame().get().workspace().get();
//        if(ws==null){
//            ws=new Workspace(DefaultApplication.this);
//            mainFrame().get().workspace().set(ws);
//        }
//    private InternalWindowsHelper internalFramesHelper = null;
        //((Frame)mainFrame()).addDefaultMenus();
    }

    public void setPlaf(WritableString plaf) {
        this.plaf = plaf;
    }

    //    protected void setStatusBar(AppStatusBarGroup statusBar) {
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
//    protected void setToolBar(AppToolBarGroup toolBar) {
//        if (state().get().ordinal() >= AppState.STARTED.ordinal()) {
//            throw new IllegalArgumentException("Already started");
//        }
//        removeRootContainer(this.toolBar);
//        this.toolBar = toolBar;
//        addRootContainer(this.toolBar);
//    }
    protected void initImpl() {
//        AppFrame mainFrameInstance = new Frame("mainFrame",this);
//        mainFrameInstance.title().set(Str.of("Application"));
//        mainFrameInstance.path().set(Path.of("/mainFrame"));
//        mainFrame().set(mainFrameInstance);
//        components().add(mainFrameInstance, "mainFrame");
    }

    //    protected void addRootContainer(AppContainer c) {
//        if (c != null) {
//            rootContainers.add(c);
//            model.addRootContainer(c);
//        }
//    }
//
//    protected void removeRootContainer(AppContainer c) {
//        if (c != null) {
//            rootContainers.remove(c);
//            model.removeRootContainer(c);
//        }
//    }
    protected void startImpl() {
        if (mainFrame().get() != null) {
            mainFrame().get().open();
        }
    }

    protected Runnable wrapSafeRunnable(Runnable run) {
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

    /**
     * should be called when the window is destructed
     */
    private void onFreeWaiters() {
        for (Iterator<Semaphore> it = waitings.iterator(); it.hasNext(); ) {
            Semaphore waiting = it.next();
            waiting.release();
            it.remove();
        }
    }

    @Override
    public ClipboardManager clipboard() {
        return clipboardManager;
    }

    @Override
    public ApplicationLoader loader() {
        return applicationLoader;
    }

    @Override
    public WritableStr name() {
        return name;
    }

    @Override
    public ObservableValue<AppState> state() {
        return state.readOnly();
    }

    @Override
    public ApplicationStartupConfig startupConfig() {
        return applicationStartupConfig;
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
    public WritableList<AppShutdownVeto> shutdownVetos() {
        return shutdownVetos;
    }

    @Override
    public WritableValue<ExecutorService> executorService() {
        return executorService;
    }

    @Override
    public Application shutdown() {
        switch (state.get()) {
            case CLOSING:
            case CLOSED: {
                break;
            }
            default: {
                state.set(AppState.CLOSING);
                break;
            }
        }
        return this;
    }

    @Override
    public WritableValue<AppFrame> mainFrame() {
        return mainFrame;
    }

    //    @Override
//    public AppNode rootNode() {
//        return appRootNode;
//    }
    @Override
    public AppContainer root() {
        return rootContainer;
    }

    @Override
    public AppHistory history() {
        return history;
    }

    @Override
    public I18n i18n() {
        return i18n;
    }

    @Override
    public AppMessages messages() {
        return messages;
    }

    @Override
    public AppLogs logs() {
        return logs;
    }

    @Override
    public IconSets iconSets() {
        return iconSets;
    }

    @Override
    public WritableValue<AppPropertiesTree> activeProperties() {
        return activeProperties;
    }
//
//    @Override
//    public AppPropertyBinding[] getProperties() {
//        return support.getProperties();
//    }
//
//    @Override
//    public PropertyListeners events() {
//        return support.events();
//    }

    public void runUI(Runnable run) {
        toolkit.runUI(wrapSafeRunnable(run));
    }

    @Override
    public void runWorker(Runnable run) {
        toolkit.runWorker(wrapSafeRunnable(run));
    }

    @Override
    public AppErrors errors() {
        return errors;
    }

    public WritableString currentWorkingDirectory() {
        return currentWorkingDirectory;
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

    //    public BindingNodeFactory getBindingNodeFactory() {
//        return bindingNodeFactory;
//    }
    @Override
    public ApplicationToolkit toolkit() {
        return toolkit;
    }

    public WritableString plaf() {
        return plaf;
    }

    @Override
    public AppContainer<AppComponent> container() {
        return rootContainer;
    }

    @Override
    public AppContainerChildren<AppComponent> components() {
        return container().children();
    }

    @Override
    public WritableBoolean hideDisabled() {
        return hideDisabled;
    }

    private void closeAppOnCloseFrame(PropertyEvent event) {
        WindowStateSet s = event.newValue();
        if (s.is(WindowState.CLOSING)) {
            if (state.get().ordinal() < AppState.CLOSING.ordinal()) {
                state.set(AppState.CLOSING);
            }
        } else if (s.is(WindowState.CLOSED)) {
            if (state.get().ordinal() < AppState.CLOSING.ordinal()) {
                state.set(AppState.CLOSING);
            } else {
                state.set(AppState.CLOSED);
            }
        }
    }

    @Override
    public Property resolveChildProperty(String name) {
        for (AppComponent component : components()) {
            if (component.path().get().name().equals(name)) {
                return component;
            }
            if (component.id().equals(name)) {
                return component;
            }
        }
        return null;
    }

    @Override
    public PrinterService printerService() {
        if(printerService==null){
            printerService=toolkit().printerService();
        }
        return printerService;
    }

    //    private class AppRootNode implements AppNode {
//        private WritableValue<Path> root=Props.of("root").valueOf(Path.class,Path.of());
//        public AppRootNode() {
//        }
//
//        @Override
//        public AppControl getComponent() {
//            return null;
//        }
//
//        @Override
//        public int getOrder() {
//            return 0;
//        }
//
//        @Override
//        public ObservableValue<Path> path() {
//            return root.readOnly();
//        }
//
//        @Override
//        public AppNode[] getChildren() {
//            return rootContainers
//                    .stream().map(x -> x.rootNode())
//                    .toArray(AppNode[]::new);
//        }
//
//        @Override
//        public AppNode get(Path path) {
//            for (AppContainer rootContainer : rootContainers) {
//                if (path.startsWith(rootContainer.rootNode().path().get())) {
//                    path = path.skipFirst();
//                    return rootContainer.rootNode().get(path);
//                }
//            }
//            return null;
//        }
//    }
    private static class WinPropertyVetoImpl implements PropertyVeto {

        private final AppFrame n;
        private final DefaultApplication a;

        public WinPropertyVetoImpl(DefaultApplication a, AppFrame n) {
            this.n = n;
            this.a = a;
        }

        @Override
        public void vetoableChange(PropertyEvent e) {
            WindowStateSet a1 = e.oldValue();
            WindowStateSet a2 = e.newValue();
            if (a2.is(WindowState.CLOSING) && !a1.is(WindowState.CLOSING)) {
                DefaultAppEvent evt = new DefaultAppEvent(this.a, n);
                for (AppShutdownVeto v : this.a.shutdownVetos) {
                    v.vetoableChange(evt);
                }
            }
//            if (a.is(WindowState.CLOSED)) {
//                DefaultAppEvent evt = new DefaultAppEvent(this.a, n);
//                for (AppShutdownVeto v : this.a.shutdownVetos) {
//                    v.vetoableChange(evt);
//                }
//            }
        }
    }

    private static class AppRootContainerPeer implements AppComponentPeer {
        @Override
        public void install(AppComponent comp) {

        }

        @Override
        public Object toolkitComponent() {
            return null;
        }

        @Override
        public void requestFocus() {
            //
        }
    }

    private static class AppRootContainerImpl extends ContainerBase<AppComponent> {
        public AppRootContainerImpl(Application app) {
            super(".<ROOT>", app, AppComponent.class, AppRootContainerPeer.class,
                    AppComponent.class
            );
            path().set(Path.root());
            peer = new AppRootContainerPeer();
        }

        @Override
        public AppComponent createPreferredChild(String name, Path absolutePath) {
            return new Frame(name, app());
        }
    }
}
