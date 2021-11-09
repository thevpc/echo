/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.swing.SwingUtilities3;
import net.thevpc.common.swing.dialog.JDialog2;
import net.thevpc.common.swing.layout.GridBagLayoutSupport;
import net.thevpc.echo.Alert;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppDialogContext;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppAlert;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.DefaultAppDialogContext;
import net.thevpc.echo.impl.dialog.DefaultAppDialogResult;
import net.thevpc.echo.spi.peers.AppAlertPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.thevpc.echo.api.AppAlertAction;
import net.thevpc.echo.api.AppAlertInputPane;
import net.thevpc.echo.api.AppAlertResult;

/**
 * @author thevpc
 */
public class SwingAlertPeer implements AppAlertPeer, SwingPeer {

    protected Application app;
    protected String selectedButton;
    //    protected String[] buttonIds;
    protected Alert appAlert;
    JDialog2 currentDialog = null;
    private GenFooter footer;
//    private Supplier<?> valueEvaluator;
//    private Component mainComponent;

    //    public SwingAlertPeer(Application app, AppAlert binding, String titleId, JComponent mainComponent, String[] buttonIds, String defaultId, AppAlertAction cons
//            , Supplier<?> valueEvaluator
//            , Object... params) {
//        this(app, binding,titleId, valueEvaluator,params);
//        build(mainComponent, buttonIds, defaultId, cons);
//    }
//
//    public SwingAlertPeer(Application app, AppAlert binding, String titleId, Supplier<?> valueEvaluator, Object... params) {
//        super((JFrame) app.mainFrame().get().peer().toolkitComponent(),
//                buildMessage(app, titleId, params), true
//        );
//    }
    private static String buildMessage(Application app1, String titleId, Object... params) {
        String m = app1.i18n().getString(titleId);
        if (params.length > 0) {
            m = MessageFormat.format(m, params);
        }
        return m;
    }

    @Override
    public void install(AppComponent comp) {
        AppAlert alert = (AppAlert) comp;
        this.app = alert.app();
        this.appAlert = (Alert) alert;
//        this.valueEvaluator = ((Alert) alert).getValueEvaluator();
    }

    @Override
    public Object toolkitComponent() {
        return null;
    }

    public AppAlertResult showDialog(AppComponent owner) {
        this.selectedButton = null;
        if (owner == null) {
            owner = appAlert.owner().get();
        }
        Window f = SwingPeerHelper.resolveOwnerWindow(owner, appAlert.app());
        JDialog2 dialog2 = (f instanceof Frame) ? new JDialog2((Frame) f) : new JDialog2((Dialog) f);
        dialog2.setModal(true);

        dialog2.setTitle(evalTitle());
//        dialog2.setLocationRelativeTo(f);
        Component _mainComponent = appAlert.content().get() == null ? null : (Component) appAlert.content().get().peer().toolkitComponent();
        if (_mainComponent == null) {
            _mainComponent = new JLabel();
        }
        if (appAlert.prefSize().get() != null) {
            dialog2.setPreferredSize(
                    SwingHelpers.toAwtDimension(appAlert.prefSize().get())
            );
        }
        build(dialog2, _mainComponent,
                appAlert.getButtonIds().toArray(new String[0]),
                appAlert.defaultButton().get(), appAlert.getAction(), f
        );
        currentDialog = dialog2;
        Dimension size = dialog2.getSize();

        //ensure size is not too big!
//        DisplayMode dm = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
        Window o = dialog2.getOwner();
        Dimension size2= SwingHelpers.minDim(
                dialog2.getSize(),
                SwingHelpers.sizePercent(SwingHelpers.sizeOf(dialog2.getGraphicsConfiguration()),0.9f,0.9f),
                SwingHelpers.sizePercent(o==null?null:o.getSize(),0.9f,0.9f)
        );
        if(!size2.equals(size)){
            dialog2.setSize(size2);
        }

        dialog2.setVisible(true);
        String buttonResult = selectedButton == null ? "" : selectedButton;
        Supplier<?> valueEvaluator = null;
        if (appAlert.content().get() instanceof AppAlertInputPane) {
            valueEvaluator = ((AppAlertInputPane) appAlert.content().get())::getValue;
        }
        return new DefaultAppDialogResult(buttonResult, valueEvaluator, appAlert, app);
    }

