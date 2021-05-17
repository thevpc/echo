package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppComponentPeer;
import net.thevpc.echo.api.peers.AppContextMenuPeer;

import javax.swing.*;
import java.awt.*;

public class SwingContextMenuPeer implements SwingPeer, AppContextMenuPeer {
    private JPopupMenu jcomponent;
    @Override
    public void install(AppComponent comp) {
        jcomponent = new JPopupMenu();
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
