/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.jfx;

import javafx.scene.Node;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppComponentPeer;

/**
 *
 * @author vpc
 */
public interface FxPeer extends AppComponentPeer{

    public static Node nodeOf(AppComponent a) {
        FxPeer q = of(a);
        return q == null ? null : q.getNode();
    }

    public static FxPeer ofOrNull(Object a) {
        if (a instanceof FxPeer) {
            return (FxPeer) a;
        }
        FxPeer q = FxApplicationUtils.extractBindAppComponent(a);
        if (q != null) {
            return q;
        }
        return null;
    }

    public static FxPeer of(Object a) {
        if (a instanceof FxPeer) {
            return (FxPeer) a;
        }
        FxPeer q = FxApplicationUtils.extractBindAppComponent(a);
        if (q != null) {
            return q;
        }
        throw new IllegalArgumentException("cannot extract fx app component");
    }

    default Node getNode() {
        return (Node) toolkitComponent();
    }

}
