/**
 * ====================================================================
 * Echo : Simple Desktop Application Framework
 * <br>
 * Echo is a simple Desktop Application Framework witj productivity in mind.
 * Currently Echo has two ports : swing and javafx
 * <br>
 *
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
import net.thevpc.common.msg.StringMessage;
import net.thevpc.common.props.*;
import net.thevpc.common.props.impl.SimpleProperty;
import net.thevpc.echo.*;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.components.AppDock;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.api.tools.AppContainerModel;
import net.thevpc.echo.iconset.DefaultIconsets;
import net.thevpc.echo.iconset.IconSets;
import net.thevpc.echo.iconset.NoIconSet;
import net.thevpc.echo.impl.components.AppContainerBase;
import net.thevpc.echo.impl.components.AppContainerChildren;
import net.thevpc.echo.impl.components.Frame;
//import net.thevpc.echo.impl.components.Workspace;
import net.thevpc.echo.impl.tools.ContainerModel;
import net.thevpc.echo.props.AppProps;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;

/**
 *
 * @author vpc
 */
public abstract class AbstractApplication extends SimpleProperty implements Application {

    protected WritableValue<AppState> state = Props.of("state").valueOf(AppState.class, AppState.NONE);
    protected WritableStr name;
    protected DefaultAppMessages messages = new DefaultAppMessages(this);
    protected DefaultAppLogs logs = new DefaultAppLogs(this);
    protected WritableList<AppShutdownVeto> shutdownVetos = Props.of("shutdownVetos").listOf(AppShutdownVeto.class);
    protected IconSets iconSets = new DefaultIconsets("iconSets");
    protected I18n i18n = new DefaultI18n("application");
//    protected PropertyContainerSupport support = new PropertyContainerSupport("app", this);
    private AppContainer rootContainer;
    private AppHistory history = new DefaultAppUndoManager(this);
    private WritableString plaf = Props.of("plaf").stringOf(null);
    private WritableValue<AppFrame> mainFrame = Props.of("mainFrame").valueOf(AppFrame.class, null);
    private WritableValue<AppPropertiesTree> activeProperties = Props.of("activeProperties").valueOf(AppPropertiesTree.class, null);
    private WritableString currentWorkingDirectory = Props.of("currentWorkingDirectory").stringOf(null);
    protected AppErrors errors = new DefaultAppErrors(this);
    private List<Semaphore> waitings = new ArrayList<>();
//    private AppRootNode appRootNode;
//    protected BindingNodeFactory bindingNodeFactory;
    protected ApplicationStartupConfig applicationStartupConfig;
    private ApplicationToolkit toolkit;

