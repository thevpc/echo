package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppMenuPeer;
import net.thevpc.echo.swing.SwingApplicationUtils;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;

public class SwingMenuPeer implements SwingPeer, AppMenuPeer {
    private JMenu jcomponent;
    private AppComponent component;

    @Override
    public void install(AppComponent comp) {
        this.component = comp;
        jcomponent = new JMenu();
        SwingApplicationUtils.prepareAbstractButton(jcomponent, comp, comp.app(), true);
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.add((Component) o, index);
        SwingHelpers.refreshPanel(jcomponent,2);
    }

    public void removeChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.remove(index);
        SwingHelpers.refreshPanel(jcomponent,2);
    }

    @Override
    public Object toolkitComponent() {
        return jcomponent;
    }

}
