package net.thevpc.echo.swing;

import net.thevpc.common.i18n.I18nString;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.ObservableValue;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.DefaultActionEvent;
import net.thevpc.echo.swing.helpers.SwingHelpers;
import net.thevpc.echo.swing.icons.SwingAppImage;
import net.thevpc.echo.swing.peers.SwingPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.TextAttribute;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        p.onChange((PropertyEvent event) -> {
            t.setVisible(event.newValue());
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
        p.onChange((PropertyEvent event) -> {
            t.setEnabled(event.newValue());
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

    public static void prepareAbstractButton(AbstractButton button, AppTextControl appComponent, Application app, boolean text) {
        SwingPeerHelper.installComponent(appComponent, button);
        WritableStr textStr = Applications.resolveTextProperty(appComponent);

        if (textStr != null) {
            if (text) {
                textStr.onChange((PropertyEvent event) -> {
                    button.setText(
                            Applications.rawString(event.newValue(), app, button.getLocale())
                    );
                });
                button.setText(Applications.rawString(textStr.get(), app, button.getLocale()));
                appComponent.locale().onChange(() -> button.setText(Applications.rawString(appComponent.text(), appComponent)));
            } else {
                button.setText(null);
                textStr.onChange((PropertyEvent event) -> {
                    button.setToolTipText(Applications.rawString(event.newValue(), app, button.getLocale()));
                });
                button.setToolTipText(Applications.rawString(textStr.get(), app, button.getLocale()));
            }
        }
        appComponent.enabled().onChange((PropertyEvent event) -> {
            button.setEnabled((Boolean) event.newValue());
        });
        button.setEnabled(appComponent.enabled().get());
        appComponent.visible().onChange((PropertyEvent event) -> {
            button.setVisible((Boolean) event.newValue());
        });
        button.setVisible(appComponent.visible().get());

        appComponent.icon().onChange((PropertyEvent event) -> {
            button.setIcon(SwingAppImage.iconOf(event.newValue()));
        });
//        appComponent.smallIcon().reevalValue();
        button.setIcon(SwingAppImage.iconOf(appComponent.icon().get()));

        appComponent.mnemonic().onChange((PropertyEvent event) -> {
            button.setMnemonic((Integer) event.newValue());
        });
        button.setMnemonic(appComponent.mnemonic().get());

        if (button instanceof JMenuItem && !(button instanceof JMenu)) {
            appComponent.accelerator().onChange((PropertyEvent event) -> {
                String s = (String) event.newValue();
                ((JMenuItem) button).setAccelerator(
                        (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
                );
            });
            String s = appComponent.accelerator().get();
            ((JMenuItem) button).setAccelerator(
                    (s == null || s.isEmpty()) ? null : KeyStroke.getKeyStroke(s)
            );
        }


        if (appComponent instanceof AppButton) {
            AppButton action = (AppButton) appComponent;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    net.thevpc.echo.api.Action a = action.action().get();
                    if (a != null) {
                        a.run(new DefaultActionEvent(app, appComponent, e.getSource(), e));
                    }
                }
            });
        }

        if (appComponent instanceof AppToggleControl) {
            ObservableValue<String> group = null;
            AppToggleControl cc = (AppToggleControl) appComponent;
            group = cc.group();
            button.setSelected(cc.selected().get());
            if (group != null) {
                String s = group.get();
                SwingApplicationToolkit tk = (SwingApplicationToolkit) app.toolkit();
                if (s != null) {
                    tk.getButtonGroup(s).add(button);
                }
                group.onChange(new PropertyListener() {
                    @Override
                    public void propertyUpdated(PropertyEvent event) {
                        if (event.oldValue() != null) {
                            tk.getButtonGroup((String) event.oldValue()).remove(button);
                        }
                        if (event.newValue() != null) {
                            tk.getButtonGroup((String) event.newValue()).add(button);
                        }
                    }
                });
            }
            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    cc.selected().set(e.getStateChange() == ItemEvent.SELECTED);
                }
            });
            cc.selected().onChange(new PropertyListener() {
                @Override
                public void propertyUpdated(PropertyEvent event) {
                    button.setSelected(event.newValue());
                }
            });
        }
    }

    public static JComponent createMenuItem(AppTextControl b, Application application) {
        AppComponent t = b;
        if (t instanceof AppContainer) {
            AppContainer a = (AppContainer) t;
            JMenu m = new JMenu();
            prepareAbstractButton(m, b, application, true);
            return m;
        }
        if (t instanceof AppToggleControl) {
            AppToggleControl a = (AppToggleControl) t;
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
        if (t instanceof AppButton) {
            AppButton a = (AppButton) t;
            JMenuItem m = new JMenuItem();
            prepareAbstractButton(m, b, application, true);
            return m;
        }
        if (t instanceof AppSeparator) {
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

    public static void updateAction(javax.swing.Action b, Application app) {
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
        app.iconSets().onChange(li);
        app.i18n().locale().onChange(li);
        updateButton(b, app);
    }

    public static void unregisterButton(AbstractButton b, Application app) {
        app.iconSets().events().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().events().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
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
        app.iconSets().onChange(li);
        app.i18n().locale().onChange(li);
        updateString(b, app);
    }

    public static void unregisterString(I18nString b, Application app) {
        app.iconSets().events().removeIf(x -> x instanceof I18nStringUpdaterPropertyListener
                && ((I18nStringUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().events().removeIf(x -> x instanceof I18nStringUpdaterPropertyListener
                && ((I18nStringUpdaterPropertyListener) x).b == b
        );
    }

    public static javax.swing.Action registerStandardAction(javax.swing.Action b, String actionId, Application app) {
        return registerAction(b, "Action." + actionId, "$Action." + actionId + ".icon", app);
    }

    public static javax.swing.Action registerAction(javax.swing.Action b, String messageId, String iconId, Application app) {
        b.putValue("icon-id", iconId);
        b.putValue("message-id", messageId);
        ActionUpdaterPropertyListener li = new ActionUpdaterPropertyListener(b, app);
        if (iconId != null) {
            app.iconSets().onChange(li);
        }
        app.i18n().locale().onChange(li);
        updateAction(b, app);
        return b;
    }

    public static javax.swing.Action updateAction(javax.swing.Action b, String messageId, String iconId, Application app) {
        b.putValue("icon-id", iconId);
        b.putValue("message-id", messageId);
        updateAction(b, app);
        return b;
    }

    public static void unregisterAction(javax.swing.Action b, Application app) {
        app.iconSets().events().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
        app.i18n().locale().events().removeIf(x -> x instanceof ButtonUpdaterPropertyListener
                && ((ButtonUpdaterPropertyListener) x).b == b
        );
    }

    public static void updateToolComponent(AppComponent subBinding, Application app) {
        String path = subBinding.path().toString();
        String s = app.i18n().getString(path, (x) -> null);
        if (s == null) {
            s = app.i18n().getString(subBinding.id(), (x) -> null);
            if (s == null) {
                LOG
                        .log(Level.FINE, "updateToolComponent failed : " + "NotFound(" + path + "," + subBinding.id() + ")");
                return;
            }
        }
        WritableStr writableStr = Applications.resolveTextProperty(subBinding);
        if (writableStr != null) {
            writableStr.set(Str.of(s));
        }
    }

    public static void bindSwingAppContent(Component jcomponent, SwingPeer aa) {
        JComponent jc = (JComponent) jcomponent;
        SwingPeer e = (SwingPeer) jc.getClientProperty(SwingPeer.class.getName());
        if (e == null) {
            jc.putClientProperty(SwingPeer.class.getName(), aa);
        }
    }

    public static void setComponentTextStrokeSize(Component component, Integer textStrokeSize) {
        //
    }

    public static Font deriveFont(Font initialFont,
                                  Boolean italic,
                                  Boolean bold,
                                  Boolean underline,
                                  Boolean strike) {
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) initialFont.getAttributes();
        boolean wasI = initialFont.isItalic();
        boolean wasB = initialFont.isBold();
        boolean wasU = TextAttribute.UNDERLINE_ON.equals(attributes.get(TextAttribute.UNDERLINE));
        boolean wasS = TextAttribute.STRIKETHROUGH_ON.equals(attributes.get(TextAttribute.STRIKETHROUGH));

        boolean i = italic == null ? wasI : italic;
        boolean b = bold == null ? wasB : bold;
        boolean u = underline == null ? wasU : underline;
        boolean s = strike == null ? wasS : strike;
        if (i == wasI && b == wasB && u == wasU && s == wasS) {
            return null;
        }
        if (i != wasI || b != wasB) {
            initialFont = initialFont.deriveFont((b ? Font.BOLD : 0) + (b ? Font.ITALIC : 0));
        }
        if (u != wasU || s != wasS) {
            if (u != wasU) {
                if (u) {
                    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                } else {
                    attributes.remove(TextAttribute.UNDERLINE);
                }
            }
            if (s != wasS) {
                if (u) {
                    attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
                } else {
                    attributes.remove(TextAttribute.STRIKETHROUGH);
                }
            }
            initialFont = initialFont.deriveFont(attributes);
        }
        return initialFont;
    }

    public static AppFont getComponentFont(Component component, Application app) {
        return SwingHelpers.fromAwtFont(component.getFont(), app);
    }

    public static void setComponentFont(Component component, AppFont initialFont,
                                        Boolean italic,
                                        Boolean bold,
                                        Boolean underline,
                                        Boolean strike) {
        Font f = initialFont == null ? null : (Font) initialFont.peer().toolkitFont();
        setComponentFont(component, f, italic, bold, underline, strike);
    }

    public static void setComponentForeground(Component component, Color color) {
        Color f = component.getForeground();
        if (!colorHashSting(f).equals(colorHashSting(color))) {
            component.setForeground(color);
        }
    }

    public static void setComponentBackground(Component component, Color color) {
        Color f = component.getForeground();
        if (!colorHashSting(f).equals(colorHashSting(color))) {
            component.setBackground(color);
        }
    }

    public static void setComponentFont(Component component, Font initialFont,
                                        Boolean italic,
                                        Boolean bold,
                                        Boolean underline,
                                        Boolean strike
    ) {
        if (initialFont == null) {
            initialFont = component.getFont();
        }
        if (initialFont == null) {
            return;
        }

        Font old = component.getFont();
        String olds = fontHashSting(old);
        String ffs = fontHashSting(initialFont);
        if (!olds.equals(ffs)) {
            Font f = SwingApplicationUtils.deriveFont(
                    initialFont,
                    null, null,
                    underline,
                    strike
            );
            if (f != null) {
                component.setFont(f);
            }
        }
    }

    public static void setLabelTextAlign(JLabel label, Anchor anchor) {
        switch (anchor) {
            case TOP: {
                label.setVerticalTextPosition(SwingConstants.TOP);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            }
            case TOP_LEFT: {
                label.setVerticalTextPosition(SwingConstants.TOP);
                label.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            }
            case LEFT: {
                label.setVerticalTextPosition(SwingConstants.CENTER);
                label.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            }
            case BOTTOM_LEFT: {
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            }
            case BOTTOM: {
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            }
            case BOTTOM_RIGHT: {
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.RIGHT);
                break;
            }
            case RIGHT: {
                label.setVerticalTextPosition(SwingConstants.CENTER);
                label.setHorizontalTextPosition(SwingConstants.RIGHT);
                break;
            }
            case TOP_RIGHT: {
                label.setVerticalTextPosition(SwingConstants.TOP);
                label.setHorizontalTextPosition(SwingConstants.RIGHT);
                break;
            }
            case CENTER: {
                label.setVerticalTextPosition(SwingConstants.CENTER);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
            }
        }
    }
    public static void setLabelTextAlign(AbstractButton label, Anchor anchor) {
        switch (anchor) {
            case TOP: {
                label.setVerticalTextPosition(SwingConstants.TOP);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            }
            case TOP_LEFT: {
                label.setVerticalTextPosition(SwingConstants.TOP);
                label.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            }
            case LEFT: {
                label.setVerticalTextPosition(SwingConstants.CENTER);
                label.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            }
            case BOTTOM_LEFT: {
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.LEFT);
                break;
            }
            case BOTTOM: {
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
                break;
            }
            case BOTTOM_RIGHT: {
                label.setVerticalTextPosition(SwingConstants.BOTTOM);
                label.setHorizontalTextPosition(SwingConstants.RIGHT);
                break;
            }
            case RIGHT: {
                label.setVerticalTextPosition(SwingConstants.CENTER);
                label.setHorizontalTextPosition(SwingConstants.RIGHT);
                break;
            }
            case TOP_RIGHT: {
                label.setVerticalTextPosition(SwingConstants.TOP);
                label.setHorizontalTextPosition(SwingConstants.RIGHT);
                break;
            }
            case CENTER: {
                label.setVerticalTextPosition(SwingConstants.CENTER);
                label.setHorizontalTextPosition(SwingConstants.CENTER);
            }
        }
    }

    public static void setLabelContentAlign(JLabel label, Anchor anchor) {
        switch (anchor) {
            case TOP: {
                label.setVerticalAlignment(SwingConstants.TOP);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            }
            case TOP_LEFT: {
                label.setVerticalAlignment(SwingConstants.TOP);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            }
            case LEFT: {
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            }
            case BOTTOM_LEFT: {
                label.setVerticalAlignment(SwingConstants.BOTTOM);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            }
            case BOTTOM: {
                label.setVerticalAlignment(SwingConstants.BOTTOM);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            }
            case BOTTOM_RIGHT: {
                label.setVerticalAlignment(SwingConstants.BOTTOM);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }
            case RIGHT: {
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }
            case TOP_RIGHT: {
                label.setVerticalAlignment(SwingConstants.TOP);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }
            case CENTER: {
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
    }
    public static void setLabelContentAlign(AbstractButton label, Anchor anchor) {
        switch (anchor) {
            case TOP: {
                label.setVerticalAlignment(SwingConstants.TOP);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            }
            case TOP_LEFT: {
                label.setVerticalAlignment(SwingConstants.TOP);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            }
            case LEFT: {
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            }
            case BOTTOM_LEFT: {
                label.setVerticalAlignment(SwingConstants.BOTTOM);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                break;
            }
            case BOTTOM: {
                label.setVerticalAlignment(SwingConstants.BOTTOM);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                break;
            }
            case BOTTOM_RIGHT: {
                label.setVerticalAlignment(SwingConstants.BOTTOM);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }
            case RIGHT: {
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }
            case TOP_RIGHT: {
                label.setVerticalAlignment(SwingConstants.TOP);
                label.setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            }
            case CENTER: {
                label.setVerticalAlignment(SwingConstants.CENTER);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
    }

    public static String colorHashSting(Color f) {
        return f == null ? "" : String.valueOf(f.getRGB());
    }

    public static String fontHashSting(Font f) {
        if (f == null) {
            return "";
        }
        String style = "";
        if (f.isBold()) {
            style += "B";
        }
        if (f.isItalic()) {
            style += "I";
        }
        Map<TextAttribute, ?> a = f.getAttributes();
        Object au = a.get(TextAttribute.UNDERLINE);
        if (TextAttribute.UNDERLINE_ON.equals(au)) {
            style += "U";
        }
        au = a.get(TextAttribute.STRIKETHROUGH);
        if (TextAttribute.STRIKETHROUGH_ON.equals(au)) {
            style += "S";
        }
        return f.getFamily() + ":" + style;
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

        private final javax.swing.Action b;
        private final Application app;

        public ActionUpdaterPropertyListener(javax.swing.Action b, Application app) {
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
        private List<javax.swing.Action> actions = new ArrayList<>();
        private List<AbstractButton> buttons = new ArrayList<>();

        public Tracker(Application app) {
            this.app = app;
        }

        public List<javax.swing.Action> getActions() {
            return actions;
        }

        public Tracker add(javax.swing.Action a) {
            actions.add(a);
            return this;
        }

        public Tracker add(AbstractButton a) {
            buttons.add(a);
            return this;
        }

        public javax.swing.Action registerStandardAction(Runnable b, String actionId) {
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

        public javax.swing.Action registerStandardAction(javax.swing.Action b, String actionId) {
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
            for (Iterator<javax.swing.Action> it = actions.iterator(); it.hasNext(); ) {
                javax.swing.Action action = it.next();
                SwingApplicationUtils.unregisterAction(action, app);
                it.remove();
            }
            for (Iterator<AbstractButton> it = buttons.iterator(); it.hasNext(); ) {
                AbstractButton button = it.next();
                SwingApplicationUtils.unregisterButton(button, app);
                it.remove();
            }
        }
    }
}
