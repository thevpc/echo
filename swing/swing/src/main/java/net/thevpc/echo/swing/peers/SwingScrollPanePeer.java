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
 * @author thevpc
 */
public class SwingScrollPanePeer implements SwingPeer, AppScrollPanePeer {

    JScrollPane swingComponent = new JScrollPane();
    private AppScrollPane appComponent;

    public static JDockAnchor toDocAnchor(Anchor anchor) {
        return JDockAnchor.valueOf(anchor.name());
    }

    @Override
    public void install(AppComponent comp) {
        appComponent = (AppScrollPane) comp;
        SwingPeerHelper.installComponent(appComponent, swingComponent);
        appComponent.children().onChange((e) -> {
            switch (e.eventType()) {
                case UPDATE: {
                    AppComponent other = e.newValue();//this.appComponent.child().get();
                    swingComponent.getViewport().setView(
                            other == null ? null : (Component) other.peer().toolkitComponent()
                    );
                    swingComponent.invalidate();
                    swingComponent.revalidate();
                    swingComponent.repaint();
                    break;
                }
                case REFRESH: {
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

    public int getMaxX() {
        return swingComponent.getHorizontalScrollBar().getMaximum();
    }

    public int getMaxY() {
        return swingComponent.getVerticalScrollBar().getMaximum();
    }

    public void scrollTo(Float x, Float y) {
        if (x != null) {
            int maxX = getMaxX();
            int xx = maxX == 0 ? 0 : (int) (x * maxX);
            swingComponent.getHorizontalScrollBar().setValue(xx);
        }
        if (y != null) {
            int maxY = getMaxY();
            int yy = maxY == 0 ? 0 : (int) (y * maxY);
            swingComponent.getVerticalScrollBar().setValue(yy);
        }
    }

    public void scrollTo(Integer x, Integer y) {
        if (x != null) {
            swingComponent.getHorizontalScrollBar().setValue(x);
        }
        if (y != null) {
            swingComponent.getVerticalScrollBar().setValue(y);
        }
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
