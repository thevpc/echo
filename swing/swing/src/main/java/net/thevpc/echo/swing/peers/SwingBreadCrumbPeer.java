package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppBreadCrumbPeer;
import net.thevpc.echo.spi.peers.AppToolBarPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;

public class SwingBreadCrumbPeer implements SwingPeer, AppBreadCrumbPeer {
    private JToolBar jcomponent;
    @Override
    public void install(AppComponent comp) {
        jcomponent = new JToolBar();
        jcomponent.setFloatable(false);
        SwingPeerHelper.installComponent(comp, jcomponent);
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.add(new JToolBar.Separator(),2*index);
        jcomponent.add((Component) o,2*index+1);
        SwingHelpers.refreshPanel(jcomponent,2);
    }

    public void removeChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.remove(2*index);
        jcomponent.remove(2*index+1);
        SwingHelpers.refreshPanel(jcomponent,2);
    }

    @Override
    public Object toolkitComponent() {
        return jcomponent;
    }

}
