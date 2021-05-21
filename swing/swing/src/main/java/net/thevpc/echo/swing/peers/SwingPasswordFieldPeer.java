package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppPasswordField;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.spi.peers.AppPasswordFieldPeer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SwingPasswordFieldPeer implements SwingPeer, AppPasswordFieldPeer {
    private JPasswordField jTextField;
    private Box comp;
    private JCheckBox check;
    private AppPasswordField appTextField;

    public SwingPasswordFieldPeer() {
    }

    public void install(AppComponent component) {
        appTextField=(AppPasswordField) component;
        comp=Box.createHorizontalBox();
        jTextField=new JPasswordField();
        comp.add(jTextField);
        comp.add(check=new JCheckBox());
        check.addActionListener((e)
                -> jTextField.setEchoChar(check.isSelected() ? '\0' : '*')
        );
        jTextField.setEchoChar(check.isSelected() ? '\0' : '*');

        jTextField.setText(
                appTextField.text().getOr(x->x==null?"":x.toString())
        );
        jTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }
            protected void update(){
                appTextField.text().set(Str.of(jTextField.getText()));
            }
        });
        appTextField.onChange(e->jTextField.setText(
                appTextField.text().getOr(x->x==null?"":x.toString())
        ));
    }

    @Override
    public Object toolkitComponent() {
        return comp;
    }
}
