/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import javax.swing.JComponent;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppDockPeer;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import net.thevpc.echo.swing.peers.SwingPeer;
import org.noos.xing.mydoggy.Content;

/**
 *
 * @author thevpc
 */
public class MyDoggyDockPeer implements SwingPeer, AppDockPeer {

    private MyDoggyToolWindowManager toolkitComponent;
    private Application application;

    public MyDoggyDockPeer() {
    }

    @Override
    public void install(AppComponent comp) {
        if(toolkitComponent==null){
            this.application=comp.app();
            toolkitComponent=new MyDoggyToolWindowManager();
        }
    }

    public Application app() {
        return application;
    }

    @Override
    public Object toolkitComponent() {
        return toolkitComponent;
    }


//    @Override
//    public AppWindowPeer addWindowImpl(String id, AppComponent component, AppWindowAnchor anchor) {
//        ToolWindowAnchor jdanchor = ToolWindowAnchor.LEFT;
//        switch (anchor) {
//            case TOP: {
//                jdanchor = ToolWindowAnchor.TOP;
//                return new MyDoggyAppToolWindow();
//            }
//            case BOTTOM: {
//                jdanchor = ToolWindowAnchor.BOTTOM;
//                return new MyDoggyAppToolWindow();
//            }
//            case LEFT: {
//                jdanchor = ToolWindowAnchor.LEFT;
//                return new MyDoggyAppToolWindow();
//            }
//            case RIGHT: {
//                jdanchor = ToolWindowAnchor.RIGHT;
//                return new MyDoggyAppToolWindow();
//            }
//            case CONTENT: {
//                return new MyDoggyAppContentWindow();
//            }
//            case DESKTOP: {
//                if (!desktopEnabled()) {
//                    return new MyDoggyAppContentWindow();
//                } else {
//                    return new MyDoggyAppDesktopWindow();
//                }
//            }
//        }
//        throw new IllegalArgumentException("unsupported");
//    }

//    @Override
//    public void removeWindowImpl(String id, AppWindowPeer a) {
//        if(a instanceof MyDoggyAppDesktopWindow){
//
//        }else if(a instanceof MyDoggyAppToolWindow){
//
//        }else if(a instanceof MyDoggyAppContentWindow){
//
//        }
////        JComponent j = ((MyDoggyAbstractWindow)atw);
////        Content c = gcomponent().getContentManager().getContentByComponent(j);
////        if (c != null) {
////            gcomponent().getContentManager().removeContent(c);
////            return;
////        }
////        WindowModel tw = gcomponent().getToolWindow(id);
////        if (tw != null) {
////            gcomponent().unregisterToolWindow(id);
////        }
////        if (desktop != null) {
////            JInternalFrame w = desktop.getWindow(new WindowPath(id));
////            if (w != null) {
////                desktop.removeWindow(w);
////            }
////        }
//    }

    @Override
    public JComponent jcomponent() {
        return (JComponent) awtComponent();
    }

    public MyDoggyToolWindowManager getToolkitComponent() {
        return toolkitComponent;
    }

}
