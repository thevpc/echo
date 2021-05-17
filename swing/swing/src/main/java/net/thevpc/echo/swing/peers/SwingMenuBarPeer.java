package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppMenuBarPeer;

import javax.swing.*;
import java.awt.*;

public class SwingMenuBarPeer implements SwingPeer, AppMenuBarPeer {
    private JMenuBar jcomponent;
    private AppComponent component;
    @Override
    public void install(AppComponent component) {
        jcomponent = new JMenuBar();
        this.component=component;
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        component.app().toolkit().runUI(()-> {
            Object o = other.peer().toolkitComponent();
            jcomponent.add((Component) o, index);
        });
    }

    public void removeChild(AppComponent other, int index) {
        component.app().toolkit().runUI(()-> {
            Object o = other.peer().toolkitComponent();
            jcomponent.remove(index);
        });
    }

    @Override
    public Object toolkitComponent() {
        return jcomponent;
    }

}
