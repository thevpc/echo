/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.dock.JDockPane;
import net.thevpc.echo.Dimension;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppScrollPane;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.spi.peers.AppScrollPanePeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;

/**
 * @author vpc
 */
public class SwingScrollPanePeer implements SwingPeer, AppScrollPanePeer {

    JScrollPane swingComponent = new JScrollPane();
    private AppScrollPane appComponent;

    public static JDockPane.DockAnchor toDocAnchor(Anchor anchor) {
        return JDockPane.DockAnchor.valueOf(anchor.name());
    }

    @Override
    public void install(AppComponent comp) {
        swingComponent.setPreferredSize(SwingHelpers.toAwtDimension(comp.prefSize().get()));
        comp.prefSize().onChange(e->{
            swingComponent.setPreferredSize(SwingHelpers.toAwtDimension(comp.prefSize().get()));
        });
    }

    @Override
    public void addChild(AppComponent other, int index) {
        swingComponent.getViewport().setView(
                (Component) other.peer().toolkitComponent()
        );
        swingComponent.invalidate();
        swingComponent.revalidate();
        swingComponent.repaint();
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        swingComponent.getViewport().setView(
                null
        );
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

}