    public AbstractApplication(ApplicationStartupConfig applicationStartupConfig) {
        super("app");
        this.name = AppProps.of("name",this).strOf(Str.of(""));
        this.applicationStartupConfig = applicationStartupConfig;
        ((DefaultApplicationStartupConfig) applicationStartupConfig).prepare(this);
        iconSets().add(new NoIconSet("no-icon"));
        i18n().bundles().add("net.thevpc.echo.app");
        i18n().bundles().add("net.thevpc.echo.app-locale-independent");
        rootContainer = new AppRootContainerImpl(new ContainerModel(this));
        mainFrame.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                AppFrame o = event.getOldValue();
                if (o != null) {
                    o.model().state().vetos().removeIf(x -> x instanceof WinPropertyVetoImpl);
                    rootContainer.children().remove(o.path().get());
                }
                AppFrame n = event.getNewValue();
                if (n != null) {
                    n.model().state().vetos().add(new WinPropertyVetoImpl(AbstractApplication.this, n));
                    rootContainer.children().add(n);
                }
            }

        });
        state.vetos().add(new PropertyVeto() {
            @Override
            public void vetoableChange(PropertyEvent e) {
                AppState a = e.getNewValue();
//                if (a == AppState.CLOSING) {
//                    DefaultAppEvent evt = new DefaultAppEvent(SwingApplication.this, state);
//                    for (AppShutdownVeto v : shutdownVetos) {
//                        v.vetoableChange(evt);
//                    }
//                }
//                if (a == AppState.CLOSED) {
//                    DefaultAppEvent evt = new DefaultAppEvent(SwingApplication.this, state);
//                    for (AppShutdownVeto v : shutdownVetos) {
//                        v.vetoableChange(evt);
//                    }
//                }
            }
        });
        state.listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                if (event.getNewValue() == AppState.STARTING) {
                    prepareFactories();
                }
                if (event.getNewValue() == AppState.CLOSING) {
                    AppFrame mw = mainFrame.get();
                    if (!mw.model().state().is(AppWindowState.CLOSING)
                            && !mw.model().state().is(AppWindowState.CLOSED)) {
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
        plaf().listeners().add(new PropertyListener() {
            @Override
            public void propertyUpdated(PropertyEvent event) {
                String nv = event.getNewValue();
                if (nv != null) {
                    toolkit.applyPlaf(nv);
                }
            }
        });
        ((DefaultIconsets) iconSets).setBuilderSupplier(() -> new DefaultIconSetSupplier(this));

        propagateEvents(state,name,mainFrame,messages,
                logs,iconSets,i18n,plaf,activeProperties,
                currentWorkingDirectory,errors,rootContainer
        );
    }

    protected void setToolkit(ApplicationToolkit toolkit) {
        this.toolkit = toolkit;
    }

    @Override
    public ApplicationStartupConfig startupConfig() {
        return applicationStartupConfig;
    }

    protected void prepareFactories() {

//        AppWorkspace ws = mainFrame().get().workspace().get();
//        if(ws==null){
//            ws=new Workspace(AbstractApplication.this);
//            mainFrame().get().workspace().set(ws);
//        }
//    private InternalWindowsHelper internalFramesHelper = null;

        if (startupConfig()
                .enableQuit().get()) {
            Applications.Helper.addQuitAction(this);
        }

        Applications.Helper.addViewToolActions(this);

        if (startupConfig()
                .enablePlaf().get()) {
            Applications.Helper.addViewPlafActions(this);
        }

        if (startupConfig()
                .enableIcons().get()) {
            Applications.Helper.addViewIconActions(this);
        }

        if (startupConfig()
                .enableAppearance().get()) {
            Applications.Helper.addViewAppearanceActions(this);
        }
        String[] el = startupConfig().enableLocales().get();
        if (el != null && el.length
                > 0) {
            Applications.Helper.addViewLocaleActions(this, Arrays.stream(el).map(x -> new Locale(x)).toArray(Locale[]::new));
        }
        Applications.Helper.addWindowsActions(this);
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
        private final AbstractApplication a;

        public WinPropertyVetoImpl(AbstractApplication a, AppFrame n) {
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

    public WritableString currentWorkingDirectory() {
        return currentWorkingDirectory;
    }

    @Override
    public AppContainer<AppComponentModel, AppComponent> container() {
        return rootContainer;
    }

    @Override
    public AppContainerChildren<AppComponentModel, AppComponent> components() {
        return container().children();
    }

    public WritableString plaf() {
        return plaf;
    }

    public void setPlaf(WritableString plaf) {
        this.plaf = plaf;
    }

    @Override
    public WritableValue<AppPropertiesTree> activeProperties() {
        return activeProperties;
    }

    @Override
    public WritableList<AppShutdownVeto> shutdownVetos() {
        return shutdownVetos;
    }

    @Override
    public IconSets iconSets() {
        return iconSets;
    }

    @Override
    public I18n i18n() {
        return i18n;
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
    public AppMessages messages() {
        return messages;
    }

    @Override
    public AppLogs logs() {
        return logs;
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
        AppFrame frame =new Frame(this);frame.model().title().set(Str.of("Application"));
        mainFrame().set(frame);
        frame.model().state().listeners().add(event -> {
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
                } else if (s == AppState.CLOSED) {
                    onFreeWaiters();
                }
            }

            @Override
            public int order() {
                return Integer.MAX_VALUE;
            }

        });
        components().add(frame,"mainFrame");
    }
//
//    @Override
//    public AppPropertyBinding[] getProperties() {
//        return support.getProperties();
//    }
//
//    @Override
//    public PropertyListeners listeners() {
//        return support.listeners();
//    }

//    protected void addRootContainer(AppContainer c) {
//        if (c != null) {
//            rootContainers.add(c);
//            tools.addRootContainer(c);
//        }
//    }
//
//    protected void removeRootContainer(AppContainer c) {
//        if (c != null) {
//            rootContainers.remove(c);
//            tools.removeRootContainer(c);
//        }
//    }
    protected void startImpl() {
        mainFrame().get().model().state().add(AppWindowState.OPENED);
    }

    @Override
    public AppErrors errors() {
        return errors;
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

//    public BindingNodeFactory getBindingNodeFactory() {
//        return bindingNodeFactory;
//    }
    @Override
    public ApplicationToolkit toolkit() {
        return toolkit;
    }

    public void runUI(Runnable run) {
        toolkit.runUI(wrapSafeRunnable(run));
    }

    @Override
    public void runWorker(Runnable run) {
        toolkit.runWorker(wrapSafeRunnable(run));
    }
    

    private static class AppRootContainerPeer implements AppComponentPeer {
        @Override
        public void install(AppComponent comp) {

        }

        @Override
        public Object toolkitComponent() {
            return null;
        }
    }
    private static class AppRootContainerImpl extends AppContainerBase<AppComponentModel,AppComponent> {
        public AppRootContainerImpl(ContainerModel rootFolder) {
            super(rootFolder, AppComponentModel.class, AppComponent.class,AppRootContainerPeer.class,
                    AppContainerModel.class, AppComponent.class
                    );
            peer=new AppRootContainerPeer();
        }
    }
    
}
