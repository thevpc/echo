package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;

import javax.swing.*;
import java.awt.*;

public class SwingToolBarPeer implements SwingPeer{
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
