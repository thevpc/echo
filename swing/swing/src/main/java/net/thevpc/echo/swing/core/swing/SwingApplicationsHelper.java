package net.thevpc.echo.swing.core.swing;

import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.echo.AppToolAction;
import net.thevpc.echo.AppToolSeparator;
import net.thevpc.echo.AppToolRadioBox;
import net.thevpc.echo.Application;
import net.thevpc.echo.AppToolComponent;
import net.thevpc.echo.AppTool;
import net.thevpc.echo.AppToolCheckBox;
import net.thevpc.echo.AppToolFolder;
import java.awt.Component;

import net.thevpc.echo.swing.core.DefaultApplication;

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
import net.thevpc.common.props.WritableValue;
import net.thevpc.common.props.ObservableValue;

public class SwingApplicationsHelper {
    private static final Logger LOG = Logger.getLogger(SwingApplicationsHelper.class.getName());

    public static void bindVisible(Component t, WritableValue<Boolean> p) {
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

    public static void bindEnabled(Component t, WritableValue<Boolean> p) {
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

    public static void prepareJCombobox(JComboBox button, AppToolComponent binding, Application application) {

    }

    public static void prepareAbstractButton(AbstractButton button, AppToolComponent binding, Application application, boolean text) {
        AppTool tool = binding.tool();
        if (text) {
            tool.title().listeners().add((PropertyEvent event) -> {
                button.setText((String) event.getNewValue());
            });
            button.setText(tool.title().get());
        } else {
            button.setText(null);
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
            button.setIcon((Icon) event.getNewValue());
        });
//        tool.smallIcon().reevalValue();
        button.setIcon(tool.smallIcon().get());

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
        if (tool instanceof AppToolCheckBox) {
            AppToolCheckBox cc = (AppToolCheckBox) tool;
            group = cc.group();
            button.setSelected(cc.selected().get());
        }
        if (tool instanceof AppToolRadioBox) {
            AppToolRadioBox cc = (AppToolRadioBox) tool;
            group = cc.group();
            button.setSelected(cc.selected().get());
        }
        if (group != null) {
            String s = group.get();
            if (s != null) {
                ((DefaultApplication) application).getButtonGroup(s).add(button);
            }
            group.listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    if (event.getOldValue() != null) {
                        ((DefaultApplication) application).getButtonGroup((String) event.getOldValue()).remove(button);
                    }
                    if (event.getNewValue() != null) {
                        ((DefaultApplication) application).getButtonGroup((String) event.getNewValue()).add(button);
                    }
                }
            });
        }

        if (tool instanceof AppToolAction) {
            AppToolAction action = (AppToolAction) tool;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ActionListener a = action.action().get();
                    if (a != null) {
                        a.actionPerformed(e);
                    }
                }
            });
        }

        if (tool instanceof AppToolCheckBox) {
            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    ((AppToolCheckBox) tool).selected().set(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            button.setSelected(((AppToolCheckBox) tool).selected().get());
            ((AppToolCheckBox) tool).selected().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    button.setSelected(event.getNewValue());
                }
            });
        }
        if (tool instanceof AppToolRadioBox) {
            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    ((AppToolRadioBox) tool).selected().set(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            button.setSelected(((AppToolRadioBox) tool).selected().get());
            ((AppToolRadioBox) tool).selected().listeners().add(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    button.setSelected(event.getNewValue());
                }
            });
        }
    }

    public static JComponent createMenuItem(AppToolComponent b, Application application) {
        AppTool t = b.tool();
        if (t instanceof AppToolFolder) {
            AppToolFolder a = (AppToolFolder) t;
            JMenu m = new JMenu();
            prepareAbstractButton(m, b, application, true);
            return m;
        }
        if (t instanceof AppToolRadioBox) {
            AppToolRadioBox a = (AppToolRadioBox) t;
            JRadioButtonMenuItem m = new JRadioButtonMenuItem();
            m.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    a.selected().set(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            prepareAbstractButton(m, b, application, true);
            return m;
        }
        if (t instanceof AppToolCheckBox) {
            AppToolCheckBox a = (AppToolCheckBox) t;
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
                }else{
                    LOG.log(Level.FINER, "missing button icon message binding for {0}", iconId);
                }
            }
            b.setIcon(app.iconSet().icon(iconId).get());
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
                }else{
                    LOG.log(Level.FINER, "missing action icon message binding for {0}", iconId);
                }
            }
            b.putValue(AbstractAction.SMALL_ICON, app.iconSet().icon(iconId).get());
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
        app.iconSet().id().listeners().add(li);
        app.i18n().locale().listeners().add(li);
        updateButton(b, app);
    }

    public static void unregisterButton(AbstractButton b, Application app) {
        app.iconSet().id().listeners().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
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
        app.iconSet().id().listeners().add(li);
        app.i18n().locale().listeners().add(li);
        updateString(b, app);
    }

    public static void unregisterString(I18nString b, Application app) {
        app.iconSet().id().listeners().removeIf(x -> x instanceof I18nStringUpdaterPropertyListener
                && ((I18nStringUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().listeners().removeIf(x -> x instanceof I18nStringUpdaterPropertyListener
                && ((I18nStringUpdaterPropertyListener) x).b == b
        );
    }

    public static void registerStandardAction(Action b, String actionId, Application app) {
        registerAction(b, "Action." + actionId, "$Action." + actionId + ".icon", app);
    }

    public static Action registerAction(Action b, String messageId, String iconId, Application app) {
        b.putValue("icon-id", iconId);
        b.putValue("message-id", messageId);
        ActionUpdaterPropertyListener li = new ActionUpdaterPropertyListener(b, app);
        app.iconSet().id().listeners().add(li);
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
        app.iconSet().id().listeners().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().listeners().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
    }

    public static void updateToolComponent(AppToolComponent subBinding, Application app) {
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
        subBinding.tool().title().set(s);
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
            SwingApplicationsHelper.registerStandardAction(a, actionId, app);
            actions.add(a);
            return a;
        }

        public Action registerStandardAction(Action b, String actionId) {
            SwingApplicationsHelper.registerStandardAction(b, actionId, app);
            actions.add(b);
            return b;
        }

        public <T extends AbstractButton> T registerStandardButton(T b, String actionId) {
            SwingApplicationsHelper.registerstandardButton(b, actionId, app);
            buttons.add(b);
            return b;
        }

        public void unregisterAll() {
            for (Iterator<Action> it = actions.iterator(); it.hasNext();) {
                Action action = it.next();
                SwingApplicationsHelper.unregisterAction(action, app);
                it.remove();
            }
            for (Iterator<AbstractButton> it = buttons.iterator(); it.hasNext();) {
                AbstractButton button = it.next();
                SwingApplicationsHelper.unregisterButton(button, app);
                it.remove();
            }
        }
    }
}
