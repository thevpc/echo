package net.thevpc.echo.impl;

import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.*;
import net.thevpc.echo.api.AppComponentPeerFactory;
import net.thevpc.echo.api.ApplicationToolkit;
import net.thevpc.echo.spi.ApplicationToolkitConfigurator;
import net.thevpc.echo.api.SupportSupplier;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.spi.peers.AppComponentPeer;
import net.thevpc.echo.impl.util._ClassMap;

import java.util.*;

public abstract class AbstractApplicationToolkit implements ApplicationToolkit {

    protected String id;
    protected Application app;
    private WritableValue<AppComponent> focusOwner= Props.of("focusOwner").valueOf(AppComponent.class);
    /**
     * used to map a peer instance for a given component
     */
//    private _ClassMap<List<AppComponentPeerFactory>> componentToPeerMap = (_ClassMap) new _ClassMap<AppComponentPeerFactory>(AppComponent.class, (Class) List.class);

    private _ClassMap<List<AppComponentPeerFactory>> peerFactories = (_ClassMap) new _ClassMap<AppComponentPeerFactory>(AppComponentPeer.class, (Class) List.class);

    /**
     * used to map a component instance to a given tool
     */
//    private _ClassMap<List<AppComponentFactory>> toolToComponent = (_ClassMap) new _ClassMap<AppComponentPeerFactory>(AppComponentModel.class, (Class) List.class);

    /**
     * used to create a map a default component  for a given Tool
     */
//    private _ClassMap<Function<AppComponentRendererContext, AppComponent>> componentsDefaultFactories = (_ClassMap) new _ClassMap<AppComponentPeerFactory>(AppComponent.class, (Class) Function.class);

    public AbstractApplicationToolkit(String id) {
        this.id=id;
    }

    @Override
    public String id() {
        return id;
    }

