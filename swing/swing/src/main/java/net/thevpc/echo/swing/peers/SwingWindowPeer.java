package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppWindowPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SwingWindowPeer implements SwingPeer, AppWindowPeer {

//    private JInternalFrameHelper helper;
//    private InternalWindowsHelper desktop;
    private AppWindow win;
    private JPanel windowPanel;

    @Override
    public void install(AppComponent comp) {
        if (this.win == null) {
            this.win = (AppWindow) comp;
            windowPanel = new JPanel(new BorderLayout());
            SwingPeerHelper.installComponent(comp, windowPanel);
            windowPanel.setName("SwingWindowPeer.Container");
            JComponent cc = (JComponent) win.component().getOr(x -> x == null ? null : x.peer().toolkitComponent());
            if (cc != null) {
                windowPanel.add(cc, BorderLayout.CENTER);
            }
            win.component().onChange(x -> {
                        if (x.newValue() == null) {
                            windowPanel.removeAll();
                        } else {
                            windowPanel.add((JComponent) ((AppComponent) x.newValue()).peer().toolkitComponent(), BorderLayout.CENTER);
                        }
                    }
            );

            //                AppComponentPeer parentPeer = comp.parent().peer();
//                if(parentPeer instanceof SwingWorkspacePeer) {
//                    SwingWorkspacePeer p = (SwingWorkspacePeer) parentPeer;
//                    WindowAnchor anchor = win.anchor().get();
//                    if (anchor == WindowAnchor.DESKTOP) {
//                        this.desktop = p.getDesktop();
//                        WindowInfo info = new WindowInfo();
//                        info.setClosable(false);
//                        info.setIcon(false);
//                        info.setIconifiable(false);
//                        info.setMaximizable(false);
//                        info.setResizable(false);
//                        info.setTitle(
//                                win.title().get() == null ? null :
//                                        win.title().get().getValue(comp.app().i18n())
//                        );
//                        info.setComponent((Component) win.component().get().peer().toolkitComponent());
//                        this.helper = new JInternalFrameHelper(desktop.addFrame(info));
//
//                        SwingAppImage aim = (SwingAppImage) win.smallIcon().get();
//                        info.setFrameIcon(aim == null ? null : aim.getIcon());
//
//                        JInternalFrame j = desktop.addFrame(info);
//                        helper = new JInternalFrameHelper(j);
//                        win.title().onChange(x -> {
//                            helper.getFrame().setTitle(
//                                    win.title().get() == null ? null :
//                                            win.title().get().getValue(comp.app().i18n())
//                            );
//                        });
//                        win.smallIcon().onChange(x -> {
//                            SwingAppImage aim2 = (SwingAppImage) win.smallIcon().get();
//                            helper.getFrame().setFrameIcon(aim2 == null ? null : aim2.getIcon());
//                        });
//                    } else {
////                        p.getWorkspacePanel().add(win.id(),
////                                (JComponent) win.component().getOr(x -> x == null ? null : x.peer().toolkitComponent()),
////                                win.title().getOr(x -> x == null ? null : x.getValue(comp.app().i18n())),
////                                (Icon) win.smallIcon().getOr(x -> (x == null ? null : x.peer().toolkitImage())),
////                                false,
////                                SwingWorkspacePeer.toDocAnchor(anchor)
////                        );
//                    }
//            }
//        });
    }

}

    @Override
    public Object toolkitComponent() {
        return windowPanel;
    }

    @Override
    public JComponent jcomponent() {
        return SwingPeer.super.jcomponent();
    }

    @Override
    public void resize(double x, double y, double w, double h) {
        AppComponent ep = Applications.effectiveParent(win);
        if(ep instanceof AppDesktop){
            if(ep.peer().toolkitComponent() instanceof JDesktopPane) {
                JDesktopPane desk = (JDesktopPane) ep.peer().toolkitComponent();
                JInternalFrame f = Arrays.stream(desk.getAllFrames())
                        .filter(inf ->
                                inf.getContentPane().getComponentCount() == 1
                                        && inf.getContentPane().getComponent(0) == windowPanel
                        ).findFirst().orElse(null);
                if(f!=null) {
                    desk.getDesktopManager().beginDraggingFrame(f);
                    desk.getDesktopManager().resizeFrame(f, (int) x, (int) y, (int) w, (int) h);
                    desk.getDesktopManager().endResizingFrame(f);
                }
            }
        }
    }
}
