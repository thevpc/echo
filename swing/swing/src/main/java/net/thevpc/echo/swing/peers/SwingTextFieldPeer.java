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
        SwingPeerHelper.installTextComponent(appTextField,jTextField);
        SwingPeerHelper.installComponent(appTextField,this.jTextField);
    }

    @Override
    public Object toolkitComponent() {
        return jTextField;
    }

    @Override
    public void replaceSelection(String newValue) {
        jTextField.replaceSelection(newValue);
    }
}
