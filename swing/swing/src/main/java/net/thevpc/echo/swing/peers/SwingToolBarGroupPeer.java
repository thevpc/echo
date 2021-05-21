package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppToolBarGroupPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;
import net.thevpc.echo.swing.raw.JToolBarGroup;

import java.awt.*;

public class SwingToolBarGroupPeer implements SwingPeer, AppToolBarGroupPeer {
    private JToolBarGroup swingComponent;
    @Override
    public void install(AppComponent comp) {
        JToolBarGroup jcomponent = new JToolBarGroup();
        this.swingComponent = jcomponent;
    }

    @Override
    public void uninstall() {

    }

    public void addChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        swingComponent.add((Component) o,index);
        SwingHelpers.refreshPanel(swingComponent,2);
    }

    public void removeChild(AppComponent other, int index) {
        Object o = other.peer().toolkitComponent();
        swingComponent.remove(index);
        SwingHelpers.refreshPanel(swingComponent,2);
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

}
