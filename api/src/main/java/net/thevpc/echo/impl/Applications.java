package net.thevpc.echo.impl;

import net.thevpc.common.i18n.I18n;
import net.thevpc.common.props.ObservableList;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.*;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppFrame;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.tools.AppTool;
import net.thevpc.echo.iconset.IconSet;
import net.thevpc.echo.impl.components.AppContainerChildren;
import net.thevpc.echo.impl.tools.ToolAction;
import net.thevpc.echo.impl.tools.ToolFolder;
import net.thevpc.echo.impl.tools.ToolSeparator;
import net.thevpc.echo.impl.tools.ToolToggle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

public class Applications {

    private Applications() {
    }

    public static class Helper {

        public static void addQuitAction(Application application) {
            runAfterStart(application, () -> {
                AppContainerChildren<AppComponent, AppTool> components = application.components();
                ToolAction a = new ToolAction("Quit",application);
                a.action().set((e) -> e.app().shutdown());
                components.add(a,AppPath.of("/mainFrame/menuBar/File/Quit"));
            });
        }

        public static void addWindowsActions(Application application) {
            runAfterStart(application, () -> {
                AppWorkspace ws = application.mainFrame().get().workspace().get();
                if (ws.desktopEnabled()) {
                    AppContainerChildren<AppComponent, AppTool> components = application.components();
                    ToolAction a;

                    a = new ToolAction("TileWindowsAction",application);
                    a.action().set((e) -> ws.iconDesktop(false));
                    components.add(a,AppPath.of("/mainFrame/menuBar/Windows/TileWindowsAction"));

                    a = new ToolAction("IconifyWindowsAction",application);
                    a.action().set((e) -> ws.iconDesktop(true));
                    components.add(a,AppPath.of("/mainFrame/menuBar/Windows/IconifyWindowsAction"));

                    a = new ToolAction("DeiconifyWindowsAction",application);
                    a.action().set((e) -> ws.iconDesktop(false));
                    components.add(a,AppPath.of("/mainFrame/menuBar/Windows/DeiconifyWindowsAction"));

                    a = new ToolAction("CloseWindowsAction",application);
                    a.action().set((e) -> ws.closeAllDesktop());
                    components.add(a,AppPath.of("/mainFrame/menuBar/Windows/CloseWindowsAction"));
                }
            });
        }

        public static void addViewActions(Application app) {
            runAfterStart(app, () -> {
                app.components().add(new ToolFolder(app), AppPath.of("/mainFrame/menuBar/View"));
            });
            addViewToolActions(app);
            addViewPlafActions(app);
            addViewFontSizeActions(app);
            addViewIconActions(app);
            addViewAppearanceActions(app);
        }

        public static void addViewToolActions(Application application) {
            addToolActions(application, null);
        }

