/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import java.awt.Component;
import javax.swing.JComponent;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppComponentPeer;

/**
 *
 * @author vpc
 */
public interface SwingPeer extends AppComponentPeer{

    static Component gcompOf(AppComponent c) {
        SwingPeer a = of(c);
        return a.awtComponent();
    }

    static JComponent jcompOf(AppComponent c) {
        SwingPeer a = of(c);
        return a.jcomponent();
    }

    static SwingPeer of(AppComponent c) {
        if (c instanceof SwingPeer) {
            return (SwingPeer) c;
        }
        AppComponentPeer p = c.peer();
        if (p instanceof SwingPeer) {
            return (SwingPeer) p;
        }
        return null;
    }

    static SwingPeer of(Component component) {
        if(component instanceof JComponent){
            SwingPeer e =(SwingPeer) ((JComponent) component).getClientProperty(SwingPeer.class.getName());
            if(e!=null){
                return e;
            }
        }
        throw new IllegalArgumentException("unsupported");
    }

    default Component awtComponent() {
        return (Component) toolkitComponent();
    }

    default JComponent jcomponent() {
        return (JComponent) awtComponent();
    }

}
