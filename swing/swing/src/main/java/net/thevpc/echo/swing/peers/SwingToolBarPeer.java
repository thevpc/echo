package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppToolBarPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;

public class SwingToolBarPeer implements SwingPeer, AppToolBarPeer {
    private JToolBar jcomponent;
    @Override
    public void install(AppComponent comp) {
        JToolBar jcomponent = new JToolBar();
        jcomponent.setFloatable(false);
        this.jcomponent = jcomponent;
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.add((Component) o,index);
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
