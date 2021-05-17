package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppMenuPeer;
import net.thevpc.echo.swing.SwingApplicationUtils;

import javax.swing.*;
import java.awt.*;

public class SwingMenuPeer implements SwingPeer, AppMenuPeer {
    private JMenu jcomponent;
    private AppComponent component;
    @Override
    public void install(AppComponent comp) {
        this.component=comp;
        jcomponent = new JMenu();
        SwingApplicationUtils.prepareAbstractButton(jcomponent,comp.model(), comp.app(), true);
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.add((Component) o, index);
    }

    public void removeChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.remove(index);
    }

    @Override
    public Object toolkitComponent() {
        return jcomponent;
    }

}
