package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.swing.SwingApplicationUtils;

import javax.swing.*;
import java.awt.*;

public class SwingMenuPeer implements SwingPeer{
    private JMenu jcomponent;
    @Override
    public void install(AppComponent comp) {
        jcomponent = new JMenu();
        SwingApplicationUtils.prepareAbstractButton(jcomponent,comp.tool(), comp.app(), true);
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        jcomponent.add((Component) o,index);
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
