/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.swing.SwingUtilities3;
import net.thevpc.common.swing.dialog.JDialog2;
import net.thevpc.common.swing.icon.EmptyIcon;
import net.thevpc.common.swing.layout.GridBagLayoutSupport;
import net.thevpc.echo.Alert;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppDialogAction;
import net.thevpc.echo.api.AppDialogContext;
import net.thevpc.echo.api.AppDialogResult;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppAlert;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.impl.DefaultAppDialogContext;
import net.thevpc.echo.impl.dialog.DefaultAppDialogResult;
import net.thevpc.echo.spi.peers.AppAlertPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vpc
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

    //    public SwingAlertPeer(Application app, AppAlert binding, String titleId, JComponent mainComponent, String[] buttonIds, String defaultId, AppDialogAction cons
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

    public String showDialog(AppComponent owner) {
        this.selectedButton = null;
        JFrame f = (JFrame) (app.mainFrame().get()==null?null:app.mainFrame().get().peer().toolkitComponent());
        JDialog2 dialog2 = new JDialog2(f);
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
                appAlert.defaultButton().get(), appAlert.getAction()
        );
        currentDialog = dialog2;
        dialog2.setVisible(true);
        return selectedButton == null ? "" : selectedButton;
    }

    public AppDialogResult showInputDialog(AppComponent owner) {
        return new DefaultAppDialogResult(showDialog(owner), appAlert.getValueEvaluator(), appAlert, app);
    }

    @Override
    public void closeDialog() {
        if (currentDialog != null) {
            currentDialog.setVisible(false);
            currentDialog = null;
        }
    }

    public void build(JDialog2 dialog2, Component mainComponent, String[] buttonIds, String defaultId, AppDialogAction action) {

        //            Str _titleId = title;
        java.util.List<String> _buttonIds = buttonIds == null ? new java.util.ArrayList<>() : new java.util.ArrayList<>(
                Arrays.asList(buttonIds)
        );
        if (_buttonIds.isEmpty()) {
            _buttonIds.add("ok");
        }
        buttonIds = _buttonIds.toArray(new String[0]);
        if (action == null) {
            action = new AppDialogAction() {
                @Override
                public void onAction(AppDialogContext context) {
                    AppDialogAction a = appAlert.getAction(context.getButtonId());
                    if (a != null) {
                        a.onAction(context);
                    }
                }
            };
        }

        dialog2.getRootPane().setLayout(new BorderLayout());
        footer = new GenFooter(app, action, buttonIds);
        JPanel withBorder = new JPanel(new BorderLayout());
        withBorder.add(mainComponent);
        withBorder.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));

        JLabel hh = new JLabel();
        hh.setText(evalHeader());
        hh.setHorizontalTextPosition(SwingConstants.RIGHT);
        hh.setIconTextGap(16);
        hh.setOpaque(true);
        hh.setPreferredSize(new Dimension(400, 60));
        hh.setMinimumSize(new Dimension(400, 60));
        Icon icon = null;
        AppImage hi = appAlert.headerIcon().get();
        if (hi != null) {
            hi = hi.scaleTo(24, 24);
        }
        icon = SwingHelpers.toAwtIcon(hi);
        if (icon == null) {
            icon = new EmptyIcon(24, 24);
        }
        hh.setIcon(icon);
//        hh.setIcon(new RectColorIcon(Color.RED, 32));

        hh.setFont(hh.getFont().deriveFont(Font.BOLD, (int) (hh.getFont().getSize() * 1.2)));
        hh.setOpaque(false);
        Box hb = Box.createHorizontalBox();
        hb.add(Box.createHorizontalStrut(10));
        hb.add(hh);

        hb.setBackground(Color.WHITE);
        hb.setOpaque(true);
        hb.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

        dialog2.getRootPane().add(hb, BorderLayout.NORTH);
        dialog2.getRootPane().add(withBorder, BorderLayout.CENTER);
        dialog2.getRootPane().add(footer, BorderLayout.SOUTH);
        SwingUtilities3.addEscapeBindings(dialog2);
        if (defaultId != null) {
            dialog2.getRootPane().setDefaultButton(footer.getButton(defaultId));
        }
        JFrame f = (JFrame) (app.mainFrame().get()==null?null:app.mainFrame().get().peer().toolkitComponent());
        dialog2.pack();
        dialog2.setLocationRelativeTo(f);
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

        public GenFooter(Application app, AppDialogAction cons, String[] buttonIds) {
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

        private final AppDialogAction cons;

        public ActionListenerImpl(AppDialogAction cons) {
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
