package net.thevpc.echo.impl;

import net.thevpc.echo.*;
import net.thevpc.common.props.Path;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.*;
import net.thevpc.echo.impl.components.*;
import net.thevpc.echo.impl.components.Calendar;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;

public abstract class AbstractApplicationToolkit implements ApplicationToolkit {

    protected Application app;
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

    public AbstractApplicationToolkit(Application application) {
        this.app = application;
//        ServiceLoader<AppComponentFactory> csl = ServiceLoader.load(AppComponentFactory.class, Thread.currentThread().getContextClassLoader());
//        for (AppComponentFactory appComponentPeerFactory : csl) {
//            addComponentFactory(appComponentPeerFactory);
//        }

//        ServiceLoader<AppComponentPeerFactory> psl = ServiceLoader.load(AppComponentPeerFactory.class, Thread.currentThread().getContextClassLoader());
//        for (AppComponentPeerFactory appComponentPeerFactory : psl) {
//            addPeerFactory(appComponentPeerFactory);
//        }
        ServiceLoader<ApplicationToolkitConfigurator> psl = ServiceLoader.load(ApplicationToolkitConfigurator.class, Thread.currentThread().getContextClassLoader());
        for (ApplicationToolkitConfigurator cc : psl) {
            cc.configure(this);
        }

//        addComponentDefaultFactory(AppButton.class, (c) -> new Button((AppToolButtonModel) c.tool()));
//        addComponentDefaultFactory(AppFrame.class, (c) -> new Frame((AppFrameModel) c.tool()));
//        addComponentDefaultFactory(AppAlert.class, (c) -> new Alert((AppAlertModel) c.tool()));
//        addComponentDefaultFactory(AppFileChooser.class, (c) -> new FileChooser((AppFileChooserModel) c.tool()));
//        addComponentDefaultFactory(AppSeparator.class, (c) -> new Separator((AppSeparatorModel) c.tool()));
//        addComponentDefaultFactory(AppSpacer.class, (c) -> new Spacer((AppSpacerModel) c.tool()));
//        addComponentDefaultFactory(AppRadioButton.class, (c) -> new RadioButton((AppToggleModel) c.tool()));
//        addComponentDefaultFactory(AppCheckBox.class, (c) -> new CheckBox((AppToggleModel) c.tool()));
//        addComponentDefaultFactory(AppToggle.class, (c) -> new Toggle((AppToggleModel) c.tool()));
//        addComponentDefaultFactory(AppComboBox.class, (c) -> new ComboBox((AppChoiceModel) c.tool()));
//        addComponentDefaultFactory(AppChoiceList.class, (c) -> new ChoiceList((AppChoiceModel) c.tool()));
//        addComponentDefaultFactory(AppCalendar.class, (c) -> new Calendar((AppCalendarModel) c.tool()));
//        addComponentDefaultFactory(AppFontChooser.class, (c) -> new FontChooser((AppFontChooserModel) c.tool()));
//        addComponentDefaultFactory(AppColorChooser.class, (c) -> new ColorChooser((AppColorChooserModel) c.tool()));
//        addComponentDefaultFactory(AppNumberField.class, (c) -> new NumberField((AppNumberFieldModel) c.tool()));
//        addComponentDefaultFactory(AppTemporalField.class, (c) -> new TemporalField((AppTemporalFieldModel) c.tool()));
//        addComponentDefaultFactory(AppTextField.class, (c) -> new TextField((AppTextModel) c.tool()));
//        addComponentDefaultFactory(AppTextArea.class, (c) -> new TextArea((AppTextModel) c.tool()));
//        addComponentDefaultFactory(AppTree.class, (c) -> new Tree((AppTreeModel) c.tool()));
//        addComponentDefaultFactory(AppTable.class, (c) -> new Table((AppTableModel) c.tool()));
//        addComponentDefaultFactory(AppMenu.class, (c) -> new Menu((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppMenuBar.class, (c) -> new MenuBar((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppToolBar.class, (c) -> new ToolBar((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppToolBarGroup.class, (c) -> new ToolBarGroup((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppDesktop.class, (c) -> new Desktop((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppDock.class, (c) -> new DockPane((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppPanel.class, (c) -> new Panel((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppTabs.class, (c) -> new Tabs((AppContainerModel) c.tool()));
//        addComponentDefaultFactory(AppWindow.class, (c) -> new Window((AppWindowModel) c.tool()));
//        addComponentDefaultFactory(AppUserControl.class, (c) -> new UserControl((AppUserControlModel) c.tool()));
//        addComponentDefaultFactory(AppMemoryMonitor.class, (c) -> new MemoryMonitor((AppMemoryMonitorModel) c.tool()));

//        addComponentTypeByToolFactory(AppToolButtonModel.class, AppButton.class);
//        addComponentTypeByToolFactory(AppFrameModel.class, AppFrame.class);
//        addComponentTypeByToolFactory(AppAlertModel.class, AppAlert.class);
//        addComponentTypeByToolFactory(AppFileChooserModel.class, AppFileChooser.class);
//        addComponentTypeByToolFactory(AppSeparatorModel.class, AppSeparator.class);
//        addComponentTypeByToolFactory(AppSpacerModel.class, AppSpacer.class);
//        addComponentTypeByToolFactory(AppChoiceModel.class, (c) -> AppComboBox.class);
//        addComponentTypeByToolFactory(AppCalendarModel.class, (c) -> AppCalendar.class);
//        addComponentTypeByToolFactory(AppFontChooserModel.class, (c) -> AppFontChooser.class);
//        addComponentTypeByToolFactory(AppColorChooserModel.class, (c) -> AppColorChooser.class);
//        addComponentTypeByToolFactory(AppNumberFieldModel.class, (c) -> AppNumberField.class);
//        addComponentTypeByToolFactory(AppTemporalFieldModel.class, (c) -> AppTemporalField.class);
//        addComponentTypeByToolFactory(AppTextModel.class, (c) -> AppTextField.class);
//        addComponentTypeByToolFactory(AppTreeModel.class, (c) -> AppTree.class);
//        addComponentTypeByToolFactory(AppTableModel.class, (c) -> AppTable.class);
//        addComponentTypeByToolFactory(AppWindowModel.class, (c) -> AppWindow.class);
//        addComponentTypeByToolFactory(AppUserControlModel.class, (c) -> AppUserControl.class);
//        addComponentTypeByToolFactory(AppToggleModel.class, (c) -> {
//            AppToggleModel t = (AppToggleModel) c.tool();
//            if (t.group().get() != null) {
//                return (Class)AppRadioButton.class;
//            }
//            return CheckBox.class;
//        });
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

}
