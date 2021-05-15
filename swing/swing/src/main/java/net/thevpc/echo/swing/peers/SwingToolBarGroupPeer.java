package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.swing.raw.JToolBarGroup;

import java.awt.*;

public class SwingToolBarGroupPeer implements SwingPeer{
    private JToolBarGroup jcomponent;
    @Override
    public void install(AppComponent comp) {
        JToolBarGroup jcomponent = new JToolBarGroup();
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