    //    public AppComponent createDefaultComponent(AppComponentRendererContext context) {
//        if(context==null || context.componentType()==null){
//            throw new IllegalArgumentException("null componentType");
//        }
//        Function<AppComponentRendererContext, AppComponent> sl = componentsDefaultFactories.get(context.componentType());
//        if (sl == null) {
//            throw new IllegalArgumentException("unable to create default component for " + context.componentType().getName());
//        }
//        AppComponent a = sl.apply(context);
//        if (a == null) {
//            throw new IllegalArgumentException("unable to create default component for tool of type " + context.componentType().getName());
//        }
//        a.setOptions(context.options());
//        a.path().set(context.path());
//        return a;
//    }

//    public AppComponent createComponent(AppComponentModel model, AppComponent parent, String name, AppComponentOptions options) {
//        Path path = name==null? Path.of(): Path.of(name);
//        if (parent != null) {
//            path = parent.path().get().child(path);
//        }
//        AppComponentRendererContext context = new AppComponentRendererContext(
//                options==null?null:options.componentType(),
//                parent,
//                model,
//                app,
//                path, name, options
//        );
//        Class<? extends AppComponent> c = context.componentType();
//        if(c!=null){
//            Function<AppComponentRendererContext, AppComponent> a = componentsDefaultFactories.get(c);
//            if(a!=null){
//                AppComponent q = a.apply(context);
//                q.path().set(path);
//                return q;
//            }
//        }
//        List<AppComponentFactory> sl = toolToComponent.get(model.getClass());
//        SupportSupplier<AppComponent> best = null;
//        if (sl != null) {
//            int bestLvl = -1;
//            for (AppComponentFactory w : sl) {
//                //path=window.path().get().append("workspace") //TODO, fix me
//                SupportSupplier<AppComponent> curr = w.createComponent(context);
//                if (curr != null) {
//                    int lvl = curr.getSupportLevel();
//                    if (lvl > 0) {
//                        if (best == null || bestLvl < lvl) {
//                            best = curr;
//                            bestLvl = lvl;
//                        }
//                    }
//                }
//            }
//            if (best != null) {
//                AppComponent q = best.get();
//                q.path().set(path);
//                return q;
//            }
//        }
//        throw new IllegalArgumentException("unable to create component for model of type " + model.getClass().getName()
//                +" and parent of type "
//                + (parent==null?"null":parent.getClass().getName())
//        );
//    }

//    @Override
//    public AppComponentPeer createComponentPeer(AppComponent component) {
//        List<AppComponentPeerFactory> sl = componentToPeerMap.get(component.getClass());
//        SupportSupplier<AppComponentPeer> best = null;
//        if (sl != null) {
//            int bestLvl = -1;
//            for (AppComponentPeerFactory w : sl) {
//                //path=window.path().get().append("workspace") //TODO, fix me
//                SupportSupplier<AppComponentPeer> curr = w.createComponentPeer(component);
//                if (curr != null) {
//                    int lvl = curr.getSupportLevel();
//                    if (lvl > 0) {
//                        if (best == null || bestLvl < lvl) {
//                            best = curr;
//                            bestLvl = lvl;
//                        }
//                    }
//                }
//            }
//            if (best != null) {
//                return best.get();
//            }
//        }
//        throw new IllegalArgumentException("unable to create peer for component of type " + component.getClass().getName());
//    }

//    private void addPeerFactory(AppComponentPeerFactory appComponentPeerFactory) {
//        for (Class supportedComponentType : appComponentPeerFactory.getSupportedComponentTypes()) {
//            List<AppComponentPeerFactory> a = componentToPeerMap.getExact(supportedComponentType);
//            if (a == null) {
//                a = new ArrayList<>();
//                componentToPeerMap.put(supportedComponentType, a);
//            }
//            a.add(appComponentPeerFactory);
//        }
//    }

//    private void addComponentFactory(AppComponentFactory factory) {
//        for (Class supportedComponentType : factory.getSupportedModelTypes()) {
//            List<AppComponentFactory> a = toolToComponent.getExact(supportedComponentType);
//            if (a == null) {
//                a = new ArrayList<>();
//                toolToComponent.put(supportedComponentType, a);
//            }
//            a.add(factory);
//        }
//    }


//    public <T extends AppComponent> void addPeerFactory(Class<T> type, Class<? extends AppComponentPeer> peer) {
//        addPeerFactory(new AppComponentPeerFactory() {
//            @Override
//            public List<Class> getSupportedComponentTypes() {
//                return Arrays.asList(type);
//            }
//
//            @Override
//            public SupportSupplier<AppComponentPeer> createComponentPeer(AppComponent component) {
//                return new SupportSupplier<AppComponentPeer>() {
//                    @Override
//                    public int getSupportLevel() {
//                        return 1;
//                    }
//
//                    @Override
//                    public AppComponentPeer get() {
//                        try {
//                            return peer.getConstructor().newInstance();
//                        } catch (Exception e) {
//                            throw UncheckedException.wrap(e);
//                        }
//                    }
//                };
//            }
//        });
//    }


//    public <T extends AppComponent> void addComponentDefaultFactory(Class<T> type, Function<AppComponentRendererContext, AppComponent> factory) {
//        componentsDefaultFactories.put(type, factory);
//    }

//    public <T extends AppComponentModel, C extends AppComponent> void addComponentTypeByToolFactory(Class<T> modelType, Class<C> componentType) {
//        addComponentFactory(new AppComponentFactory() {
//            @Override
//            public List<Class> getSupportedModelTypes() {
//                return Arrays.asList(modelType);
//            }
//
//            @Override
//            public SupportSupplier<AppComponent> createComponent(AppComponentRendererContext context) {
//                return new SupportSupplier<AppComponent>() {
//                    @Override
//                    public int getSupportLevel() {
//                        return 1;
//                    }
//
//                    @Override
//                    public AppComponent get() {
//                        AppComponentOptions options = context.options();
//                        return createDefaultComponent(new AppComponentRendererContext(
//                                componentType,
//                                context.parent(), context.tool(), context.app(),context.path(),
//                                context.name(), options));
//                    }
//                };
//            }
//        });
//    }

//    public <T extends AppComponentModel, C extends AppComponent> void addComponentTypeByToolFactory(
//            Class<T> modelType,
//            Function<AppComponentRendererContext, Class<C>> componentType) {
//        addComponentFactory(new AppComponentFactory() {
//            @Override
//            public List<Class> getSupportedModelTypes() {
//                return Arrays.asList(modelType);
//            }
//
//            @Override
//            public SupportSupplier<AppComponent> createComponent(AppComponentRendererContext context) {
//                return new SupportSupplier<AppComponent>() {
//                    @Override
//                    public int getSupportLevel() {
//                        return 1;
//                    }
//
//                    @Override
//                    public AppComponent get() {
//                        Class<? extends AppComponent> c = c = componentType.apply(context);
//                        return createDefaultComponent(
//                                new AppComponentRendererContext(
//                                c, context.parent(), context.tool(), context.app(), context.path(),
//                                context.name(), context.options()
//                        )
//                        );
//                    }
//                };
//            }
//        });
//    }

//    public <T extends AppComponentModel> void addComponentFactory(Class<T> type, Function<AppComponentRendererContext, AppComponent> component) {
//        addComponentFactory(new AppComponentFactory() {
//            @Override
//            public List<Class> getSupportedModelTypes() {
//                return Arrays.asList(type);
//            }
//
//            @Override
//            public SupportSupplier<AppComponent> createComponent(AppComponentRendererContext context) {
//                return new SupportSupplier<AppComponent>() {
//                    @Override
//                    public int getSupportLevel() {
//                        return 1;
//                    }
//
//                    @Override
//                    public AppComponent get() {
//                        return component.apply(context);
//                    }
//                };
//            }
//        });
//    }

