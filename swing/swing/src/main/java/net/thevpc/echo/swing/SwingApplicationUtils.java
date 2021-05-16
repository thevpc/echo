package net.thevpc.echo.swing;

import net.thevpc.common.props.*;
import net.thevpc.echo.api.AppImage;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppAction;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.tools.*;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.swing.icons.SwingAppImage;
import net.thevpc.echo.*;
import net.thevpc.echo.impl.DefaultAppActionEvent;

import java.awt.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.thevpc.common.i18n.I18nString;
import net.thevpc.echo.swing.peers.SwingPeer;

public class SwingApplicationUtils {

    private static final Logger LOG = Logger.getLogger(SwingApplicationUtils.class.getName());

    public static void bindVisible(Component t, WritableBoolean p) {
        t.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            switch (evt.getPropertyName()) {
                case "visible": {
                    p.set((Boolean) evt.getNewValue());
                    break;
                }
            }
        });
        p.listeners().add((PropertyEvent event) -> {
            t.setVisible(event.getNewValue());
        });
        p.set(t.isVisible());
    }

    public static void bindEnabled(Component t, WritableBoolean p) {
        t.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            switch (evt.getPropertyName()) {
                case "enabled": {
                    p.set((Boolean) evt.getNewValue());
                    break;
                }
            }
        });
        p.listeners().add((PropertyEvent event) -> {
            t.setEnabled(event.getNewValue());
        });
        p.set(t.isEnabled());
    }

    public static void invokeLong(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            new Thread(r).run();
        }
        r.run();
    }

    public static void invokeLater(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        }
        SwingUtilities.invokeLater(r);
    }

    public static void invokeAndWait(Runnable r) {
        if (SwingUtilities.isEventDispatchThread()) {
            r.run();
        }
        try {
            SwingUtilities.invokeAndWait(r);
        } catch (InterruptedException | InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void prepareAbstractButton(AbstractButton button, AppComponent binding, Application application, boolean text) {
        prepareAbstractButton(button, binding.tool(), application, text);
    }

    public static void prepareAbstractButton(AbstractButton button, AppTool tool, Application app, boolean text) {
        if (text) {
            tool.title().listeners().add((PropertyEvent event) -> {
                button.setText(
                        Applications.rawString(event.getNewValue(),app)
                );
            });
            button.setText(Applications.rawString(tool.title().get(),app));
        } else {
            button.setText(null);
            tool.title().listeners().add((PropertyEvent event) -> {
                button.setToolTipText(Applications.rawString(event.getNewValue(),app));
            });
            button.setToolTipText(Applications.rawString(tool.title().get(),app));
        }
        tool.enabled().listeners().add((PropertyEvent event) -> {
            button.setEnabled((Boolean) event.getNewValue());
        });
        button.setEnabled(tool.enabled().get());
        tool.visible().listeners().add((PropertyEvent event) -> {
            button.setVisible((Boolean) event.getNewValue());
        });
        button.setVisible(tool.visible().get());

        tool.smallIcon().listeners().add((PropertyEvent event) -> {
            button.setIcon(SwingAppImage.iconOf(event.getNewValue()));
        });
//        tool.smallIcon().reevalValue();
        button.setIcon(SwingAppImage.iconOf(tool.smallIcon().get()));

        tool.mnemonic().listeners().add((PropertyEvent event) -> {
            button.setMnemonic((Integer) event.getNewValue());
        });
        button.setMnemonic(tool.mnemonic().get());

        if (button instanceof JMenuItem && !(button instanceof JMenu)) {
            tool.accelerator().listeners().add((PropertyEvent event) -> {
                String s = (String) event.getNewValue();
                ((JMenuItem) button).setAccelerator(
                        (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
                );
            });
            String s = tool.accelerator().get();
            ((JMenuItem) button).setAccelerator(
                    (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
            );
        }

        ObservableValue<String> group = null;
        if (tool instanceof AppToolToggle) {
            AppToolToggle cc = (AppToolToggle) tool;
            group = cc.group();
            button.setSelected(cc.selected().get());
        }
        if (group != null) {
            String s = group.get();
            SwingApplication swingApp = (SwingApplication) app;
            SwingApplicationToolkit tk = (SwingApplicationToolkit) swingApp.toolkit();
            if (s != null) {
                tk.getButtonGroup(s).add(button);
            }
            group.listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    if (event.getOldValue() != null) {
                        tk.getButtonGroup((String) event.getOldValue()).remove(button);
                    }
                    if (event.getNewValue() != null) {
                        tk.getButtonGroup((String) event.getNewValue()).add(button);
                    }
                }
            });
        }

        if (tool instanceof AppToolAction) {
            AppToolAction action = (AppToolAction) tool;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    AppAction a = action.action().get();
                    if (a != null) {
                        a.run(new DefaultAppActionEvent(app, tool, e.getSource(), e));
                    }
                }
            });
        }

        if (tool instanceof AppToolToggle) {
            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    ((AppToolToggle) tool).selected().set(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            button.setSelected(((AppToolToggle) tool).selected().get());
            ((AppToolToggle) tool).selected().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    button.setSelected(event.getNewValue());
                }
            });
        }
    }

    public static JComponent createMenuItem(AppComponent b, Application application) {
        AppTool t = b.tool();
        if (t instanceof AppToolFolder) {
            AppToolFolder a = (AppToolFolder) t;
            JMenu m = new JMenu();
            prepareAbstractButton(m, b, application, true);
            return m;
        }
        if (t instanceof AppToolToggle) {
            AppToolToggle a = (AppToolToggle) t;
            JCheckBoxMenuItem m = new JCheckBoxMenuItem();
            m.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    a.selected().set(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            prepareAbstractButton(m, b, application, true);
            return m;
        }
        if (t instanceof AppToolAction) {
            AppToolAction a = (AppToolAction) t;
            JMenuItem m = new JMenuItem();
            prepareAbstractButton(m, b, application, true);
            return m;
        }
        if (t instanceof AppToolSeparator) {
            return new JPopupMenu.Separator();
        }
        throw new IllegalArgumentException("Unsupported");
    }

    /////////////////////////////////////////////////////////////////////////
    public static void updateButton(AbstractButton b, Application app) {
        String iconId = (String) b.getClientProperty("icon-id");
        if (iconId != null) {
            if (iconId.startsWith("$")) {
                String id2 = app.i18n().getString(iconId.substring(1), x -> null);
                if (id2 != null) {
                    iconId = id2;
                } else {
                    LOG.log(Level.FINER, "missing button icon message binding for {0}", iconId);
                }
            }
            AppImage aim = app.iconSets().icon(iconId).get();
            b.setIcon(aim == null ? null : SwingAppImage.iconOf(aim));
        }
        String messageId = (String) b.getClientProperty("message-id");
        if (messageId != null) {
            b.setText(app.i18n().getString(messageId));
        }
    }

    public static void updateAction(Action b, Application app) {
        String iconId = (String) b.getValue("icon-id");
        if (iconId != null) {
            if (iconId.startsWith("$")) {
                String id2 = app.i18n().getString(iconId.substring(1), x -> null);
                if (id2 != null) {
                    iconId = id2;
                } else {
                    LOG.log(Level.FINER, "missing action icon message binding for {0}", iconId);
                }
            }
            b.putValue(AbstractAction.SMALL_ICON, SwingAppImage.iconOf(app.iconSets().icon(iconId).get()));
        }
        String messageId = (String) b.getValue("message-id");
        if (messageId != null) {
            String txt = app.i18n().getString(messageId);
            b.putValue(AbstractAction.NAME, txt);
            String s = app.i18n().getString(messageId + ".tooltip", (n) -> null);
            if (s == null) {
                s = txt;
            }
            b.putValue(AbstractAction.SHORT_DESCRIPTION, s);
        }
    }

    public static void registerstandardButton(AbstractButton b, String id, Application app) {
        registerButton(b, id, "$" + id + ".icon", app);
    }

    public static void registerButton(AbstractButton b, String messageId, String iconId, Application app) {
        b.putClientProperty("icon-id", iconId);
        b.putClientProperty("message-id", messageId);
        ButtonUpdaterPropertyListener li = new ButtonUpdaterPropertyListener(b, app);
        app.iconSets().listeners().add(li);
        app.i18n().locale().listeners().add(li);
        updateButton(b, app);
    }

    public static void unregisterButton(AbstractButton b, Application app) {
        app.iconSets().listeners().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().listeners().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
    }

    public static void updateString(I18nString b, Application app) {
        b.setI18n(app.i18n());
    }

    public static I18nString registerString(String b, Application app) {
        I18nString s = new I18nString(b);
        registerString(s, app);
        return s;
    }

    public static void registerString(I18nString b, Application app) {
        I18nStringUpdaterPropertyListener li = new I18nStringUpdaterPropertyListener(b, app);
        app.iconSets().listeners().add(li);
        app.i18n().locale().listeners().add(li);
        updateString(b, app);
    }

    public static void unregisterString(I18nString b, Application app) {
        app.iconSets().listeners().removeIf(x -> x instanceof I18nStringUpdaterPropertyListener
                && ((I18nStringUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().listeners().removeIf(x -> x instanceof I18nStringUpdaterPropertyListener
                && ((I18nStringUpdaterPropertyListener) x).b == b
        );
    }

    public static Action registerStandardAction(Action b, String actionId, Application app) {
        return registerAction(b, "Action." + actionId, "$Action." + actionId + ".icon", app);
    }

    public static Action registerAction(Action b, String messageId, String iconId, Application app) {
        b.putValue("icon-id", iconId);
        b.putValue("message-id", messageId);
        ActionUpdaterPropertyListener li = new ActionUpdaterPropertyListener(b, app);
        if (iconId != null) {
            app.iconSets().listeners().add(li);
        }
        app.i18n().locale().listeners().add(li);
        updateAction(b, app);
        return b;
    }

    public static Action updateAction(Action b, String messageId, String iconId, Application app) {
        b.putValue("icon-id", iconId);
        b.putValue("message-id", messageId);
        updateAction(b, app);
        return b;
    }

    public static void unregisterAction(Action b, Application app) {
        app.iconSets().listeners().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().listeners().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
    }

    public static void updateToolComponent(AppComponent subBinding, Application app) {
        String path = subBinding.path().toString();
        String s = app.i18n().getString(path, (x) -> null);
        if (s == null) {
            s = app.i18n().getString(subBinding.tool().id(), (x) -> null);
            if (s == null) {
                LOG
                        .log(Level.FINE, "updateToolComponent failed : " + "NotFound(" + path + "," + subBinding.tool().id() + ")");
                return;
            }
        }
        subBinding.tool().title().set(Str.of(s));
    }

    public static void bindSwingAppContent(Component jcomponent, SwingPeer aa) {
        JComponent jc = (JComponent) jcomponent;
        SwingPeer e = (SwingPeer) jc.getClientProperty(SwingPeer.class.getName());
        if (e == null) {
            jc.putClientProperty(SwingPeer.class.getName(), aa);
        }
    }

    private static class I18nStringUpdaterPropertyListener implements PropertyListener {

        private final I18nString b;
        private final Application app;

        public I18nStringUpdaterPropertyListener(I18nString b, Application app) {
            this.b = b;
            this.app = app;
        }

        @Override
        public void propertyUpdated(PropertyEvent event) {
            updateString(b, app);
        }
    }

    private static class ButtonUpdaterPropertyListener implements PropertyListener {

        private final AbstractButton b;
        private final Application app;

        public ButtonUpdaterPropertyListener(AbstractButton b, Application app) {
            this.b = b;
            this.app = app;
        }

        @Override
        public void propertyUpdated(PropertyEvent event) {
            updateButton(b, app);
        }
    }

    private static class ActionUpdaterPropertyListener implements PropertyListener {

        private final Action b;
        private final Application app;

        public ActionUpdaterPropertyListener(Action b, Application app) {
            this.b = b;
            this.app = app;
        }

        @Override
        public void propertyUpdated(PropertyEvent event) {
            updateAction(b, app);
        }
    }

    public static class Tracker {

        private Application app;
        private List<Action> actions = new ArrayList<>();
        private List<AbstractButton> buttons = new ArrayList<>();

        public Tracker(Application app) {
            this.app = app;
        }

        public List<Action> getActions() {
            return actions;
        }

        public Tracker add(Action a) {
            actions.add(a);
            return this;
        }

        public Tracker add(AbstractButton a) {
            buttons.add(a);
            return this;
        }

        public Action registerStandardAction(Runnable b, String actionId) {
            AbstractAction a = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    b.run();
                }
            };
            SwingApplicationUtils.registerStandardAction(a, actionId, app);
            actions.add(a);
            return a;
        }

        public Action registerStandardAction(Action b, String actionId) {
            SwingApplicationUtils.registerStandardAction(b, actionId, app);
            actions.add(b);
            return b;
        }

        public <T extends AbstractButton> T registerStandardButton(T b, String actionId) {
            SwingApplicationUtils.registerstandardButton(b, actionId, app);
            buttons.add(b);
            return b;
        }

        public void unregisterAll() {
            for (Iterator<Action> it = actions.iterator(); it.hasNext();) {
                Action action = it.next();
                SwingApplicationUtils.unregisterAction(action, app);
                it.remove();
            }
            for (Iterator<AbstractButton> it = buttons.iterator(); it.hasNext();) {
                AbstractButton button = it.next();
                SwingApplicationUtils.unregisterButton(button, app);
                it.remove();
            }
        }
    }
}
