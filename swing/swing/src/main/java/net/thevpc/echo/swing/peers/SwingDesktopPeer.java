/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.dock.JDockPane;
import net.thevpc.common.swing.win.InternalWindowsHelper;
import net.thevpc.common.swing.win.WindowInfo;
import net.thevpc.echo.AppDimension;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppDesktopPeer;
import net.thevpc.echo.swing.peers.SwingPeer;

import javax.swing.*;
import java.awt.*;

/**
 * @author vpc
 */
public class SwingDesktopPeer implements SwingPeer, AppDesktopPeer {

    InternalWindowsHelper desktop = new InternalWindowsHelper();
    private AppDesktop appDesktop;

    public static JDockPane.DockAnchor toDocAnchor(AppWindowAnchor anchor) {
        return JDockPane.DockAnchor.valueOf(anchor.name());
    }

    @Override
    public void install(AppComponent comp) {
        if (appDesktop == null) {
            appDesktop = (AppDesktop) comp;
        }
    }

    @Override
    public void addChild(AppComponent other, int index) {
        AppWindow win = (AppWindow) other;
        JComponent j = (JComponent) win.peer().toolkitComponent();

        WindowInfo info = new WindowInfo();
        info.setClosable(false);
        info.setIcon(false);
        info.setIconifiable(false);
        info.setMaximizable(false);
        info.setResizable(false);
        info.setTitle(
                win.model().title().get() == null ? null :
                        win.model().title().get().getValue(win.app().i18n())
        );
        info.setComponent((Component) win.model().component().get().peer().toolkitComponent());
        JInternalFrame frame = desktop.addFrame(info);
        frame.putClientProperty(AppWindow.class.getName(), win);
        win.model().title().listeners().add(
                v -> win.model().title().withValue(
                        x -> frame.setTitle(x == null ? null : x.getValue(win.app().i18n()))));
        win.model().smallIcon().listeners().add(
                v -> win.model().smallIcon().withValue(
                        x -> frame.setFrameIcon(x == null ? null : (Icon) x.peer().toolkitImage())));
        win.model().closable().listeners().add(
                v -> win.model().closable().withValue(
                        x -> frame.setClosable(x)));
//        win.model().anchor().listeners().add(
//                v -> win.model().anchor().withValue(
//                        x -> frame.setWindowAnchor(win.model().id(), toDocAnchor(x))));
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        AppWindow win = (AppWindow) other;
        for (JInternalFrame internalFrame : desktop.getDesktop().getAllFrames()) {
            AppWindow win2 = (AppWindow) internalFrame.getClientProperty(AppWindow.class.getName());
            if (win2 == win) {
                desktop.getDesktop().remove(internalFrame);
            }
        }
    }

    @Override
    public Object toolkitComponent() {
        return desktop.getDesktop();
    }

    @Override
    public AppDimension size() {
        Dimension size = desktop.getDesktop().getSize();
        return new AppDimension(
                size.getWidth(), size.getHeight()
        );
    }

}