    public <T extends AppComponentPeer> void addPeerFactory(
            Class<T> peerInterface,Class<? extends T> impl) {
        addPeerFactory(peerInterface,impl,1);
    }
    public <T extends AppComponentPeer> void addPeerFactory(
            Class<T> peerInterface,Class<? extends T> impl,int supportLevel) {
        addPeerFactory(peerInterface, new AppComponentPeerFactory() {
            @Override
            public SupportSupplier<AppComponentPeer> createComponentPeer(AppComponent component) {
                return new SupportSupplier<AppComponentPeer>() {
                    @Override
                    public int getSupportLevel() {
                        return supportLevel;
                    }

                    @Override
                    public AppComponentPeer get() {
                        try {
                            return impl.getConstructor().newInstance();
                        } catch (Exception e) {
                            throw new IllegalArgumentException("Unable to create instance of type "+impl);
                        }
                    }
                };
            }
        });
    }

    public void addPeerFactory(Class<? extends AppComponentPeer> peerInterface,AppComponentPeerFactory appComponentPeerFactory) {
        List<AppComponentPeerFactory> a = peerFactories.getExact(peerInterface);
        if (a == null) {
            a = new ArrayList<>();
            peerFactories.put(peerInterface, a);
        }
        a.add(appComponentPeerFactory);
    }

    @Override
    public AppComponentPeer createComponentPeer(AppComponent component) {
        List<AppComponentPeerFactory> sl = peerFactories.get(component.peerType());
        SupportSupplier<AppComponentPeer> best = null;
        if (sl != null) {
            int bestLvl = -1;
            for (AppComponentPeerFactory w : sl) {
                //path=window.path().get().append("workspace") //TODO, fix me
                SupportSupplier<AppComponentPeer> curr = w.createComponentPeer(component);
                if (curr != null) {
                    int lvl = curr.getSupportLevel();
                    if (lvl > 0) {
                        if (best == null || bestLvl < lvl) {
                            best = curr;
                            bestLvl = lvl;
                        }
                    }
                }
            }
            if (best != null) {
                return best.get();
            }
        }
        throw new IllegalArgumentException("unable to create peer for component of type "
                + component.getClass().getName()
                +" with expected peerType "+component.peerType());
    }

    public void initialize(Application app){
        this.app=app;
        ServiceLoader<ApplicationToolkitConfigurator> psl = ServiceLoader.load(ApplicationToolkitConfigurator.class, Thread.currentThread().getContextClassLoader());
        for (ApplicationToolkitConfigurator cc : psl) {
            cc.configure(this);
        }
    }

    @Override
    public ObservableValue<AppComponent> focusOwner() {
        return focusOwner;
    }
}
