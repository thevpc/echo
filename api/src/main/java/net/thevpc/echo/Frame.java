package net.thevpc.echo;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppContainerChildren;
import net.thevpc.echo.api.AppUIPlaf;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.iconset.IconSet;
import net.thevpc.echo.impl.components.ContainerBase;
import net.thevpc.echo.impl.components.NameBoundPropertyListener;
import net.thevpc.echo.spi.peers.AppFramePeer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Frame extends ContainerBase<AppComponent> implements AppFrame {
    public WritableValue<FrameDisplayMode> displayMode = Props.of("displayMode")
            .adjust(e -> {
                if (e.newValue() == null) {
                    e.doInstead(() -> e.writableValue().set(FrameDisplayMode.NORMAL));
                }
            })
            .valueOf(FrameDisplayMode.class, FrameDisplayMode.NORMAL);
    private WritableValue<AppMenuBar> menuBar = Props.of("menuBar").valueOf(AppMenuBar.class, null);
    private WritableValue<AppToolBarGroup> statusBar = Props.of("statusBar").valueOf(AppToolBarGroup.class, null);
    private WritableValue<AppToolBarGroup> toolBar = Props.of("toolBar").valueOf(AppToolBarGroup.class, null);
    private WritableValue<AppComponent> content = Props.of("content").valueOf(AppComponent.class, null);
    private WindowStateSetValue state;
    private WritableBoolean closable;
    private WritableBoolean iconifiable;
    private DefaultMenusHelper defaultMenusHelper=new DefaultMenusHelper();

    public Frame(Application app) {
        this(null, app);
    }

    public Frame(String id, Application app) {
        super(id,
                app, AppFrame.class, AppFramePeer.class,
                AppComponent.class);
        propagateEvents(menuBar, statusBar, toolBar, content);

        menuBar().onChange(new NameBoundPropertyListener<>("menuBar", children));
        statusBar().onChange(new NameBoundPropertyListener<>("statusBar", children));
        toolBar().onChange(new NameBoundPropertyListener<>("toolBar", children));
        content().onChange(new NameBoundPropertyListener<>("content", children));
        children().onChange((e) -> {
            AppComponent c = e.newValue();
            if (c != null) {
                Path cp = c.path().get();
                switch (cp.name()) {
                    case "menuBar": {
                        if (menuBar().get() != c) {
                            menuBar().set((AppMenuBar) c);
                        }
                        break;
                    }
                    case "statusBar": {
                        if (statusBar().get() != c) {
                            statusBar().set((AppToolBarGroup) c);
                        }
                        break;
                    }
                    case "toolBar": {
                        if (toolBar().get() != c) {
                            toolBar().set((AppToolBarGroup) c);
                        }
                        break;
                    }
                    case "content": {
                        if (content().get() != c) {
                            content().set(c);
                        }
                        break;
                    }
                }
            }
        });
        closable = AppProps.of("closable", app()).booleanOf(true);
        iconifiable = AppProps.of("iconifiable", app()).booleanOf(true);
        state = new WindowStateSetValue("state");
        propagateEvents(closable, iconifiable, state);
    }

    private void runAfterStart(Runnable r) {
        Path path = path().get();
        if (path == null || path.isEmpty()) {
            path().onChange(e -> {
                r.run();
            });
        } else {
            r.run();
        }
    }

    @Override
    public AppComponent createPreferredChild(String name, Path absolutePath) {
        switch (name) {
            case "menuBar": {
                return new MenuBar(app());
            }
            case "statusBar":
            case "toolBar": {
                return new ToolBarGroup(app());
            }
            case "content": {
                return new DockPane(app());
            }
        }
        throw new IllegalArgumentException("unsupported child " + name + " in Frame");
    }

    @Override
    public WritableBoolean closable() {
        return closable;
    }

    @Override
    public WritableBoolean iconifiable() {
        return iconifiable;
    }

    @Override
    public void open() {
        state().add(WindowState.OPENED);
    }

    public WindowStateSetValue state() {
        return state;
    }

    @Override
    public WritableValue<FrameDisplayMode> displayMode() {
        return displayMode;
    }

    @Override
    public WritableValue<AppMenuBar> menuBar() {
        return menuBar;
    }

    @Override
    public WritableValue<AppToolBarGroup> statusBar() {
        return statusBar;
    }

    @Override
    public WritableValue<AppToolBarGroup> toolBar() {
        return toolBar;
    }

    @Override
    public WritableValue<AppComponent> content() {
        return content;
    }

    @Override
    public void centerOnDefaultMonitor() {
        ((AppFramePeer) peer()).centerOnDefaultMonitor();
    }

    @Override
    public void close() {
        this.state().add(WindowState.CLOSING);
    }

    public void addDefaultMenus() {
        defaultMenusHelper.addDefaultMenus();
    }

    public DefaultMenusHelper defaultMenus() {
        return defaultMenusHelper;
    }

    public class DefaultMenusHelper {
        public void addDefaultMenus() {
            runAfterStart(() -> {
                app().components().addFolder(path().get().append("/menuBar/File"));
                app().components().addFolder(path().get().append("/menuBar/Edit"));
                app().components().addFolder(path().get().append("/menuBar/View"));
                app().components().addFolder(path().get().append("/menuBar/Help"));
            });
//        if (startupConfig()
//                .enableQuit().get()) {
//            addQuitAction();
//        }

            addViewToolActions();

//        if (startupConfig()
//                .enablePlaf().get()) {
            addViewPlafActions();
//        }

//        if (startupConfig()
//                .enableIcons().get()) {
            addViewIconActions();
//        }

//        if (startupConfig()
//                .enableAppearance().get()) {
            addViewAppearanceActions();
//        }
            addViewLocaleActions();
            addWindowsActions();
        }
        public void addViewAppearanceActions() {
            runAfterStart(() -> {
                Path path = path().get().append("/menuBar/View/Appearance");
                Frame thisFrame = Frame.this;
                Application app = app();
                AppContainerChildren<AppComponent> components = app.components();
                components.addFolder(path).smallIcon().set(Str.i18n("appearance"));
                Button a = new Button("Switch", app);
                a.action().set(() -> {
                    AppFrame w = thisFrame;
                    FrameDisplayMode dm = w.displayMode().get();
                    if (dm == null) {
                        dm = FrameDisplayMode.NORMAL;
                    }
                    switch (dm) {
                        case NORMAL: {
                            w.displayMode().set(FrameDisplayMode.FULLSCREEN);
                            break;
                        }
                        default: {
                            w.displayMode().set(FrameDisplayMode.NORMAL);
                            break;
                        }
                    }
                });
                a.accelerator().set("alt shift F");
                components.add(a, path.append("Switch"));

                            /*,
                        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK),
                        JComponent.WHEN_IN_FOCUSED_WINDOW*/
                app.components().addSeparator(path.append("*"));

                RadioButton t = new RadioButton("AppWindowDisplayMode.Normal", path.toString(), app);
                t.text().set(Str.i18n(t.id()));
                t.selected().bindEquals(Frame.this.displayMode(), FrameDisplayMode.NORMAL);
                app.components().add(t, path.append("Normal"));

                t = new RadioButton("AppWindowDisplayMode.FullScreen", path.toString(), app);
                t.text().set(Str.i18n(t.id()));
                t.selected().bindEquals(Frame.this.displayMode(), FrameDisplayMode.FULLSCREEN);
                app.components().add(t, path.append("/FullScreen"));

                app.components().addSeparator(path.append("/Separator2"));

                CheckBox visibleToolBar = new CheckBox("Toolbar.visible", app);
                visibleToolBar.visible().bind(app, Frame.this.path().get().append("toolBar/visible"));
                app.components().add(visibleToolBar, path.append("Toolbar"));

                CheckBox visibleStatusBar = new CheckBox("StatusBar.visible", app);
                visibleStatusBar.visible().bind(app, Frame.this.path().get().append("statusBar/visible"));
                app.components().add(visibleStatusBar, path.append("StatusBar"));
            });
        }

        public void addViewLocaleActions() {
            runAfterStart(() -> {
                if (!app().i18n().locales().isEmpty()) {
                    Path path = path().get().append("menuBar/View/Lang");
                    app().components().addFolder(path).smallIcon().set(Str.i18n("locales"));
                    RadioButton toggle = new RadioButton("Locale.System", path.toString(), app());
                    toggle.text().set(Str.i18n(toggle.id()));
                    toggle.selected().bindEquals(app().i18n().locale(), Locale.getDefault());
                    app().components().add(toggle, path.append("system-locale"));
                    for (Locale locale : app().i18n().locales()) {
                        toggle = new RadioButton("Locale." + locale.toString(), path.toString(), app());
                        toggle.text().set(Str.i18n(toggle.id()));
                        toggle.selected().bindEquals(app().i18n().locale(), locale);
                        app().components().add(toggle, path.append(locale + "-locale"));
                    }
                }
            });
        }

        public void addQuitAction() {
            Runnable r = () -> {
                Frame frame = Frame.this;
                AppContainerChildren<AppComponent> components = app().components();
                Button a = new Button("Quit", app());
                a.action().set((e) -> e.app().shutdown());
                Path path = path().get();
                components.add(a, path.append("menuBar/File/Quit"));
            };
            Path path = path().get();
            if (path == null) {
                path().onChange(e -> {
                    r.run();
                });
            } else {
                r.run();
            }
        }

        public void addWindowsActions() {
            Runnable r = () -> {
                Frame frame = Frame.this;
                Path path = path().get();
                AppComponent ws = frame.content().get();
                AppDesktop desktop = null;
                if (ws instanceof AppDesktop) {
                    desktop = (AppDesktop) ws;
                } else if (ws instanceof AppDock) {
                    List<AppComponent> content = ((AppDock) ws).children().stream().filter(x -> x.anchor().get() == Anchor.CENTER).collect(Collectors.toList());
                    if (content.size() == 1) {
                        AppComponent mainComp = content.get(0);
                        if (mainComp instanceof AppDesktop) {
                            desktop = (AppDesktop) mainComp;
                        }
                    }
                }
                if (desktop != null) {
                    AppContainerChildren<AppComponent> components = app().components();
                    Button a;

                    a = new Button("TileWindowsAction", app());
                    AppDesktop finalDesktop = desktop;
                    a.action().set((e) -> finalDesktop.iconDesktop(false));
                    components.add(a, path.append("menuBar/Windows/TileWindowsAction"));

                    a = new Button("IconifyWindowsAction", app());
                    a.action().set((e) -> finalDesktop.iconDesktop(true));
                    components.add(a, path.append("menuBar/Windows/IconifyWindowsAction"));

                    a = new Button("DeiconifyWindowsAction", app());
                    a.action().set((e) -> finalDesktop.iconDesktop(false));
                    components.add(a, path.append("menuBar/Windows/DeiconifyWindowsAction"));

                    a = new Button("CloseWindowsAction", app());
                    a.action().set((e) -> finalDesktop.closeAllDesktop());
                    components.add(a, path.append("menuBar/Windows/CloseWindowsAction"));
                }
            };
            Path path = path().get();
            if (path == null) {
                path().onChange(e -> {
                    r.run();
                });
            } else {
                r.run();
            }
        }

        public void addViewActions() {
            Runnable r = () -> {
                Path path = path().get();
                app().components().addFolder(path.append("menuBar/View"));
            };
            Path path = path().get();
            if (path == null) {
                path().onChange(e -> {
                    r.run();
                });
            } else {
                r.run();
            }
            addViewToolActions();
            addViewPlafActions();
            addViewFontSizeActions();
            addViewIconActions();
            addViewAppearanceActions();
        }

        public void addViewToolActions() {
        }

        public void addToolActions() {
            runAfterStart(() -> {
                Path path = path().get().append("menuBar/View/ToolWindows");
                AppContainerChildren<AppComponent> components = app().components();
                components.addFolder(path).smallIcon().set(Str.i18n("tool-windows"));
                AppComponent ws = Frame.this.content().get();
                if (ws instanceof AppDock) {
                    ObservableList<AppComponent> values = ((AppDock) ws).children();
                    values.onChange(e -> {
                        System.out.println("tools changed...");
                        if (e.eventType() == PropertyUpdate.ADD) {
                            AppComponent value = e.newValue();
                            if (value.anchor().get() != Anchor.CENTER) {
                                CheckBox t = new CheckBox(app());
                                t.text().bind(value.title());
                                t.selected().bindTarget(value.active());
                                app().components()
                                        .add(t, path.append(value.id()));
                            }
                        }
                    });
                    java.util.List<AppComponent> list = new ArrayList<>();
                    for (AppComponent value : values) {
                        list.add(value);
                    }
                    if (!list.isEmpty()) {
                        list.sort(Comparator.comparing(a -> a.id()));
                        for (AppComponent value : list) {
                            if (value.anchor().get() != Anchor.CENTER) {
                                CheckBox t = new CheckBox(app());
                                t.text().bind(value.title());
                                t.selected().bindTarget(value.active());
                                app().components()
                                        .add(t, path.append(value.id()));
                            }
                        }
                    }
                }
            });
        }

        public void addViewFontSizeActions() {
//            addFontSizeRelativeActions(application, null);
//            addFontSizeAbsoluteActions(application, null);
        }

        //        public static void addFontSizeRelativeActions(Application application, String path, float... sizes) {
//            if (path == null) {
//                path = "/mainFrame/menuBar/View/Font/Sizes";
//            }
//            if (sizes == null || sizes.length == 0) {
//                sizes = new float[]{0.5f, 0.8f, 1.0f, 1.2f, 1.4f, 1.6f, 2f, 2.5f};
//            }
//            AppTools model = application.model();
//            model.addFolder(path);
//            for (int i = 0; i < sizes.length; i++) {
//                float size = sizes[i];
//                model.addAction().bind(
//                        () -> UIPlafManager.getCurrentManager().resizeRelativeFonts(size)
//                ).path(path + "/*" + size).tool();
//            }
////            model.addCustomTool(path + "/**", (c)->new JSlider(0, 6));
//        }
//
//        public static void addFontSizeAbsoluteActions(Application application, String path, float... sizes) {
//            if (path == null) {
//                path = "/mainFrame/menuBar/View/Font/Sizes";
//            }
//            if (sizes == null || sizes.length == 0) {
//                sizes = new float[]{8f, 10f, 12f, 14f, 16f, 18f, 20f, 28f};
//            }
//            AppTools model = application.model();
//            model.addFolder(path);
//            for (int i = 0; i < sizes.length; i++) {
//                float size = sizes[i];
//                model.addAction().bind(
//                        () -> UIPlafManager.getCurrentManager().resizeAbsoluteFonts(size)
//                ).path(path + "/" + size).tool();
//            }
//        }
        public void addViewIconActions() {
            addIconActions();
        }

        public void addIconActions(int... sizes0) {
            runAfterStart(() -> {
                Path path = path().get().append("menuBar/View/Icons");
                int[] sizes = sizes0;
                AppContainerChildren<AppComponent> components = app().components();
                components.addFolder(path).smallIcon().set(Str.i18n("icons"));
                components.addFolder(path.append("Packs"));
                for (IconSet iconset : app().iconSets().values()) {
                    RadioButton t = new RadioButton("IconSet." + iconset.getId(), path.append("Packs").toString(), app());
                    t.text().set(Str.i18n(t.id()));
                    t.selected().bindEquals(app().iconSets().id(), iconset.getId());
                    components.add(t, path.append("Packs").append(iconset.getId()));
                }
                if (sizes == null || sizes.length == 0) {
                    sizes = new int[]{8, 16, 24, 32, 48};
                }
                components.addFolder(Path.of(path + "/Sizes"));
//                ToggleModel[] toggles = null;
//                app.iconSets().config().onChange(new PropertyListener() {
//                    @Override
//                    public void propertyUpdated(PropertyEvent event) {
//                        for (int i = 0; i < toggles.length; i++) {
//                            Integer s = (Integer) toggles[i].properties().get("size");
//                            toggles[i].selected().set(s == event.getNewValue());
//                        }
//                    }
//                });
                for (int i = 0; i < sizes.length; i++) {
                    int size = sizes[i];
                    RadioButton toggle = new RadioButton("IconSet.Size." + size, path.append("Sizes").toString(), app());
                    toggle.text().set(Str.i18n(toggle.id()));
                    toggle.selected().bind(
                            app().iconSets().config().mapBooleanValue(x -> x.getWidth() == size),
                            b -> {
                                if (b) {
                                    app().iconSets().config().set(
                                            app().iconSets().config().get().setSize(size)
                                    );
                                }
                            }
                    );
                    app().components().add(toggle, path.append("Sizes").append(String.valueOf(size)));
                }
            });
        }

        public void addViewPlafActions() {
            addPlafActions();
        }

        public void addPlafActions() {
            runAfterStart(() -> {
                Frame frame = Frame.this;
                Path path = frame.path().get().append("/menuBar/View/Plaf");
                AppContainerChildren<AppComponent> components = app().components();
                components.addFolder(path).smallIcon().set(Str.i18n("themes"));
                for (AppUIPlaf item : app().toolkit().loadAvailablePlafs()) {
                    String q = "Other";
                    if (item.isSystem()) {
                        q = "System";
                    } else if (item.isDark()) {
                        q = "Dark";
                    } else if (item.isLight()) {
                        q = "Light";
                    } else {
                        q = "Other";
                    }
                    String id = item.getId();
                    String pname = id.replace("/", "_");
                    if (item.isContrast()) {
                        q = "Contrast";
                    }
                    RadioButton toggle = new RadioButton("Plaf." + id, path.toString(), app());
                    toggle.text().set(Str.i18n("Plaf." + id));
                    toggle.smallIcon().set((Str) null);
                    toggle.selected().userObjects().put("path", path.append(q).append(pname));
                    toggle.selected().bindEquals(app().plaf(), id);
                    app().components().add(toggle, path.append(q).append(pname));
                }
            });
        }

    }
}

