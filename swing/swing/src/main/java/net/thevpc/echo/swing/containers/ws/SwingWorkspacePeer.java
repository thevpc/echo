/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.containers.ws;

import net.thevpc.common.swing.frame.JInternalFrameHelper;
import net.thevpc.common.swing.win.InternalWindowsHelper;
import net.thevpc.common.swing.win.WindowInfo;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.AppWorkspace;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.api.peers.AppWorkspacePeer;
import net.thevpc.echo.swing.icons.SwingAppImage;
import net.thevpc.echo.swing.peers.SwingPeer;
import net.thevpc.echo.swing.raw.JDockPane;

import javax.swing.*;
import java.awt.*;

/**
 * @author vpc
 */
public class SwingWorkspacePeer implements SwingPeer, AppWorkspacePeer {

    private InternalWindowsHelper desktop;
    private AppWorkspace workspace;
    private JDockPane workspacePanel;

    @Override
    public void install(AppComponent comp) {
        if (desktop == null) {
            workspace = (AppWorkspace) comp;
            workspacePanel = new JDockPane();
            desktop = new InternalWindowsHelper();
            workspacePanel.add(
                    "Desktop",
                    desktop.getDesktop(), "Desktop", null, false, JDockPane.DockAnchor.CENTER);
        }
    }

    @Override
    public Object toolkitComponent() {
        return workspacePanel;
    }

    public boolean dockingSupported() {
        return true;
    }

    public boolean desktopEnabled() {
        return true;
    }

    @Override
    public void tileDesktop(boolean vertical) {
        if (!desktopEnabled()) {
            return;
        }
        desktop.tileFrames();
    }

    @Override
    public void iconDesktop(boolean iconify) {
        if (!desktopEnabled()) {
            return;
        }
        if (iconify) {
            desktop.iconifyFrames();
        } else {
            desktop.deiconifyFrames();
        }
    }

    @Override
    public void closeAllDesktop() {
        if (!desktopEnabled()) {
            return;
        }
        desktop.closeFrames();
    }

    @Override
    public AppWindowPeer addWindowImpl(String id, AppComponent component, AppWindowAnchor anchor) {
        return new DesktopWin();
    }

    @Override
    public void removeWindowImpl(String id, AppWindowPeer a) {
        JComponent j = (JComponent) a.toolkitComponent();
        desktop.removeWindow(j);
    }

    private static class DesktopWin implements SwingPeer, AppWindowPeer {

        private JInternalFrameHelper helper;
        private InternalWindowsHelper desktop;
        private AppWindow win;

        @Override
        public void install(AppComponent comp) {
            SwingWorkspacePeer p = (SwingWorkspacePeer) comp.parent().peer();
            if (this.win == null) {
                this.win = (AppWindow) comp;
                AppWindowAnchor anchor = win.tool().anchor().get();
                if (anchor == AppWindowAnchor.DESKTOP) {
                    this.desktop = p.desktop;
                    WindowInfo info = new WindowInfo();
                    info.setClosable(false);
                    info.setIcon(false);
                    info.setIconifiable(false);
                    info.setMaximizable(false);
                    info.setResizable(false);
                    info.setTitle(
                            win.tool().title().get() == null ? null :
                                    win.tool().title().get().getValue(comp.app().i18n())
                    );
                    info.setComponent((Component) win.tool().component().get().peer().toolkitComponent());
                    this.helper = new JInternalFrameHelper(desktop.addFrame(info));

                    SwingAppImage aim = (SwingAppImage) win.tool().smallIcon().get();
                    info.setFrameIcon(aim == null ? null : aim.getIcon());

                    JInternalFrame j = desktop.addFrame(info);
                    helper = new JInternalFrameHelper(j);
                    win.tool().title().listeners().add(x -> {
                        helper.getFrame().setTitle(
                                win.tool().title().get() == null ? null :
                                        win.tool().title().get().getValue(comp.app().i18n())
                        );
                    });
                    win.tool().smallIcon().listeners().add(x -> {
                        SwingAppImage aim2 = (SwingAppImage) win.tool().smallIcon().get();
                        helper.getFrame().setFrameIcon(aim2 == null ? null : aim2.getIcon());
                    });
                } else {
                    p.workspacePanel.add(win.tool().id(),
                            (JComponent) win.tool().component().getOr(x->x==null?null:x.peer().toolkitComponent()),
                            win.tool().title().getOr(x->x==null?null:x.getValue(comp.app().i18n())),
                            (Icon) win.tool().smallIcon().getOr(x->(x==null?null: x.peer().toolkitImage())),
                            false,
                            getAnchor(anchor)
                            );
                }
            }
        }

        @Override
        public Object toolkitComponent() {
            return desktop;
        }

        @Override
        public void centerOnDesktop() {
        }


        @Override
        public JComponent jcomponent() {
            return SwingPeer.super.jcomponent();
        }
    }

    private static JDockPane.DockAnchor getAnchor(AppWindowAnchor anchor) {
        return JDockPane.DockAnchor.valueOf(anchor.name());
    }


}
