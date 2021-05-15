package net.thevpc.echo.impl;

import net.thevpc.echo.*;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.tools.*;
import net.thevpc.echo.impl.components.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Function;

public abstract class AbstractApplicationToolkit implements ApplicationToolkit {

    protected Application app;
    /**
     * used to map a peer instance for a given component
     */
    private _ClassMap<List<AppComponentPeerFactory>> componentToPeers = (_ClassMap) new _ClassMap<AppComponentPeerFactory>(AppComponent.class, (Class) List.class);

    /**
     * used to map a component instance to a given tool
     */
    private _ClassMap<List<AppComponentFactory>> toolToComponent = (_ClassMap) new _ClassMap<AppComponentPeerFactory>(AppTool.class, (Class) List.class);

    /**
     * used to create a map a default component  for a given Tool
     */
    private _ClassMap<Function<AppComponentRendererContext, AppComponent>> componentsDefaultFactories = (_ClassMap) new _ClassMap<AppComponentPeerFactory>(AppComponent.class, (Class) Function.class);

    public AbstractApplicationToolkit(Application application) {
        this.app = application;
        ServiceLoader<AppComponentFactory> csl = ServiceLoader.load(AppComponentFactory.class, Thread.currentThread().getContextClassLoader());
        for (AppComponentFactory appComponentPeerFactory : csl) {
            addComponentFactory(appComponentPeerFactory);
        }

        ServiceLoader<AppComponentPeerFactory> psl = ServiceLoader.load(AppComponentPeerFactory.class, Thread.currentThread().getContextClassLoader());
        for (AppComponentPeerFactory appComponentPeerFactory : psl) {
            addPeerFactory(appComponentPeerFactory);
        }
        addComponentDefaultFactory(AppButton.class, (c) -> new Button((AppToolAction) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppFrame.class, (c) -> new Frame((AppToolFrame) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppAlert.class, (c) -> new Alert((AppToolAlert) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppFileChooser.class, (c) -> new FileChooser((AppToolFile) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppSeparator.class, (c) -> new Separator((AppToolSeparator) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppSpacer.class, (c) -> new Spacer((AppToolSpacer) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppRadioButton.class, (c) -> new RadioButton((AppToolToggle) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppCheckBox.class, (c) -> new CheckBox((AppToolToggle) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppToggle.class, (c) -> new Toggle((AppToolToggle) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppComboBox.class, (c) -> new ComboBox((AppToolChoice) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppChoiceList.class, (c) -> new ChoiceList((AppToolChoice) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppCalendar.class, (c) -> new Calendar((AppToolCalendar) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppFontChooser.class, (c) -> new FontChooser((AppToolFont) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppColorChooser.class, (c) -> new ColorChooser((AppToolColor) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppNumberField.class, (c) -> new NumberField((AppToolNumber) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppTemporalField.class, (c) -> new TemporalField((AppToolTemporal) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppTextField.class, (c) -> new TextField((AppToolText) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppTextArea.class, (c) -> new TextArea((AppToolText) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppTree.class, (c) -> new Tree((AppToolTree) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppTable.class, (c) -> new Table((AppToolTable) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppMenu.class, (c) -> new Menu((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppMenuBar.class, (c) -> new MenuBar((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppToolBar.class, (c) -> new ToolBar((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppToolBarGroup.class, (c) -> new ToolBarGroup((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppWorkspace.class, (c) -> new Workspace((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppDesktop.class, (c) -> new Desktop((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppDock.class, (c) -> new DockPane((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppPanel.class, (c) -> new Panel((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppTabs.class, (c) -> new Tabs((AppToolFolder) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppWindow.class, (c) -> new Window((AppToolWindow) c.tool()).setOptions(c.options()));
        addComponentDefaultFactory(AppUserControl.class, (c) -> new UserControl((AppToolCustom) c.tool()).setOptions(c.options()));

        addComponentFactory(AppToolAction.class, AppButton.class);
        addComponentFactory(AppToolFrame.class, AppFrame.class);
        addComponentFactory(AppToolAlert.class, AppAlert.class);
        addComponentFactory(AppToolFile.class, AppFileChooser.class);
        addComponentFactory(AppToolSeparator.class, AppSeparator.class);
        addComponentFactory(AppToolSpacer.class, AppSpacer.class);
        addComponentFactory0(AppToolChoice.class, (c) -> AppComboBox.class);
        addComponentFactory0(AppToolCalendar.class, (c) -> AppCalendar.class);
        addComponentFactory0(AppToolFont.class, (c) -> AppFontChooser.class);
        addComponentFactory0(AppToolColor.class, (c) -> AppColorChooser.class);
        addComponentFactory0(AppToolNumber.class, (c) -> AppNumberField.class);
        addComponentFactory0(AppToolTemporal.class, (c) -> AppTemporalField.class);
        addComponentFactory0(AppToolText.class, (c) -> AppTextField.class);
        addComponentFactory0(AppToolTree.class, (c) -> AppTree.class);
        addComponentFactory0(AppToolTable.class, (c) -> AppTable.class);
        addComponentFactory0(AppToolWindow.class, (c) -> AppWindow.class);
        addComponentFactory0(AppToolCustom.class, (c) -> AppUserControl.class);
        addComponentFactory0(AppToolToggle.class, (c) -> {
            AppToolToggle t = (AppToolToggle) c.tool();
            if (t.group().get() != null) {
                return (Class)AppRadioButton.class;
            }
            return CheckBox.class;
        });
    }

    public AppComponent createDefaultComponent(Class<? extends AppComponent> componentType, AppTool tool, AppComponent parent, String name, AppComponentOptions options) {
        if (componentType == null) {
            throw new IllegalArgumentException("missing componentType");
        }
        AppPath path = name==null?AppPath.of():AppPath.of(name);
        if (parent != null) {
            path = parent.path().get().child(path);
        }
        AppComponentRendererContext context = new AppComponentRendererContext(
                parent,
                tool,
                app,
                path, name, options
        );
        Function<AppComponentRendererContext, AppComponent> sl = componentsDefaultFactories.get(componentType);
        if (sl == null) {
            throw new IllegalArgumentException("unable to create default component for tool of type " + tool.getClass().getName());
        }
        AppComponent a = sl.apply(context);
        if (a == null) {
            throw new IllegalArgumentException("unable to create default component for tool of type " + tool.getClass().getName());
        }
        return a;
    }

    public AppComponent createComponent(AppTool tool, AppComponent parent, String name, AppComponentOptions options) {
        AppPath path = name==null?AppPath.of():AppPath.of(name);
        if (parent != null) {
            path = parent.path().get().child(path);
        }
        AppComponentRendererContext context = new AppComponentRendererContext(
                parent,
                tool,
                app,
                path, name, options
        );
        Class<? extends AppComponent> c = options==null?null:options.componentType();
        if(c!=null){
            Function<AppComponentRendererContext, AppComponent> a = componentsDefaultFactories.get(c);
            if(a!=null){
                return a.apply(context);
            }
        }
        List<AppComponentFactory> sl = toolToComponent.get(tool.getClass());
        SupportSupplier<AppComponent> best = null;
        if (sl != null) {
            int bestLvl = -1;
            for (AppComponentFactory w : sl) {
                //path=window.path().get().append("workspace") //TODO, fix me
                SupportSupplier<AppComponent> curr = w.createComponent(context);
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
        throw new IllegalArgumentException("unable to create component for tool of type " + tool.getClass().getName());
    }

    @Override
    public AppComponentPeer createComponentPeer(AppComponent component) {
        List<AppComponentPeerFactory> sl = componentToPeers.get(component.getClass());
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
        throw new IllegalArgumentException("unable to create peer for component of type " + component.getClass().getName());
    }

    private void addPeerFactory(AppComponentPeerFactory appComponentPeerFactory) {
        for (Class supportedComponentType : appComponentPeerFactory.getSupportedComponentTypes()) {
            List<AppComponentPeerFactory> a = componentToPeers.getExact(supportedComponentType);
            if (a == null) {
                a = new ArrayList<>();
                componentToPeers.put(supportedComponentType, a);
            }
            a.add(appComponentPeerFactory);
        }
    }

    private void addComponentFactory(AppComponentFactory factory) {
        for (Class supportedComponentType : factory.getSupportedToolTypes()) {
            List<AppComponentFactory> a = toolToComponent.getExact(supportedComponentType);
            if (a == null) {
                a = new ArrayList<>();
                toolToComponent.put(supportedComponentType, a);
            }
            a.add(factory);
        }
    }


    public <T extends AppComponent> void addPeerFactory(Class<T> type, Class<? extends AppComponentPeer> peer) {
        addPeerFactory(new AppComponentPeerFactory() {
            @Override
            public List<Class> getSupportedComponentTypes() {
                return Arrays.asList(type);
            }

            @Override
            public SupportSupplier<AppComponentPeer> createComponentPeer(AppComponent component) {
                return new SupportSupplier<AppComponentPeer>() {
                    @Override
                    public int getSupportLevel() {
                        return 1;
                    }

                    @Override
                    public AppComponentPeer get() {
                        try {
                            return peer.getConstructor().newInstance();
                        } catch (Exception e) {
                            throw UncheckedException.wrap(e);
                        }
                    }
                };
            }
        });
    }


    public <T extends AppComponent> void addComponentDefaultFactory(Class<T> type, Function<AppComponentRendererContext, AppComponent> factory) {
        componentsDefaultFactories.put(type, factory);
    }

    public <T extends AppTool, C extends AppComponent> void addComponentFactory(Class<T> toolType, Class<C> componentType) {
        addComponentFactory(new AppComponentFactory() {
            @Override
            public List<Class> getSupportedToolTypes() {
                return Arrays.asList(toolType);
            }

            @Override
            public SupportSupplier<AppComponent> createComponent(AppComponentRendererContext context) {
                return new SupportSupplier<AppComponent>() {
                    @Override
                    public int getSupportLevel() {
                        Class<? extends AppComponent> c = context.componentType();
                        if (c == null) {
                            c = componentType;
                        }
                        Function<AppComponentRendererContext, AppComponent> q = componentsDefaultFactories.get(c);
                        return q == null ? -1 : 1;
                    }

                    @Override
                    public AppComponent get() {
                        Class<? extends AppComponent> c = context.componentType();
                        if (c == null) {
                            c = componentType;
                        }
                        return createDefaultComponent(c, context.tool(), context.parent(),
                                context.name(), context.options());
                    }
                };
            }
        });
    }

    public <T extends AppTool, C extends AppComponent> void addComponentFactory0(
            Class<T> toolType,
            Function<AppComponentRendererContext, Class<C>> componentType) {
        addComponentFactory(new AppComponentFactory() {
            @Override
            public List<Class> getSupportedToolTypes() {
                return Arrays.asList(toolType);
            }

            @Override
            public SupportSupplier<AppComponent> createComponent(AppComponentRendererContext context) {
                return new SupportSupplier<AppComponent>() {
                    @Override
                    public int getSupportLevel() {
                        return 1;
                    }

                    @Override
                    public AppComponent get() {
                        Class<? extends AppComponent> c = context.componentType();
                        if (c == null) {
                            c = componentType.apply(context);
                        }
                        return createDefaultComponent(c, context.tool(), context.parent(),
                                context.name(), context.options());
                    }
                };
            }
        });
    }

    public <T extends AppTool> void addComponentFactory(Class<T> type, Function<AppComponentRendererContext, AppComponent> component) {
        addComponentFactory(new AppComponentFactory() {
            @Override
            public List<Class> getSupportedToolTypes() {
                return Arrays.asList(type);
            }

            @Override
            public SupportSupplier<AppComponent> createComponent(AppComponentRendererContext context) {
                return new SupportSupplier<AppComponent>() {
                    @Override
                    public int getSupportLevel() {
                        return 1;
                    }

                    @Override
                    public AppComponent get() {
                        return component.apply(context);
                    }
                };
            }
        });
    }

}
