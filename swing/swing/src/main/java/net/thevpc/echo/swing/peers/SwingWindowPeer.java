package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.frame.JInternalFrameHelper;
import net.thevpc.common.swing.win.InternalWindowsHelper;
import net.thevpc.echo.AppBounds;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.swing.peers.SwingPeer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SwingWindowPeer implements SwingPeer, AppWindowPeer {

//    private JInternalFrameHelper helper;
//    private InternalWindowsHelper desktop;
    private AppWindow win;
    private JComponent jComponent;

    @Override
    public void install(AppComponent comp) {
        if (this.win == null) {
            this.win = (AppWindow) comp;
            win.app().toolkit().runUI(() -> {
                        JPanel pp = new JPanel(new BorderLayout());
                        pp.setName("SwingWindowPeer.Container");
                        JComponent cc = (JComponent) win.model().component().getOr(x -> x == null ? null : x.peer().toolkitComponent());
                        jComponent = pp;
                        if (cc != null) {
                            pp.add(cc, BorderLayout.CENTER);
                        }
                        win.model().component().listeners().add(x -> {
                                    if (x.getNewValue() == null) {
                                        jComponent.removeAll();
                                    } else {
                                        jComponent.add((JComponent) ((AppComponent) x.getNewValue()).peer().toolkitComponent());
                                    }
                                }
                        );
                    });
//                AppComponentPeer parentPeer = comp.parent().peer();
//                if(parentPeer instanceof SwingWorkspacePeer) {
//                    SwingWorkspacePeer p = (SwingWorkspacePeer) parentPeer;
//                    AppWindowAnchor anchor = win.model().anchor().get();
//                    if (anchor == AppWindowAnchor.DESKTOP) {
//                        this.desktop = p.getDesktop();
//                        WindowInfo info = new WindowInfo();
//                        info.setClosable(false);
//                        info.setIcon(false);
//                        info.setIconifiable(false);
//                        info.setMaximizable(false);
//                        info.setResizable(false);
//                        info.setTitle(
//                                win.model().title().get() == null ? null :
//                                        win.model().title().get().getValue(comp.app().i18n())
//                        );
//                        info.setComponent((Component) win.model().component().get().peer().toolkitComponent());
//                        this.helper = new JInternalFrameHelper(desktop.addFrame(info));
//
//                        SwingAppImage aim = (SwingAppImage) win.model().smallIcon().get();
//                        info.setFrameIcon(aim == null ? null : aim.getIcon());
//
//                        JInternalFrame j = desktop.addFrame(info);
//                        helper = new JInternalFrameHelper(j);
//                        win.model().title().listeners().add(x -> {
//                            helper.getFrame().setTitle(
//                                    win.model().title().get() == null ? null :
//                                            win.model().title().get().getValue(comp.app().i18n())
//                            );
//                        });
//                        win.model().smallIcon().listeners().add(x -> {
//                            SwingAppImage aim2 = (SwingAppImage) win.model().smallIcon().get();
//                            helper.getFrame().setFrameIcon(aim2 == null ? null : aim2.getIcon());
//                        });
//                    } else {
////                        p.getWorkspacePanel().add(win.model().id(),
////                                (JComponent) win.model().component().getOr(x -> x == null ? null : x.peer().toolkitComponent()),
////                                win.model().title().getOr(x -> x == null ? null : x.getValue(comp.app().i18n())),
////                                (Icon) win.model().smallIcon().getOr(x -> (x == null ? null : x.peer().toolkitImage())),
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
        return jComponent;
    }

    @Override
    public JComponent jcomponent() {
        return SwingPeer.super.jcomponent();
    }

    @Override
    public void resize(double x, double y, double w, double h) {
        if(win.parent() instanceof AppDesktop){
            if(win.parent().peer().toolkitComponent() instanceof JDesktopPane) {
                JDesktopPane desk = (JDesktopPane) win.parent().peer().toolkitComponent();
                JInternalFrame f = Arrays.stream(desk.getAllFrames())
                        .filter(inf ->
                                inf.getContentPane().getComponentCount() == 1
                                        && inf.getContentPane().getComponent(0) == jComponent
                        ).findFirst().orElse(null);
                if(f!=null) {
                    desk.getDesktopManager().beginDraggingFrame(f);
                    desk.getDesktopManager().resizeFrame(f, (int) x, (int) y, (int) w, (int) h);
                    desk.getDesktopManager().endResizingFrame(f);
                }
            }
        }
    }

    @Override
    public AppBounds bounds() {
        Rectangle r = jComponent.getBounds();
        return new AppBounds(
                r.getX(),
                r.getY(),
                r.getWidth(),
                r.getHeight()
        );
    }
}