    @Override
    public void closeDialog() {
        if (currentDialog != null) {
            currentDialog.setVisible(false);
            currentDialog = null;
        }
    }

    public void build(JDialog2 dialog2, Component mainComponent, String[] buttonIds, String defaultId, AppAlertAction action, Window owner) {

        //            Str _titleId = title;
        java.util.List<String> _buttonIds = buttonIds == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(
                Arrays.asList(buttonIds)
        );
        if (_buttonIds.isEmpty()) {
            _buttonIds.add("ok");
        }
        buttonIds = _buttonIds.toArray(new String[0]);
        if (action == null) {
            action = new AppAlertAction() {
                @Override
                public void onAction(AppDialogContext context) {
                    AppAlertAction a = appAlert.getAction(context.getButtonId());
                    if (a == null) {
                        a=c -> c.getAlert().closeAlert();
                    }
                    a.onAction(context);
                }
            };
        }

        dialog2.getRootPane().setLayout(new BorderLayout());
        footer = new GenFooter(app, action, buttonIds);
        JPanel withBorder = new JPanel(new BorderLayout());
        withBorder.add(mainComponent);
        withBorder.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        SwingAlertHeader header = new SwingAlertHeader();
        header.setHeaderText(evalHeader());
        Icon icon = null;
        AppImage hi = appAlert.headerIcon().get();
        if (hi != null) {
            hi = hi.scaleTo(24, 24);
        }
        icon = SwingHelpers.toAwtIcon(hi);
        header.setHeaderIcon(icon);

        dialog2.getRootPane().add(header, BorderLayout.NORTH);
        dialog2.getRootPane().add(withBorder, BorderLayout.CENTER);
        dialog2.getRootPane().add(footer, BorderLayout.SOUTH);
        SwingUtilities3.addEscapeBindings(dialog2);
        if (defaultId != null) {
            dialog2.getRootPane().setDefaultButton(footer.getButton(defaultId));
        }
        dialog2.pack();
        if (owner != null) {
            dialog2.setLocationRelativeTo(owner);
        }
    }

    public String evalHeader() {
        Str s = appAlert.headerText().get();
        if (s == null) {
            s = Str.i18n("Message.defaultHeader");
        }
        return Applications.rawString(s, appAlert);
    }

    public String evalTitle() {
        Str s = appAlert.title().get();
        if (s == null) {
            s = Str.i18n("Message.defaultTitle");
        }
        return Applications.rawString(s, appAlert);
    }

    public static class ActionInfo {

        int index;
        String id;

        public ActionInfo(int index, String id) {
            this.index = index;
            this.id = id;
        }

    }

    public class GenFooter extends JPanel {

        Map<String, JButton> buttons = new LinkedHashMap<>();
        private ActionListenerImpl actionListenerImpl;

        public GenFooter(Application app, AppAlertAction cons, String[] buttonIds) {
            StringBuilder form = new StringBuilder("[-=glue(h)]");
            actionListenerImpl = new ActionListenerImpl(cons);
            for (int i = 0; i < buttonIds.length; i++) {
                String id = buttonIds[i];
                JButton b = new JButton(app.i18n().getString("Message." + id));
                b.putClientProperty("ActionInfo", new ActionInfo(i, id));
                buttons.put(id, b);
                form.append("[b" + i + "]");
                b.addActionListener(actionListenerImpl);
            }
            form.append(" ; insets(5)");
            GridBagLayoutSupport a = new GridBagLayoutSupport(form.toString());
            int index = 0;
            for (Map.Entry<String, JButton> entry : buttons.entrySet()) {
                a.bind("b" + index, entry.getValue());
                index++;
            }
            a.apply(this);
            setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        }

        public JButton getButton(String id) {
            return buttons.get(id);
        }

    }

    private class ActionListenerImpl implements ActionListener {

        private final AppAlertAction cons;

        public ActionListenerImpl(AppAlertAction cons) {
            this.cons = cons;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ActionInfo ci = (ActionInfo) ((JButton) e.getSource()).getClientProperty("ActionInfo");
            selectedButton = ci.id;
            cons.onAction(new DefaultAppDialogContext(appAlert, ci.id, ci.index));
        }
    }

}
