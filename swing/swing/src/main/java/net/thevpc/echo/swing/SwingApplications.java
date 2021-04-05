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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Locale;
import net.thevpc.echo.swing.actions.CloseWindowsAction;
import net.thevpc.echo.swing.actions.DeiconifyWindowsAction;
import net.thevpc.echo.swing.actions.IconAction;
import net.thevpc.echo.swing.actions.IconifyWindowsAction;
import net.thevpc.echo.swing.actions.PlafAction;
import net.thevpc.echo.swing.actions.QuitAction;
import net.thevpc.echo.swing.actions.TileWindowsAction;
import net.thevpc.echo.swing.core.swing.JAppMenuBar;
import net.thevpc.echo.swing.core.swing.JAppToolBarGroup;
import net.thevpc.echo.swing.core.swing.JComponentSupplier;
import net.thevpc.echo.swing.core.swing.JFrameAppWindow;
import net.thevpc.echo.swing.core.swing.JStatusBarGroupStatusBar;
import net.thevpc.common.props.ObservableList;

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
                tools.addAction(new QuitAction(application), "/mainWindow/menuBar/File/QuitAction");
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
                tools.addAction(new TileWindowsAction(application, wins), "/mainWindow/menuBar/Windows/TileWindowsAction");
                tools.addAction(new IconifyWindowsAction(application, wins), "/mainWindow/menuBar/Windows/IconifyWindowsAction");
                tools.addAction(new DeiconifyWindowsAction(application, wins), "/mainWindow/menuBar/Windows/DeiconifyWindowsAction");
                tools.addAction(new CloseWindowsAction(application, wins), "/mainWindow/menuBar/Windows/CloseWindowsAction");
            });
        }

        public static void addViewActions(Application application) {
            application.tools().addFolder("/mainWindow/menuBar/View");
            addViewToolActions(application);
            addViewPlafActions(application);
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
            tools.addFolder(path);
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
                    list.sort((a, b) -> a.id().compareTo(b.id()));
                    for (AppToolWindow value : list) {
                        tools.addCheck(value.active(), finalPath + "/" + value.id());
                    }
                }
            });

        }

        public static void addViewIconActions(Application application) {
            addIconActions(application, null);
        }

        public static void addIconActions(Application application, String path) {
            if (path == null) {
                path = "/mainWindow/menuBar/View/Icons";
            }
            AppTools tools = application.tools();
            tools.addFolder(path);
            for (IconSet iconset : application.iconSets().values()) {
                Dimension d = iconset.getSize();
                if (d != null) {
                    if (d.width <= 16 && d.height <= 16) {
                        tools.addAction(new IconAction(application, iconset.getId()), path + "/Small/" + iconset.getId());
                    } else if (d.width <= 24 && d.height <= 24) {
                        tools.addAction(new IconAction(application, iconset.getId()), path + "/Large/" + iconset.getId());
                    } else if (d.width <= 32 && d.height <= 32) {
                        tools.addAction(new IconAction(application, iconset.getId()), path + "/Larger/" + iconset.getId());
                    } else {
                        tools.addAction(new IconAction(application, iconset.getId()), path + "/Huge/" + iconset.getId());
                    }
                } else {
                    tools.addAction(new IconAction(application, iconset.getId()), path + "/Custom/" + iconset.getId());
                }
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
            tools.addFolder(path);
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
                String nname = item.getName();
                tools.addAction(new PlafAction(application, id, nname, null, nname), path + "/" + q + "/" + pname);
                if (item.isContrast()) {
                    q = "Contrast";
                    tools.addAction(new PlafAction(application, id, nname, null, nname), path + "/" + q + "/" + pname);
                }
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
            tools.addFolder(path);
            tools.addRadio(path, application.i18n().locale(), Locale.getDefault(), path + "/system-locale");
            for (Locale locale : locales) {
                tools.addRadio(path, application.i18n().locale(), locale, path + "/" + locale + "-locale");
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
            String finalPath = path;
            runAfterStart(application, () -> {
                AppToolAction a = tools.addAction(
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
                },
                        finalPath + "/Switch"
                /*,
                        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.ALT_MASK + KeyEvent.SHIFT_MASK),
                        JComponent.WHEN_IN_FOCUSED_WINDOW*/
                );
                a.accelerator().set("alt shift F");

                tools.addSeparator(finalPath + "/Separator1");
                tools.addRadio(finalPath, application.mainWindow().get().displayMode(), AppWindowDisplayMode.NORMAL, finalPath + "/Normal");
                tools.addRadio(finalPath, application.mainWindow().get().displayMode(), AppWindowDisplayMode.FULLSCREEN, finalPath + "/FullScreen");
                tools.addSeparator(finalPath + "/Separator2");
                tools.addCheck(application.mainWindow().get().toolBar().get().visible(), finalPath + "/Toolbar");
                tools.addCheck(application.mainWindow().get().statusBar().get().visible(), finalPath + "/StatusBar");
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
