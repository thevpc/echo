/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import javax.swing.JComponent;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.api.peers.AppWorkspacePeer;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import net.thevpc.common.swing.win.InternalWindowsHelper;
import net.thevpc.echo.swing.peers.SwingPeer;
import org.noos.xing.mydoggy.Content;
import org.noos.xing.mydoggy.ToolWindowAnchor;

/**
 *
 * @author thevpc
 */
public class MyDoggyAppDockingWorkspace implements SwingPeer, AppWorkspacePeer {

    private InternalWindowsHelper desktop;
    private MyDoggyToolWindowManager toolkitComponent;
    private Content desktopWindow;
    private Application application;

    public MyDoggyAppDockingWorkspace() {
    }

    @Override
    public void install(AppComponent comp) {
        if(toolkitComponent==null){
            this.application=comp.app();
            toolkitComponent=new MyDoggyToolWindowManager();
            if (desktopEnabled()) {
                desktop = new InternalWindowsHelper();
                desktopWindow = toolkitComponent.getContentManager()
                        .addContent("Desktop", null, null,  this.desktop.getDesktop());
                desktopWindow.setEnabled(true);
                desktopWindow.getContentUI().setCloseable(false);
                desktopWindow.getContentUI().setMaximizable(false);
                desktopWindow.getContentUI().setMinimizable(false);
                desktopWindow.getContentUI().setMinimizable(false);
            }
        }
    }

    public Application app() {
        return application;
    }

    @Override
    public Object toolkitComponent() {
        return toolkitComponent;
    }

    @Override
    public void tileDesktop(boolean vertical) {
        desktop.tileFrames();
    }

    @Override
    public void iconDesktop(boolean iconify) {
        if (iconify) {
            desktop.iconifyFrames();
        } else {
            desktop.deiconifyFrames();
        }
    }

    @Override
    public void closeAllDesktop() {
        desktop.closeFrames();
    }

    @Override
    public AppWindowPeer addWindowImpl(String id, AppComponent component, AppWindowAnchor anchor) {
        ToolWindowAnchor jdanchor = ToolWindowAnchor.LEFT;
        switch (anchor) {
            case TOP: {
                jdanchor = ToolWindowAnchor.TOP;
                return new MyDoggyAppToolWindow();
            }
            case BOTTOM: {
                jdanchor = ToolWindowAnchor.BOTTOM;
                return new MyDoggyAppToolWindow();
            }
            case LEFT: {
                jdanchor = ToolWindowAnchor.LEFT;
                return new MyDoggyAppToolWindow();
            }
            case RIGHT: {
                jdanchor = ToolWindowAnchor.RIGHT;
                return new MyDoggyAppToolWindow();
            }
            case CONTENT: {
                return new MyDoggyAppContentWindow();
            }
            case DESKTOP: {
                if (!desktopEnabled()) {
                    return new MyDoggyAppContentWindow();
                } else {
                    return new MyDoggyAppDesktopWindow();
                }
            }
        }
        throw new IllegalArgumentException("unsupported");
    }

    @Override
    public void removeWindowImpl(String id, AppWindowPeer a) {
        if(a instanceof MyDoggyAppDesktopWindow){

        }else if(a instanceof MyDoggyAppToolWindow){

        }else if(a instanceof MyDoggyAppContentWindow){

        }
//        JComponent j = ((MyDoggyAbstractWindow)atw);
//        Content c = gcomponent().getContentManager().getContentByComponent(j);
//        if (c != null) {
//            gcomponent().getContentManager().removeContent(c);
//            return;
//        }
//        ToolWindow tw = gcomponent().getToolWindow(id);
//        if (tw != null) {
//            gcomponent().unregisterToolWindow(id);
//        }
//        if (desktop != null) {
//            JInternalFrame w = desktop.getWindow(new WindowPath(id));
//            if (w != null) {
//                desktop.removeWindow(w);
//            }
//        }
    }

    @Override
    public JComponent jcomponent() {
        return (JComponent) awtComponent();
    }

    public InternalWindowsHelper getDesktop() {
        return desktop;
    }

    public MyDoggyToolWindowManager getToolkitComponent() {
        return toolkitComponent;
    }

    @Override
    public boolean dockingSupported() {
        return true;
    }

    @Override
    public boolean desktopEnabled() {
        return true;
    }
}
