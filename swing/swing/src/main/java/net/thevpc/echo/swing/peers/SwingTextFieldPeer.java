package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.spi.peers.AppTextFieldPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SwingTextFieldPeer implements SwingPeer, AppTextFieldPeer {
    private JTextField jTextField;
    private AppTextField appTextField;

    public SwingTextFieldPeer() {
    }

    public void install(AppComponent component) {
        appTextField=(AppTextField) component;
        jTextField=new JTextField();
        jTextField.setText(
                appTextField.text().getOr(x->x==null?"":x.toString())
        );
        SwingPeerHelper.installComponent(appTextField,this.jTextField);
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
        return jTextField;
    }
}
