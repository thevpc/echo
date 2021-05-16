package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;

import javax.swing.*;
import java.awt.*;

public class SwingMenuBarPeer implements SwingPeer{
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
        component.app().toolkit().runUIAndWait(()-> {
            Object o = other.peer().toolkitComponent();
            jcomponent.add((Component) o, index);
        });
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
