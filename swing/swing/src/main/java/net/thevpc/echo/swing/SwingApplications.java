package net.thevpc.echo.swing;

import net.thevpc.echo.*;
import net.thevpc.echo.swing.core.SwingApplication;
import net.thevpc.common.iconset.IconSet;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.swing.win.InternalWindowsHelper;
import net.thevpc.swing.plaf.UIPlaf;
import net.thevpc.swing.plaf.UIPlafManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Locale;
import net.thevpc.echo.swing.actions.CloseWindowsAction;
import net.thevpc.echo.swing.actions.DeiconifyWindowsAction;
import net.thevpc.echo.swing.actions.IconifyWindowsAction;
import net.thevpc.echo.swing.actions.QuitAction;
import net.thevpc.echo.swing.actions.TileWindowsAction;
import net.thevpc.echo.swing.core.swing.JAppMenuBar;
import net.thevpc.echo.swing.core.swing.JAppToolBarGroup;
import net.thevpc.echo.swing.core.swing.JComponentSupplier;
import net.thevpc.echo.swing.core.swing.JFrameAppWindow;
import net.thevpc.echo.swing.core.swing.JStatusBarGroupStatusBar;
import net.thevpc.common.props.ObservableList;
import net.thevpc.echo.swing.actions.FontRelativeSizeAction;
import net.thevpc.echo.swing.actions.FontSizeAbsoluteAction;
import net.thevpc.echo.swing.core.swing.JAppPopupMenu;

public class SwingApplications {

    private SwingApplications() {
    }

    public static class Apps {

        private Apps() {
        }

        public static Application Default() {
            return new SwingApplication();
        }
    }

    public static class Windows {

        private Windows() {
        }

        public static AppLayoutWindowFactory Default() {
            return JFrameAppWindow.factory();
        }
    }

    public static class MenuBars {

        private MenuBars() {
        }

        public static AppLayoutMenuBarFactory Default() {
            return JAppMenuBar.factory();
        }
    }

    public static class StatusBars {

        private StatusBars() {
        }

        public static AppLayoutStatusBarFactory Default() {
            return JStatusBarGroupStatusBar.factory();
        }
    }

    public static class ToolBars {

        private ToolBars() {
        }

        public static AppLayoutToolBarFactory Default() {
            return JAppToolBarGroup.factory();
        }
    }

    public static class Helper {

        public static void addQuitAction(Application application) {
            runAfterStart(application, () -> {
                AppTools tools = application.tools();
                tools.addAction().bind(new QuitAction(application)).path("/mainWindow/menuBar/File/QuitAction").tool();
            });
        }

        public static void addWindowsActions(Application application, JDesktopPane desktopPane) {
            runAfterStart(application, () -> {
                JDesktopPane p = desktopPane;
                InternalWindowsHelper wins = (InternalWindowsHelper) p.getClientProperty(InternalWindowsHelper.class.getName());
                if (wins == null) {
                    wins = new InternalWindowsHelper(p);
                    p.putClientProperty(InternalWindowsHelper.class.getName(), wins);
                }
                AppTools tools = application.tools();
                tools.addAction().bind(new TileWindowsAction(application, wins)).path("/mainWindow/menuBar/Windows/TileWindowsAction").tool();
                tools.addAction().bind(new IconifyWindowsAction(application, wins)).path("/mainWindow/menuBar/Windows/IconifyWindowsAction").tool();
                tools.addAction().bind(new DeiconifyWindowsAction(application, wins)).path("/mainWindow/menuBar/Windows/DeiconifyWindowsAction").tool();
                tools.addAction().bind(new CloseWindowsAction(application, wins)).path("/mainWindow/menuBar/Windows/CloseWindowsAction").tool();
            });
        }

        public static void addViewActions(Application application) {
            application.tools().addFolder("/mainWindow/menuBar/View");
            addViewToolActions(application);
            addViewPlafActions(application);
            addViewFontSizeActions(application);
            addViewIconActions(application);
            addViewAppearanceActions(application);
        }

        public static void addViewToolActions(Application application) {
            addToolActions(application, null);
        }

        public static void addToolActions(Application application, String path) {
            if (path == null) {
                path = "/mainWindow/menuBar/View/ToolWindows";
            }
            AppTools tools = application.tools();
            tools.addFolder(path).tool().smallIcon().setId("tool-windows");
            String finalPath = path;
            runAfterStart(application, () -> {
                AppWorkspace ws = application.mainWindow().get().workspace().get();
                if (ws != null && ws instanceof AppDockingWorkspace) {
                    AppDockingWorkspace dws = (AppDockingWorkspace) ws;
                    ObservableList<AppToolWindow> values = dws.toolWindows().values();
                    java.util.List<AppToolWindow> list = new ArrayList<>();
                    for (AppToolWindow value : values) {
                        list.add(value);
                    }
                    if (!list.isEmpty()) {
                        list.sort((a, b) -> a.id().compareTo(b.id()));
                        for (AppToolWindow value : list) {
                            tools.addToggle()
                                    .bind(value.active())
                                    .path(finalPath + "/" + value.id())
                                    .tool();
                        }
                    }
                }
            });

        }