        public static void addToolActions(Application app, String path0) {
            runAfterStart(app, () -> {
                String path = path0;

                if (path == null) {
                    path = "/mainFrame/menuBar/View/ToolWindows";
                }
                AppContainerChildren<AppComponent, AppTool> components = app.components();
                components.add(new ToolFolder(app), AppPath.of(path)).tool().smallIcon().set(Str.i18n("tool-windows"));
                String finalPath = path;
                runAfterStart(app, () -> {
                    AppWorkspace ws = app.mainFrame().get().workspace().get();
                    if (ws != null && ws.dockingEnabled()) {
                        ObservableList<AppWindow> values = ws.windows().values();
                        java.util.List<AppWindow> list = new ArrayList<>();
                        for (AppWindow value : values) {
                            list.add(value);
                        }
                        if (!list.isEmpty()) {
                            list.sort(Comparator.comparing(a -> a.tool().id()));
                            for (AppWindow value : list) {
                                ToolToggle t = new ToolToggle(app);
                                t.selected().bindTarget(value.tool().active());
                                app.components()
                                        .add(t,AppPath.of(finalPath).append(value.tool().id()));
                            }
                        }
                    }
                });
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
                AppContainerChildren<AppComponent, AppTool> components = app.components();
                if (path == null) {
                    path = "/mainFrame/menuBar/View/Icons";
                }
                components.add(new ToolFolder(app), AppPath.of(path)).tool().smallIcon().set(Str.i18n("icons"));
                components.add(new ToolFolder(app), AppPath.of(path + "/Packs"));
                for (IconSet iconset : app.iconSets().values()) {
                    ToolToggle t = new ToolToggle("IconSet." + iconset.getId(), app);
                    t.group().set(path + "/Packs");
                    t.selected().bindEquals(app.iconSets().id(), iconset.getId());
                    components.add(t, AppPath.of(path).append("Packs").append(iconset.getId()));
                }
                if (sizes == null || sizes.length == 0) {
                    sizes = new int[]{8, 16, 24, 32, 48};
                }
                components.add(new ToolFolder(app), AppPath.of(path + "/Sizes"));
                ToolToggle[] toggles = null;
                app.iconSets().config().listeners().add(new PropertyListener() {
                    @Override
                    public void propertyUpdated(PropertyEvent event) {
                        for (int i = 0; i < toggles.length; i++) {
                            Integer s = (Integer) toggles[i].properties().get("size");
                            toggles[i].selected().set(s == event.getNewValue());
                        }
                    }
                });
                for (int i = 0; i < sizes.length; i++) {
                    int size = sizes[i];
                    ToolToggle toggle = new ToolToggle(app);
                    toggle.group().set(path + "/Sizes");
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
                    app.components().add(toggle, AppPath.of(path + "/Sizes/" + size));
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
                AppContainerChildren<AppComponent, AppTool> components = app.components();
                components.add(new ToolFolder(app), AppPath.of(path)).tool().smallIcon().set(Str.i18n("themes"));
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
                    ToolToggle toggle = new ToolToggle("Plaf." + id, app);
                    toggle.group().set(path);
                    toggle.smallIcon().set((Str) null);
                    toggle.selected().bindEquals(app.plaf(), id);
                    app.components().add(toggle, AppPath.of(path + "/" + q + "/" + pname));
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

                app.components().add(new ToolFolder(app), AppPath.of(path)).tool().smallIcon().set(Str.i18n("locales"));

                ToolToggle toggle = new ToolToggle("Locale.System", app);
                toggle.group().set(path);
                toggle.selected().bindEquals(app.i18n().locale(), Locale.getDefault());
                app.components().add(toggle, AppPath.of(path + "/system-locale"));
                for (Locale locale : locales) {
                    toggle = new ToolToggle("Locale." + locale.toString(), app);
                    toggle.group().set(path);
                    toggle.selected().bindEquals(app.i18n().locale(), locale);
                    app.components().add(toggle, AppPath.of(path + "/" + locale + "-locale"));
                }
            });
        }

        public static void addViewAppearanceActions(Application application) {
            addAppearanceActions(application, null);
        }

        public static void addAppearanceActions(Application app, String path0) {
            runAfterStart(app, () -> {
                String path = path0;

                AppContainerChildren<AppComponent, AppTool> components = app.components();
                if (path == null) {
                    path = "/mainFrame/menuBar/View/Appearance";
                }
                components.add(new ToolFolder(app), AppPath.of(path)).tool().smallIcon().set(Str.i18n("appearance"));
                String finalPath = path;
                runAfterStart(app, () -> {
                    ToolAction a = new ToolAction("Switch", app);
                    a.action().set(() -> {
                        AppFrame w = app.mainFrame().get();
                        AppWindowDisplayMode dm = w.tool().displayMode().get();
                        if (dm == null) {
                            dm = AppWindowDisplayMode.NORMAL;
                        }
                        switch (dm) {
                            case NORMAL: {
                                w.tool().displayMode().set(AppWindowDisplayMode.FULLSCREEN);
                                break;
                            }
                            default: {
                                w.tool().displayMode().set(AppWindowDisplayMode.NORMAL);
                                break;
                            }
                        }
                    });
                    a.accelerator().set("alt shift F");
                    components.add(a, AppPath.of(finalPath + "/Switch"));

                            /*,
                        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK),
                        JComponent.WHEN_IN_FOCUSED_WINDOW*/
                    app.components().add(new ToolSeparator(app), AppPath.of(finalPath + "/Separator1"));

                    ToolToggle t = new ToolToggle("AppWindowDisplayMode.Normal", finalPath, app);
                    t.selected().bindEquals(app.mainFrame().get().tool().displayMode(), AppWindowDisplayMode.NORMAL);
                    app.components().add(t, AppPath.of(finalPath + "/Normal"));

                    t = new ToolToggle("AppWindowDisplayMode.FullScreen", finalPath, app);
                    t.selected().bindEquals(app.mainFrame().get().tool().displayMode(), AppWindowDisplayMode.FULLSCREEN);
                    app.components().add(t, AppPath.of(finalPath + "/FullScreen"));

                    app.components().add(new ToolSeparator(app), AppPath.of(finalPath + "/Separator2"));

                    t = new ToolToggle("Toolbar.visible", app);
                    t.selected().bind(app.mainFrame().get().toolBar().get().tool().visible());
                    app.components().add(t, AppPath.of(finalPath + "/Toolbar"));
                    t = new ToolToggle("StatusBar.visible", app);
                    t.selected().bind(app.mainFrame().get().toolBar().get().tool().visible());
                    app.components().add(t, AppPath.of(finalPath + "/StatusBar"));
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

    public static String rawString(Str str, Application app){
        return rawString(str,app.i18n());
    }

    public static String rawString(Str str, I18n i18n){
        if(str==null){
            return "";
        }
        if(!str.is18n()){
            return str.getValue();
        }
        return i18n.getString(str.getValue());
    }
}
