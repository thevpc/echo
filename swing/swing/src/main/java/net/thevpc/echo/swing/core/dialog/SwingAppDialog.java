/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.core.dialog;

import net.thevpc.echo.AppDialogResult;
import net.thevpc.echo.AppDialog;
import net.thevpc.echo.AppDialogInputPanel;
import net.thevpc.echo.AppDialogAction;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import net.thevpc.common.swing.GridBagLayoutSupport;
import net.thevpc.common.swing.JDialog2;
import net.thevpc.common.swing.SwingUtilities3;
import net.thevpc.echo.Application;

/**
 *
 * @author vpc
 */
public class SwingAppDialog extends JDialog2 implements AppDialog {

    private GenFooter footer;
    private JComponent mainComponent;
    protected Application app;
    protected String selectedButton;
    protected String[] buttonIds;

    public static SwingAppDialogBuilder of(Application app) {
        return new SwingAppDialogBuilder(app);
    }

    public SwingAppDialog(Application app, String titleId, JComponent mainComponent, String[] buttonIds, String defaultId, AppDialogAction cons) {
        this(app, titleId);
        build(mainComponent, buttonIds, defaultId, cons);
    }

    public SwingAppDialog(Application app, String titleId) {
        super((JFrame) app.mainWindow().get().component(),
                app.i18n().getString(titleId), true
        );
        this.app = app;
    }

    @Override
    public void closeDialog() {
        this.setVisible(false);
    }

    public AppDialogResult showInputDialog() {
        String a = showDialog();
        return new AppDialogResult() {
            @Override
            public String getButtonId() {
                return a;
            }

            @Override
            public <T> T getValue() {
                if (mainComponent == null || !(mainComponent instanceof AppDialogInputPanel)) {
                    throw new IllegalArgumentException("Not an input component");
                }
                return (T) ((AppDialogInputPanel) mainComponent).getValue();
            }
        };
    }

    public String showDialog() {
        this.selectedButton = null;
        setVisible(true);
        return selectedButton;
    }

    protected void build(JComponent mainComponent, String[] buttonIds, String defaultId, AppDialogAction cons) {
        this.mainComponent = mainComponent;
        getRootPane().setLayout(new BorderLayout());
        footer = new GenFooter(app, cons, buttonIds);
        JPanel withBorder = new JPanel(new BorderLayout());
        withBorder.add(mainComponent);
        withBorder.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        getRootPane().add(withBorder, BorderLayout.CENTER);
        getRootPane().add(footer, BorderLayout.SOUTH);
        SwingUtilities3.addEscapeBindings(this);
        if (defaultId != null) {
            this.getRootPane().setDefaultButton(footer.getButton(defaultId));
        }
        this.setLocationRelativeTo(getOwner());
        pack();
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
            cons.onAction(new SwingAppDialogContext(SwingAppDialog.this, ci.id, ci.index));
        }
    }

    public static class InputTextFieldPanel extends JPanel implements AppDialogInputPanel {

        private JLabel header = new JLabel();
        private JTextField value = new JTextField();

        public InputTextFieldPanel(Application app, String headerId, String initalValue) {
            header.setText(app.i18n().getString(headerId));
            value.setText(initalValue);
            GridBagLayoutSupport.of("[^<header]\n[-=<value];insets(5,5,5,5)")
                    .bind("header", header)
                    .bind("value", value)
                    .apply(this);
        }

        @Override
        public Object getValue() {
            return value.getText();
        }

    }

    public static class InputTextAreaPanel extends JPanel implements AppDialogInputPanel {

        private JLabel header = new JLabel();
        private JTextArea value = new JTextArea();

        public InputTextAreaPanel(Application app, String headerId, String initalValue) {
            header.setText(app.i18n().getString(headerId));
            value.setText(initalValue);
            GridBagLayoutSupport.of("[^<header]\n[-==<$+value];insets(5,5,5,5)")
                    .bind("header", header)
                    .bind("value", new JScrollPane(value))
                    .apply(this);
        }

        @Override
        public Object getValue() {
            return value.getText();
        }

    }

}