        public static void addViewFontSizeActions(Application application) {
            addFontSizeRelativeActions(application, null);
            addFontSizeAbsoluteActions(application, null);
        }

        public static void addFontSizeRelativeActions(Application application, String path, float... sizes) {
            if (path == null) {
                path = "/mainWindow/menuBar/View/Font/Sizes";
            }
            if (sizes == null || sizes.length == 0) {
                sizes = new float[]{0.5f, 0.8f, 1.0f, 1.2f, 1.4f, 1.6f, 2f, 2.5f};
            }
            AppTools tools = application.tools();
            tools.addFolder(path);
            for (int i = 0; i < sizes.length; i++) {
                float size = sizes[i];
                tools.addAction().bind(new FontRelativeSizeAction(application, size)).path(path + "/*" + size).tool();
            }
//            tools.addCustomTool(path + "/**", (c)->new JSlider(0, 6));
        }

        public static void addFontSizeAbsoluteActions(Application application, String path, float... sizes) {
            if (path == null) {
                path = "/mainWindow/menuBar/View/Font/Sizes";
            }
            if (sizes == null || sizes.length == 0) {
                sizes = new float[]{8f, 10f, 12f, 14f, 16f, 18f, 20f, 28f};
            }
            AppTools tools = application.tools();
            tools.addFolder(path);
            for (int i = 0; i < sizes.length; i++) {
                float size = sizes[i];
                tools.addAction().bind(new FontSizeAbsoluteAction(application, size)).path(path + "/" + size).tool();
            }
        }

        public static void addViewIconActions(Application application) {
            addIconActions(application, null);
        }

        public static void addIconActions(Application application, String path, int... sizes) {
            AppTools tools = application.tools();
            if (path == null) {
                path = "/mainWindow/menuBar/View/Icons";
            }
            tools.addFolder(path).tool().smallIcon().setId("icons");
            tools.addFolder(path + "/Packs");
            for (IconSet iconset : application.iconSets().values()) {
                tools.<String>addToggle().buttonType(AppToolButtonType.RADIO)
                        .id("IconSet." + iconset.getId())
                        .group(path + "/Packs")
                        .bind(application.iconSets().id(), iconset.getId())
                        .path(path + "/Packs/" + iconset.getId())
                        .tool();
            }
            if (sizes == null || sizes.length == 0) {
                sizes = new int[]{8, 16, 24, 32, 48};
            }
            tools.addFolder(path + "/Sizes");
            for (int i = 0; i < sizes.length; i++) {
                int size = sizes[i];
                tools.addToggle().buttonType(AppToolButtonType.RADIO)
                        .group(path + "/Sizes")
                        .path(path + "/Sizes/" + size)
                        .bind(new AppToolToggleModel() {
                            @Override
                            public boolean isSelected() {
                                return size == (application.iconSets().config().get() == null ? 16 : application.iconSets().config().get().getWidth());
                            }

                            @Override
                            public void setSelected(boolean b) {
                                if (b) {
                                    application.iconSets().config().set(
                                            application.iconSets().config().get().setSize(size)
                                    );
                                }
                            }
                        })
;
            }
        }

        public static void addViewPlafActions(Application application) {
            addPlafActions(application, null);
        }

        public static void addPlafActions(Application application, String path) {
            if (path == null) {
                path = "/mainWindow/menuBar/View/Plaf";
            }
            AppTools tools = application.tools();
            tools.addFolder(path).tool().smallIcon().setId("themes");
            for (UIPlaf item : UIPlafManager.INSTANCE.items()) {
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
                AppToolToggle tool = tools.<String>addToggle().buttonType(AppToolButtonType.RADIO)
                        .id("Plaf." + id)
                        .group(path)
                        .bind(((SwingApplication) application).plaf(), id)
                        .path(path + "/" + q + "/" + pname)
                        .tool();
                tool.smallIcon().setId(null);//remove icon binding...
            }
        }

        public static void addViewLocaleActions(Application application, Locale[] locales) {
            addViewLocaleActions(application, locales, null);
        }

