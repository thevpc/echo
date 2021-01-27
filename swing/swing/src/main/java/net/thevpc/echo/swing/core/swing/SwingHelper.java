package net.thevpc.echo.swing.core.swing;

import net.thevpc.common.props.PValue;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.WritablePValue;
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

public class SwingHelper {

    public static void bindVisible(Component t, WritablePValue<Boolean> p) {
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

    public static void bindEnabled(Component t, WritablePValue<Boolean> p) {
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
            button.setIcon(application.iconSet().icon((String) event.getNewValue()).get());
        });
        application.iconSet().id().listeners().add((PropertyEvent event) -> {
            String sm = tool.smallIcon().get();
            button.setIcon(application.iconSet().icon(sm).get());
        });
        String sm = tool.smallIcon().get();
        button.setIcon(application.iconSet().icon(sm).get());

        PValue<String> group = null;
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
}
