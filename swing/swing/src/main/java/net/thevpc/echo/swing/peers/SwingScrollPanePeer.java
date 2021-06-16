/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.dock.JDockAnchor;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppScrollPane;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.spi.peers.AppScrollPanePeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import java.awt.*;

/**
 * @author vpc
 */
public class SwingScrollPanePeer implements SwingPeer, AppScrollPanePeer {

    JScrollPane swingComponent = new JScrollPane();
    private AppScrollPane appComponent;

    public static JDockAnchor toDocAnchor(Anchor anchor) {
        return JDockAnchor.valueOf(anchor.name());
    }

    @Override
    public void install(AppComponent comp) {
        appComponent=(AppScrollPane) comp;
        SwingPeerHelper.installComponent(appComponent, swingComponent);
        appComponent.children().onChange((e)->{
            switch (e.eventType()){
                case UPDATE:{
                    AppComponent other = e.newValue();//this.appComponent.child().get();
                    swingComponent.getViewport().setView(
                            other==null?null: (Component) other.peer().toolkitComponent()
                    );
                    swingComponent.invalidate();
                    swingComponent.revalidate();
                    swingComponent.repaint();
                    break;
                }
                case REFRESH:{
                    swingComponent.invalidate();
                    swingComponent.revalidate();
                    swingComponent.repaint();
                    break;
                }
            }
        });
//        appComponent.children().onChange(()->{
//            AppComponent c = this.appComponent.children().get();
//            swingComponent.getViewport().setView(
//                    c==null?null:((Component) c.peer().toolkitComponent())
//            );
//        });
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
        swingComponent.invalidate();
        swingComponent.revalidate();
        swingComponent.repaint();
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

}