        public static void addViewLocaleActions(Application application, Locale[] locales, String path) {
            if (path == null) {
                path = "/mainWindow/menuBar/View/Lang";
            }
            AppTools tools = application.tools();
            tools.addFolder(path).tool().smallIcon().setId("locales");
            tools.addToggle().buttonType(AppToolButtonType.RADIO)
                    .id("Locale.System")
                    .group(path)
                    .bind(application.i18n().locale(), Locale.getDefault())
                    .path(path + "/system-locale")
                    .tool();
            for (Locale locale : locales) {
                tools.addToggle().buttonType(AppToolButtonType.RADIO)
                        .id("Locale." + locale.toString())
                        .group(path)
                        .bind(application.i18n().locale(), locale)
                        .path(path + "/" + locale + "-locale")
                        .tool();
            }
        }

        public static void addViewAppearanceActions(Application application) {
            addAppearanceActions(application, null);
        }

        public static void addAppearanceActions(Application application, String path) {
            AppTools tools = application.tools();
            if (path == null) {
                path = "/mainWindow/menuBar/View/Appearance";
            }
            tools.addFolder(path).tool().smallIcon().setId("appareance");
            String finalPath = path;
            runAfterStart(application, () -> {
                AppToolAction a = tools.addAction().bind(
                        new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AppWindow w = application.mainWindow().get();
                        AppWindowDisplayMode dm = w.displayMode().get();
                        if (dm == null) {
                            dm = AppWindowDisplayMode.NORMAL;
                        }
                        switch (dm) {
                            case NORMAL: {
                                w.displayMode().set(AppWindowDisplayMode.FULLSCREEN);
                                break;
                            }
                            default: {
                                w.displayMode().set(AppWindowDisplayMode.NORMAL);
                                break;
                            }
                        }
                    }
                }).path(
                                finalPath + "/Switch"
                        /*,
                        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK),
                        JComponent.WHEN_IN_FOCUSED_WINDOW*/
                        ).tool();
                a.accelerator().set("alt shift F");

                tools.addSeparator(finalPath + "/Separator1");
                tools.addToggle().buttonType(AppToolButtonType.RADIO)
                        .id("AppWindowDisplayMode.Normal")
                        .group(finalPath)
                        .bind(application.mainWindow().get().displayMode(), AppWindowDisplayMode.NORMAL)
                        .path(finalPath + "/Normal")
                        .tool();
                tools.<AppWindowDisplayMode>addToggle().buttonType(AppToolButtonType.RADIO)
                        .id("AppWindowDisplayMode.FullScreen")
                        .group(finalPath)
                        .bind(application.mainWindow().get().displayMode(), AppWindowDisplayMode.FULLSCREEN)
                        .path(finalPath + "/FullScreen")
                        .tool();

                tools.addSeparator(finalPath + "/Separator2");
                tools.addToggle()
                        .bind(application.mainWindow().get().toolBar().get().visible())
                        .path(finalPath + "/Toolbar")
                        .tool();
                tools.addToggle()
                        .bind(application.mainWindow().get().statusBar().get().visible())
                        .path(finalPath + "/StatusBar")
                        .tool();
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

    public static class Workspaces {

        private Workspaces() {
        }

        public static AppLayoutWorkspaceFactory Default() {
            return new DefaultAppLayoutWorkspaceFactory(new JPanel());
        }

        public static AppLayoutWorkspaceFactory Default(JComponent component) {
            return new DefaultAppLayoutWorkspaceFactory(component == null ? new JPanel() : component);
        }

        public static ComponentAppWorkspace Component(JComponent component) {
            return new ComponentAppWorkspace(component == null ? new JPanel() : component);
        }

    }

    public static class Components {

        private Components() {
        }

        public static AppMenuBar createMenuBar(Application app) {
            return createMenuBar(app, "/");
        }

        public static AppPopupMenu createPopupMenu(Application app) {
            return createPopupMenu(app, "/");
        }

        public static AppToolBar createToolBar(Application app) {
            return createToolBar(app, "/");
        }

        public static AppMenuBar createMenuBar(Application app, String rootPath) {
            return new JAppMenuBar(rootPath, app);
        }

        public static AppPopupMenu createPopupMenu(Application app, String rootPath) {
            return new JAppPopupMenu(rootPath, app);
        }

        public static AppToolBar createToolBar(Application app, String rootPath) {
            return new JAppToolBarGroup(rootPath, app);
        }

    }

    private static class DefaultAppLayoutWorkspaceFactory implements AppLayoutWorkspaceFactory {

        private JComponent component;

        public DefaultAppLayoutWorkspaceFactory(JComponent component) {
            this.component = component;
        }

        @Override
        public AppWorkspace createWorkspace(AppWindow window) {
            return new ComponentAppWorkspace(component);
        }
    }

    private static class ComponentAppWorkspace implements AppWorkspace, JComponentSupplier {

        private JComponent component;

        public ComponentAppWorkspace(JComponent component) {
            this.component = component;
        }

        @Override
        public JComponent component() {
            return component;
        }
    }
}
