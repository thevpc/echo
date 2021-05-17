package net.thevpc.echo.swing.peers;

import net.thevpc.common.i18n.Str;
import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.api.peers.AppTextFieldPeer;
import net.thevpc.echo.api.peers.AppTextPeer;
import net.thevpc.echo.api.tools.AppTextModel;
import net.thevpc.echo.impl.components.AppComponentBase;
import net.thevpc.echo.swing.icons.SwingAppImage;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class SwingTextFieldPeer implements SwingPeer, AppTextFieldPeer {
    private JTextField jTextField;
    private AppTextField appTextField;

    public SwingTextFieldPeer() {
    }

    public void install(AppComponent component) {
        appTextField=(AppTextField) component;
        jTextField=new JTextField();
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
