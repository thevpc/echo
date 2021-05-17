/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.SwingUtilities3;
import net.thevpc.common.swing.dialog.JDialog2;
import net.thevpc.common.swing.layout.GridBagLayoutSupport;
import net.thevpc.echo.AppDialogAction;
import net.thevpc.echo.AppDialogResult;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppAlert;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.DefaultAppDialogContext;
import net.thevpc.echo.impl.components.Alert;
import net.thevpc.echo.impl.dialog.DefaultAppDialogResult;
import net.thevpc.echo.api.peers.AppAlertPeer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author vpc
 */
public class SwingAppAlertPeer implements AppAlertPeer {

    protected Application app;
    protected String selectedButton;
//    protected String[] buttonIds;
    protected Alert binding;
    JDialog2 currentDialog = null;
    private GenFooter footer;
//    private Supplier<?> valueEvaluator;
//    private Component mainComponent;


//    public SwingAppAlertPeer(Application app, AppAlert binding, String titleId, JComponent mainComponent, String[] buttonIds, String defaultId, AppDialogAction cons
//            , Supplier<?> valueEvaluator
//            , Object... params) {
//        this(app, binding,titleId, valueEvaluator,params);
//        build(mainComponent, buttonIds, defaultId, cons);
//    }
//
//    public SwingAppAlertPeer(Application app, AppAlert binding, String titleId, Supplier<?> valueEvaluator, Object... params) {
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
        this.binding =(Alert) alert;
//        this.valueEvaluator = ((Alert) alert).getValueEvaluator();
    }

    @Override
    public Object toolkitComponent() {
        return null;
    }

    public String showDialog() {
        this.selectedButton = null;
        JFrame f = (JFrame) app.mainFrame().get().peer().toolkitComponent();
        JDialog2 dialog2 = new JDialog2(f);
        dialog2.setLocationRelativeTo(f);
        Component _mainComponent=(Component) binding.getContent().peer().toolkitComponent();
        if (_mainComponent == null) {
            _mainComponent = new JLabel();
        }
        if (binding.getPreferredSize() != null) {
            dialog2.setPreferredSize(new Dimension(
                    (int) binding.getPreferredSize().getWidth(),
                    (int) binding.getPreferredSize().getHeight()
            ));
        }
        build(dialog2,_mainComponent, binding.getButtonIds().toArray(new String[0]), binding.getDefaultId(), binding.getAction());


        build(dialog2, _mainComponent,
                binding.getButtonIds().toArray(new String[0])
                , binding.getDefaultId(),binding.getAction()
        );
        currentDialog = dialog2;
        dialog2.setVisible(true);
        return selectedButton == null ? "" : selectedButton;
    }

    public AppDialogResult showInputDialog() {
        return new DefaultAppDialogResult(showDialog(), binding.getValueEvaluator(), app);
    }

    @Override
    public void closeDialog() {
        if (currentDialog != null) {
            currentDialog.setVisible(false);
            currentDialog = null;
        }
    }

    public void build(JDialog2 dialog2, Component mainComponent, String[] buttonIds, String defaultId, AppDialogAction cons) {
        dialog2.getRootPane().setLayout(new BorderLayout());
        footer = new GenFooter(app, cons, buttonIds);
        JPanel withBorder = new JPanel(new BorderLayout());
        withBorder.add(mainComponent);
        withBorder.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        dialog2.getRootPane().add(withBorder, BorderLayout.CENTER);
        dialog2.getRootPane().add(footer, BorderLayout.SOUTH);
        SwingUtilities3.addEscapeBindings(dialog2);
        if (defaultId != null) {
            dialog2.getRootPane().setDefaultButton(footer.getButton(defaultId));
        }
        dialog2.setLocationRelativeTo(dialog2.getOwner());
        dialog2.pack();
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
            cons.onAction(new DefaultAppDialogContext(binding, ci.id, ci.index));
        }
    }

}
