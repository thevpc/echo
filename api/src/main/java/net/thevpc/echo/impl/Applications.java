package net.thevpc.echo.impl;

import net.thevpc.common.i18n.I18n;
import net.thevpc.common.props.*;
import net.thevpc.echo.*;
import net.thevpc.common.props.Path;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.iconset.IconSet;
import net.thevpc.echo.impl.components.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Applications {

    private Applications() {
    }

    public static String rawString(Str str, Application app) {
        return rawString(str, app.i18n());
    }

    public static String rawString(Str str, I18n i18n) {
        if (str == null) {
            return "";
        }
        if (!str.is18n()) {
            return str.getValue();
        }
        return i18n.getString(str.getValue());
    }

    public static class Helper {

        public static void addQuitAction(Application application) {
            runAfterStart(application, () -> {
                AppContainerChildren<AppComponentModel, AppComponent> components = application.components();
                Button a = new Button("Quit", application);
                a.model().action().set((e) -> e.app().shutdown());
                components.add(a, Path.of("/mainFrame/menuBar/File/Quit"));
            });
        }

        public static void addWindowsActions(Application application) {
            runAfterStart(application, () -> {
                AppComponent ws = application.mainFrame().get().workspace().get();
                AppDesktop desktop=null;
                if(ws instanceof AppDesktop){
                    desktop=(AppDesktop) ws;
                }else if(ws instanceof AppDock){
                    List<AppWindow> content = ((AppDock) ws).children().stream().filter(x -> x.model().anchor().get() == AppWindowAnchor.CENTER).collect(Collectors.toList());
                    if(content.size()==1){
                        AppComponent mainComp = content.get(0).model().component().get();
                        if(mainComp instanceof AppDesktop){
                            desktop=(AppDesktop) mainComp;
                        }
                    }
                }
                if (desktop!=null) {
                    AppContainerChildren<AppComponentModel, AppComponent> components = application.components();
                    Button a;

                    a = new Button("TileWindowsAction", application);
                    AppDesktop finalDesktop = desktop;
                    a.action().set((e) -> finalDesktop.iconDesktop(false));
                    components.add(a, Path.of("/mainFrame/menuBar/Windows/TileWindowsAction"));

                    a = new Button("IconifyWindowsAction", application);
                    a.action().set((e) -> finalDesktop.iconDesktop(true));
                    components.add(a, Path.of("/mainFrame/menuBar/Windows/IconifyWindowsAction"));

                    a = new Button("DeiconifyWindowsAction", application);
                    a.action().set((e) -> finalDesktop.iconDesktop(false));
                    components.add(a, Path.of("/mainFrame/menuBar/Windows/DeiconifyWindowsAction"));

                    a = new Button("CloseWindowsAction", application);
                    a.action().set((e) -> finalDesktop.closeAllDesktop());
                    components.add(a, Path.of("/mainFrame/menuBar/Windows/CloseWindowsAction"));
                }
            });
        }

        public static void addViewActions(Application app) {
            runAfterStart(app, () -> {
                app.components().addFolder(Path.of("/mainFrame/menuBar/View"));
            });
            addViewToolActions(app);
            addViewPlafActions(app);
            addViewFontSizeActions(app);
            addViewIconActions(app);
            addViewAppearanceActions(app);
        }

        public static void addViewToolActions(Application app) {
            addToolActions(app, null);
        }

        public static void addToolActions(Application app,String path0) {
            runAfterStart(app, () -> {
                String path = path0;

                if (path == null) {
                    path = "/mainFrame/menuBar/View/ToolWindows";
                }
                AppContainerChildren<AppComponentModel, AppComponent> components = app.components();
                components.addFolder(Path.of(path)).model().smallIcon().set(Str.i18n("tool-windows"));
                String finalPath = path;
                AppComponent ws = app.mainFrame().get().workspace().get();
                if (ws instanceof AppDock) {
                    ObservableList<AppWindow> values = ((AppDock)ws).children();
                    java.util.List<AppWindow> list = new ArrayList<>();
                    for (AppWindow value : values) {
                        list.add(value);
                    }
                    if (!list.isEmpty()) {
                        list.sort(Comparator.comparing(a -> a.model().id()));
                        for (AppWindow value : list) {
                            CheckBox t = new CheckBox(app);
                            t.title().bind(value.model().title());
                            t.selected().bindTarget(value.model().active());
                            app.components()
                                    .add(t, Path.of(finalPath).append(value.model().id()));
                        }
                    }
                }
            });
        }

        public static void addViewFontSizeActions(Application application) {
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
//            AppTools tools = application.tools();
//            tools.addFolder(path);
//            for (int i = 0; i < sizes.length; i++) {
//                float size = sizes[i];
//                tools.addAction().bind(
//                        () -> UIPlafManager.getCurrentManager().resizeRelativeFonts(size)
//                ).path(path + "/*" + size).tool();
//            }
////            tools.addCustomTool(path + "/**", (c)->new JSlider(0, 6));
//        }
//
//        public static void addFontSizeAbsoluteActions(Application application, String path, float... sizes) {
//            if (path == null) {
//                path = "/mainFrame/menuBar/View/Font/Sizes";
//            }
//            if (sizes == null || sizes.length == 0) {
//                sizes = new float[]{8f, 10f, 12f, 14f, 16f, 18f, 20f, 28f};
//            }
//            AppTools tools = application.tools();
//            tools.addFolder(path);
//            for (int i = 0; i < sizes.length; i++) {
//                float size = sizes[i];
//                tools.addAction().bind(
//                        () -> UIPlafManager.getCurrentManager().resizeAbsoluteFonts(size)
//                ).path(path + "/" + size).tool();
//            }
//        }
        public static void addViewIconActions(Application application) {
            addIconActions(application, null);
        }

        public static void addIconActions(Application app, String path0, int... sizes0) {
            runAfterStart(app, () -> {
                String path = path0;
                int[] sizes = sizes0;
                AppContainerChildren<AppComponentModel, AppComponent> components = app.components();
                if (path == null) {
                    path = "/mainFrame/menuBar/View/Icons";
                }
                components.addFolder( Path.of(path)).model().smallIcon().set(Str.i18n("icons"));
                components.addFolder(Path.of(path + "/Packs"));
                for (IconSet iconset : app.iconSets().values()) {
                    RadioButton t = new RadioButton("IconSet." + iconset.getId(), path + "/Packs",app);
                    t.title().set(Str.i18n(t.id()));
                    t.selected().bindEquals(app.iconSets().id(), iconset.getId());
                    components.add(t, Path.of(path).append("Packs").append(iconset.getId()));
                }
                if (sizes == null || sizes.length == 0) {
                    sizes = new int[]{8, 16, 24, 32, 48};
                }
                components.addFolder(Path.of(path + "/Sizes"));
//                ToggleModel[] toggles = null;
//                app.iconSets().config().listeners().add(new PropertyListener() {
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
                    RadioButton toggle = new RadioButton("IconSet.Size." +size,path + "/Sizes",app);
                    toggle.title().set(Str.i18n(toggle.id()));
                    toggle.selected().bind(
                            app.iconSets().config().mapBooleanValue(x -> x.getWidth() == size),
                            b -> {
                                if (b) {
                                    app.iconSets().config().set(
                                            app.iconSets().config().get().setSize(size)
                                    );
                                }
                            }
                    );
                    app.components().add(toggle, Path.of(path + "/Sizes/" + size));
                }
            });
        }

        public static void addViewPlafActions(Application application) {
            addPlafActions(application, null);
        }

        public static void addPlafActions(Application app, String path0) {
            runAfterStart(app, () -> {
                String path = path0;
                if (path == null) {
                    path = "/mainFrame/menuBar/View/Plaf";
                }
                AppContainerChildren<AppComponentModel, AppComponent> components = app.components();
                components.addFolder(Path.of(path)).model().smallIcon().set(Str.i18n("themes"));
                for (AppUIPlaf item : app.toolkit().loadAvailablePlafs()) {
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
                    RadioButton toggle = new RadioButton("Plaf." + id, path,app);
                    toggle.title().set(Str.i18n("Plaf." + id));
                    toggle.smallIcon().set((Str) null);
                    toggle.selected().userObjects().put("path", path + "/" + q + "/" + pname);
                    toggle.selected().bindEquals(app.plaf(), id);
                    app.components().add(toggle, Path.of(path + "/" + q + "/" + pname));
                }
            });
        }

        public static void addViewLocaleActions(Application application, Locale[] locales) {
            addViewLocaleActions(application, locales, null);
        }

        public static void addViewLocaleActions(Application app, Locale[] locales, String path0) {
            runAfterStart(app, () -> {
                String path = path0;

                if (path == null) {
                    path = "/mainFrame/menuBar/View/Lang";
                }

                app.components().addFolder(Path.of(path)).model().smallIcon().set(Str.i18n("locales"));

                RadioButton toggle = new RadioButton("Locale.System", path,app);
                toggle.selected().bindEquals(app.i18n().locale(), Locale.getDefault());
                app.components().add(toggle, Path.of(path + "/system-locale"));
                for (Locale locale : locales) {
                    toggle = new RadioButton("Locale." + locale.toString(), path,app);
                    toggle.selected().bindEquals(app.i18n().locale(), locale);
                    app.components().add(toggle, Path.of(path + "/" + locale + "-locale"));
                }
            });
        }

        public static void addViewAppearanceActions(Application application) {
            addAppearanceActions(application, null);
        }

        public static void addAppearanceActions(Application app, String path0) {
            runAfterStart(app, () -> {
                String path = path0;

                AppContainerChildren<AppComponentModel, AppComponent> components = app.components();
                if (path == null) {
                    path = "/mainFrame/menuBar/View/Appearance";
                }
                components.addFolder(Path.of(path)).model().smallIcon().set(Str.i18n("appearance"));
                String finalPath = path;
                runAfterStart(app, () -> {
                    Button a = new Button("Switch", app);
                    a.action().set(() -> {
                        AppFrame w = app.mainFrame().get();
                        AppWindowDisplayMode dm = w.model().displayMode().get();
                        if (dm == null) {
                            dm = AppWindowDisplayMode.NORMAL;
                        }
                        switch (dm) {
                            case NORMAL: {
                                w.model().displayMode().set(AppWindowDisplayMode.FULLSCREEN);
                                break;
                            }
                            default: {
                                w.model().displayMode().set(AppWindowDisplayMode.NORMAL);
                                break;
                            }
                        }
                    });
                    a.accelerator().set("alt shift F");
                    components.add(a, Path.of(finalPath + "/Switch"));

                            /*,
                        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK),
                        JComponent.WHEN_IN_FOCUSED_WINDOW*/
                    app.components().add(new Separator(app), Path.of(finalPath + "/Separator1"));

                    RadioButton t = new RadioButton("AppWindowDisplayMode.Normal", finalPath, app);
                    t.title().set(Str.i18n(t.id()));
                    t.selected().bindEquals(app.mainFrame().get().model().displayMode(), AppWindowDisplayMode.NORMAL);
                    app.components().add(t, Path.of(finalPath + "/Normal"));

                    t = new RadioButton("AppWindowDisplayMode.FullScreen", finalPath, app);
                    t.title().set(Str.i18n(t.id()));
                    t.selected().bindEquals(app.mainFrame().get().model().displayMode(), AppWindowDisplayMode.FULLSCREEN);
                    app.components().add(t, Path.of(finalPath + "/FullScreen"));

                    app.components().addSeparator(Path.of(finalPath + "/Separator2"));

                    CheckBox visibleToolBar = new CheckBox("Toolbar.visible",app);
                    visibleToolBar.visible().bind(app,Path.of("/mainFrame/toolBar/visible"));
                    app.components().add(visibleToolBar, Path.of(finalPath + "/Toolbar"));

                    CheckBox visibleStatusBar = new CheckBox("StatusBar.visible", app);
                    visibleStatusBar.visible().bind(app,Path.of("/mainFrame/statusBar/visible"));
                    app.components().add(visibleStatusBar, Path.of(finalPath + "/StatusBar"));
                });
            });
        }

        public static void runAfterStart(Application application, Runnable r) {
            if (application.state().get().ordinal() >= AppState.STARTED.ordinal()) {
                r.run();
            } else {
                application.state().listeners().add(new PropertyListener() {
                    @Override
                    public void propertyUpdated(PropertyEvent event) {
                        AppState e = event.getNewValue();
                        if (e == AppState.STARTED) {
                            r.run();
                        }
                    }
                });
            }
        }

    }
}
