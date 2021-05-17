package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.api.peers.AppPasswordFieldPeer;
import net.thevpc.echo.api.peers.AppTextFieldPeer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SwingPasswordFieldPeer implements SwingPeer, AppPasswordFieldPeer {
    private JPasswordField jTextField;
    private AppTextField appTextField;

    public SwingPasswordFieldPeer() {
    }

    public void install(AppComponent component) {
        appTextField=(AppTextField) component;
        jTextField=new JPasswordField();
        jTextField.setText(
                appTextField.model().text().getOr(x->x==null?"":x.toString())
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
                appTextField.model().text().set(Str.of(jTextField.getText()));
            }
        });
        appTextField.listeners().add(e->jTextField.setText(
                appTextField.model().text().getOr(x->x==null?"":x.toString())
        ));
    }

    @Override
    public Object toolkitComponent() {
        return jTextField;
    }
}
