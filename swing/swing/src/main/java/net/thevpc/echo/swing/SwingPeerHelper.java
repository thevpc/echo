package net.thevpc.echo.swing;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.common.props.PropertyEvent;
import net.thevpc.common.props.PropertyListener;
import net.thevpc.common.props.PropertyUpdate;
import net.thevpc.echo.Application;
import net.thevpc.echo.KeyCode;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.DefaultAppComponentEvents;
import net.thevpc.echo.spi.peers.AppComponentPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.*;

public class SwingPeerHelper {
    public static final MouseListener FROM_SWING_MOUSE_LISTENER = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_CLICKED, e, appComponent));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_PRESSED, e, appComponent));
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_RELEASED, e, appComponent));
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_ENTER, e, appComponent));
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_EXIT, e, appComponent));
        }
    };
    public static final MouseWheelListener FROM_SWING_MOUSE_WHEEL_LISTENER = new MouseWheelListener() {
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_WHEEL_MOVED, e, appComponent));
        }
    };
    public static final MouseMotionListener FROM_SWING_MOUSE_MOTION_LISTENER = new MouseMotionListener() {
        @Override
        public void mouseDragged(MouseEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_DRAGGED, e, appComponent));
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            DefaultAppComponentEvents events = (DefaultAppComponentEvents) appComponent.events();
            events.fire(new SwingComponentEvent(AppEventType.MOUSE_MOVED, e, appComponent));
        }
    };
    private static final ComponentListener FROM_SWING_COMPONENT_MAPPER = new ComponentListener() {
        @Override
        public void componentResized(ComponentEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            Rectangle bounds = swingComponent.getBounds();
            appComponent.bounds().set(SwingHelpers.fromAwtBounds(bounds));
        }

        @Override
        public void componentMoved(ComponentEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            Rectangle bounds = swingComponent.getBounds();
            appComponent.bounds().set(SwingHelpers.fromAwtBounds(bounds));
        }

        @Override
        public void componentShown(ComponentEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            appComponent.shown().set(true);
        }

        @Override
        public void componentHidden(ComponentEvent e) {
            JComponent swingComponent = (JComponent) e.getComponent();
            AppComponent appComponent = appComponentOf(swingComponent);
            appComponent.shown().set(false);
        }
    };
    private static final KeyListener FROM_SWING_KEY_MAPPER = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            AppComponent appComponent = appComponentOf((JComponent) e.getComponent());
            DefaultAppComponentEvents de = (DefaultAppComponentEvents) appComponent.events();
            de.fire(new SwingComponentEvent(AppEventType.KEY_TYPED, e, appComponent));
        }

        @Override
        public void keyPressed(KeyEvent e) {
            AppComponent appComponent = appComponentOf((JComponent) e.getComponent());
            DefaultAppComponentEvents de = (DefaultAppComponentEvents) appComponent.events();
            de.fire(new SwingComponentEvent(AppEventType.KEY_PRESSED, e, appComponent));
        }

        @Override
        public void keyReleased(KeyEvent e) {
            AppComponent appComponent = appComponentOf((JComponent) e.getComponent());
            DefaultAppComponentEvents de = (DefaultAppComponentEvents) appComponent.events();
            de.fire(new SwingComponentEvent(AppEventType.KEY_RELEASED, e, appComponent));
        }
    };
    private static final FocusListener FROM_SWING_FOCUS_MAPPER = new FocusListener() {
        @Override
        public void focusGained(FocusEvent e) {
            AppComponent appComponent = appComponentOf((JComponent) e.getComponent());
            appComponent.focused().set(true);
        }

        @Override
        public void focusLost(FocusEvent e) {
            AppComponent appComponent = appComponentOf((JComponent) e.getComponent());
            appComponent.focused().set(false);
        }
    };

    public static void installTextComponent(AppEditTextControl appComponent, JTextComponent swingComponent) {
        new InstallTextComponent(appComponent, swingComponent).install();
    }

    public static AppComponent appComponentOf(JComponent swingComponent) {
        return (AppComponent) swingComponent.getClientProperty(AppComponent.class.getName());
    }

    public static JComponent effectiveSwingComponent(AppComponent component) {
        component.peer();
        return (JComponent) component.userObjects().get(AppComponentPeer.class.getName() + ":effective");
    }

    public static void installComponent(AppComponent appComponent, JComponent swingComponent) {
        appComponent.userObjects().put(AppComponentPeer.class.getName() + ":effective", swingComponent);
        swingComponent.putClientProperty("toolkit-impl-type", appComponent.getClass().getSimpleName());
        boolean separator = swingComponent instanceof Box.Filler || swingComponent instanceof JSeparator;
        swingComponent.putClientProperty(AppComponent.class.getName(), appComponent);
        if (!separator) {
            swingComponent.addKeyListener(FROM_SWING_KEY_MAPPER);
            swingComponent.addFocusListener(FROM_SWING_FOCUS_MAPPER);
            appComponent.prefSize().onChangeAndInit(
                    (ee) -> {
                        swingComponent.setPreferredSize(SwingHelpers.toAwtDimension(appComponent.prefSize().get()));
                    }
            );

            //Property Opaque
            appComponent.opaque().onChangeAndInit(() -> swingComponent.setOpaque(appComponent.opaque().get()));

            //Property Background
            if (appComponent.backgroundColor().get() == null) {
                appComponent.backgroundColor().set(SwingHelpers.fromAwtColor(swingComponent.getBackground(), appComponent.app()));
            } else {
                Color c = SwingHelpers.toAwtColor(appComponent.backgroundColor().get(), swingComponent, SwingHelpers.DEF_COLOR_BACKGROUND);
                SwingApplicationUtils.setComponentBackground(swingComponent, c);
            }
            appComponent.backgroundColor().onChange(
                    () -> {
                        Color c = SwingHelpers.toAwtColor(appComponent.backgroundColor().get(), swingComponent, SwingHelpers.DEF_COLOR_BACKGROUND);
                        SwingApplicationUtils.setComponentBackground(swingComponent, c);
                    }
            );

            //Property Foreground
            if (appComponent.foregroundColor().get() == null) {
                appComponent.foregroundColor().set(SwingHelpers.fromAwtColor(swingComponent.getForeground(), appComponent.app()));
            } else {
                Color c = SwingHelpers.toAwtColor(appComponent.foregroundColor().get(), swingComponent, SwingHelpers.DEF_COLOR_FOREGROUND);
                SwingApplicationUtils.setComponentForeground(swingComponent, c);
            }
            appComponent.foregroundColor().onChange(
                    () -> {
                        Color c = SwingHelpers.toAwtColor(appComponent.foregroundColor().get(), swingComponent, SwingHelpers.DEF_COLOR_FOREGROUND);
                        SwingApplicationUtils.setComponentForeground(swingComponent, c);
                    }
            );

            if (appComponent instanceof AppTextControl) {
                AppTextControl tc = (AppTextControl) appComponent;
                AppComponentTextStylerApplier appComponentTextStylerApplier = new AppComponentTextStylerApplier(tc);
                AppFont afont = tc.textStyle().font().get();
                if (afont != null) {
                    appComponentTextStylerApplier.propertyUpdated(null);
                } else {
                    tc.textStyle().font().set(
                            SwingHelpers.fromAwtFont(swingComponent.getFont(), appComponent.app())
                    );
                }
                tc.textStyle().font().onChange(appComponentTextStylerApplier);
                tc.textStyle().strokeSize().onChange(appComponentTextStylerApplier); // no init because already initialize
                tc.textStyle().strikethrough().onChange(appComponentTextStylerApplier);
                tc.textStyle().underline().onChange(appComponentTextStylerApplier);
            }
        }
        swingComponent.addComponentListener(FROM_SWING_COMPONENT_MAPPER);
        swingComponent.addMouseListener(FROM_SWING_MOUSE_LISTENER);
        swingComponent.addMouseWheelListener(FROM_SWING_MOUSE_WHEEL_LISTENER);
        swingComponent.addMouseMotionListener(FROM_SWING_MOUSE_MOTION_LISTENER);

        appComponent.visible().onChangeAndInit(
                () -> swingComponent.setVisible(appComponent.visible().get())
        );
        appComponent.enabled().onChangeAndInit(
                () -> swingComponent.setEnabled(appComponent.enabled().get())
        );
        appComponent.tooltip().onChangeAndInit(
                () -> swingComponent.setToolTipText(
                        Applications.rawString(appComponent.tooltip(), appComponent)
                )
        );
        appComponent.contextMenu().onChangeAndInit(
                () -> {
                    AppContextMenu g = appComponent.contextMenu().get();
                    if (g == null) {
                        swingComponent.setComponentPopupMenu(null);
                    } else {
                        swingComponent.setComponentPopupMenu((JPopupMenu) effectiveSwingComponent(g));
                    }
                }
        );
        appComponent.onChange(e -> {
            if ("UpdateUI".equals(e.changeId()) || e.eventType() == PropertyUpdate.REFRESH) {
                swingComponent.invalidate();
                swingComponent.revalidate();
                swingComponent.repaint();
            }
        });
//        SwingApplicationUtils.deriveFont(
//                ff,
//                null, null,
//                appComponent.textStyle().underline().get(),
//                appComponent.textStyle().strikethrough().get()
//        )

    }

    public static Window resolveOwnerWindow(AppComponent cmp, Application app) {
        Component c = resolveOwnerComponent(cmp, app);
        if (c != null) {
            if (c instanceof Window) {
                return (Window) c;
            }
            return SwingUtilities.getWindowAncestor(c);
        }
        return null;
    }

    public static Component resolveOwnerComponent(AppComponent cmp, Application app) {
        if (cmp != null) {
            return (Component) cmp.peer().toolkitComponent();
        }
        return (app.mainFrame().get() == null ? null : (Component) (app.mainFrame().get().peer().toolkitComponent()));
    }

    public static Window getComponentOwnerWindow(Object parent, AppComponent comp) {
        while (comp != null) {
            Object c = comp.peer().toolkitComponent();
            if (c instanceof Window) {
                return (Window) c;
            }
            comp = comp.parent();
        }
        return null;
    }

    public static Component getComponentOwner(Object parent, AppComponent comp) {
        if (parent instanceof Component) {
            return (Component) parent;
        }
        parent = comp.app().mainFrame().get().peer().toolkitComponent();
        if (parent instanceof Component) {
            return (Component) parent;
        }
        return null;
    }

    public static class AppComponentTextStylerApplier implements PropertyListener {
        AppTextControl appComponent;

        public AppComponentTextStylerApplier(AppTextControl appComponent) {
            this.appComponent = appComponent;
        }


        @Override
        public void propertyUpdated(PropertyEvent e) {
            JComponent swingComponent = (JComponent) effectiveSwingComponent(appComponent);
            AppFont f = appComponent.textStyle().font().get();
            if (f != null) {
                SwingApplicationUtils.setComponentFont(
                        swingComponent, f, null, null,
                        appComponent.textStyle().underline().get(),
                        appComponent.textStyle().strikethrough().get()
                );


            } else {
                // reset?
            }
        }
    }

    public static class InstallTextComponent {

        private boolean updatingDocument;
        private AppControl appComponent;
        private JTextComponent swingComponent;
        private WritableStr text;

        public InstallTextComponent(AppEditTextControl appComponent, JTextComponent swingComponent) {
            this(appComponent, swingComponent, appComponent.text());
        }

        public InstallTextComponent(AppControl appComponent, JTextComponent swingComponent, WritableStr text) {
            this.appComponent = appComponent;
            this.swingComponent = swingComponent;
            this.text = text;
        }

        public void install() {
            DocumentListener documentListener = new DocumentListener() {

                @Override
                public void insertUpdate(DocumentEvent e) {
                    anyChange(e);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    anyChange(e);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    anyChange(e);
                }

                public void anyChange(DocumentEvent e) {
                    if (!updatingDocument) {
                        try {
                            updatingDocument = true;
                            text.set(Str.of(swingComponent.getText()));
                        } finally {
                            updatingDocument = false;
                        }
                    }
                }
            };
            swingComponent.addPropertyChangeListener("document", e -> {
                Document o = (Document) e.getOldValue();
                Document n = (Document) e.getNewValue();
                if (o != null) {
                    o.removeDocumentListener(documentListener);
                }
                if (n != null) {
                    n.addDocumentListener(documentListener);
                }
            });
            swingComponent.addCaretListener(new CaretListener() {
                @Override
                public void caretUpdate(CaretEvent e) {
                    int d = e.getDot();
                    int m = e.getMark();
                    if (appComponent instanceof AppEditTextControl) {
                        if (d != m) {
                            ((AppEditTextControl) appComponent).textSelection().set(
                                    swingComponent.getSelectedText()
                            );
                        } else {
                            ((AppEditTextControl) appComponent).textSelection().set("");
                        }
                    }
                }
            });
            swingComponent.getDocument().addDocumentListener(documentListener);

            //ignore locale!!
            text.onChangeAndInit(new Runnable() {
                boolean updating = false;

                @Override
                public void run() {
                    if (!updating) {
                        updating = true;
                        try {
                            if (!updatingDocument) {
                                String t = text.getOr(x -> x == null ? "" : x.toString());
                                if(swingComponent.getDocument() instanceof HTMLDocument){
                                    //BUG work-around, when replacing a document with some invisible table
                                    //with empty text, the table is inheritted!
                                    if(t.isEmpty()) {
                                        swingComponent.setText("empty text");
                                    }
                                }
                                swingComponent.setText(t);
                            }
                        } finally {
                            updating = false;
                        }
                    }
                }
            });
            appComponent.editable().onChangeAndInit(
                    () -> swingComponent.setEditable(appComponent.editable().get())
            );
        }

    }

    private static class SwingComponentEvent implements AppComponentEvent {
        private AppEventType eventType;
        private KeyCode code;
        private AppComponent component;
        private InputEvent e;

        public SwingComponentEvent(AppEventType eventType, KeyEvent e, AppComponent component) {
            this.eventType = eventType;
            this.e = e;
            this.component = component;
            code = KeyCode.of(e.getKeyCode());
        }

        public SwingComponentEvent(AppEventType eventType, MouseEvent e, AppComponent component) {
            this.eventType = eventType;
            this.component = component;
            code = KeyCode.UNKNOWN;
            this.e = e;
        }

        public SwingComponentEvent(AppEventType eventType, MouseWheelEvent e, AppComponent component) {
            this.eventType = eventType;
            this.component = component;
            code = KeyCode.UNKNOWN;
            this.e = e;
        }

        @Override
        public AppEventType eventType() {
            return eventType;
        }

        @Override
        public KeyCode code() {
            return code;
        }

        @Override
        public int wheelRotation() {
            if (e instanceof MouseWheelEvent) {
                return ((MouseWheelEvent) e).getWheelRotation();
            }
            return 0;
        }

        @Override
        public boolean isShiftDown() {
            return e.isShiftDown();
        }

        @Override
        public boolean isControlDown() {
            return e.isControlDown();
        }

        @Override
        public boolean isMetaDown() {
            return e.isMetaDown();
        }

        @Override
        public boolean isAltDown() {
            return e.isAltDown();
        }

        @Override
        public boolean isAltGraphDown() {
            return e.isAltGraphDown();
        }

        @Override
        public boolean isMouseEvent() {
            return e instanceof MouseEvent;
        }

        @Override
        public boolean isKeyEvent() {
            return e instanceof KeyEvent;
        }

        @Override
        public double getMouseX() {
            if (e instanceof MouseEvent) {
                return ((MouseEvent) e).getX();
            }
            return 0;
        }

        @Override
        public double getMouseY() {
            if (e instanceof MouseEvent) {
                return ((MouseEvent) e).getY();
            }
            return 0;
        }

        @Override
        public boolean isPrimaryMouseButton() {
            if (e instanceof MouseEvent) {
                return SwingUtilities.isLeftMouseButton(((MouseEvent) e));
            }
            return false;
        }

        @Override
        public boolean isSecondaryMouseButton() {
            if (e instanceof MouseEvent) {
                return SwingUtilities.isRightMouseButton(((MouseEvent) e));
            }
            return false;
        }

        @Override
        public boolean isDoubleClick() {
            if (e instanceof MouseEvent) {
                return (((MouseEvent) e)).getClickCount() == 2;
            }
            return false;
        }

        @Override
        public boolean isSimpleClick() {
            if (e instanceof MouseEvent) {
                return (((MouseEvent) e)).getClickCount() == 1;
            }
            return false;
        }

        @Override
        public int getClickCount() {
            if (e instanceof MouseEvent) {
                return (((MouseEvent) e)).getClickCount();
            }
            return 0;
        }

        @Override
        public AppComponent source() {
            return component;
        }

        @Override
        public Application app() {
            return component.app();
        }
    }

}
