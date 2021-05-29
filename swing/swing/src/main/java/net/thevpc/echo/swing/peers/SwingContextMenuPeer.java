package net.thevpc.echo.swing.peers;

import net.thevpc.echo.ContextMenu;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.spi.peers.AppComponentPeer;
import net.thevpc.echo.spi.peers.AppContextMenuPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import java.awt.*;

public class SwingContextMenuPeer implements SwingPeer, AppContextMenuPeer {
    private JPopupMenu jcomponent;
    private ContextMenu appContextMenu;
    @Override
    public void install(AppComponent comp) {
        this.appContextMenu=(ContextMenu) comp;
        jcomponent = new JPopupMenu();
        SwingPeerHelper.installComponent(appContextMenu,jcomponent);
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

    @Override
    public void show(AppComponentPeer owner) {

    }


    //    public void show(Object source, int x, int y) {
//        Object g = guiElement();
//        if(g instanceof JPopupMenu) {
//            JPopupMenu c = (JPopupMenu) g;
//            c.show((Component) source, x, y);
//        }
//        if(g instanceof JMenu) {
//            JMenu c = (JMenu) g;
//            c.setMenuLocation(x,y);
//            c.setPopupMenuVisible(true);
//        }
//        if(g instanceof JDropDownButton) {
//            JDropDownButton c = (JDropDownButton) g;
//            c.setMenuLocation(x,y);
//            c.setPopupMenuVisible(true);
//        }
//        if(g instanceof JDropDownLabel) {
//            JDropDownLabel c = (JDropDownLabel) g;
////            c.setMenuLocation(x,y);
////            c.setPopupMenuVisible(true);
//        }
//    }

}
