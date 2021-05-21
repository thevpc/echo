/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.dock.JDockPane;
import net.thevpc.common.swing.win.InternalWindowsHelper;
import net.thevpc.common.swing.win.WindowInfo;
import net.thevpc.echo.Dimension;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDesktop;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.spi.peers.AppDesktopPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;

/**
 * @author vpc
 */
public class SwingDesktopPeer implements SwingPeer, AppDesktopPeer {

    InternalWindowsHelper desktop = new InternalWindowsHelper();
    private AppDesktop appDesktop;

    public static JDockPane.DockAnchor toDocAnchor(Anchor anchor) {
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
                win.title().get() == null ? null :
                        win.title().get().value(win.app().i18n())
        );
        info.setComponent((Component) win.component().get().peer().toolkitComponent());
        JInternalFrame frame = desktop.addFrame(info);
        frame.putClientProperty(AppWindow.class.getName(), win);
        win.title().onChange(
                v -> win.title().withValue(
                        x -> frame.setTitle(x == null ? null : x.value(win.app().i18n()))));
        win.smallIcon().onChange(
                v -> win.smallIcon().withValue(
                        x -> frame.setFrameIcon(SwingHelpers.toAwtIcon(x))));
        win.closable().onChange(
                v -> win.closable().withValue(
                        x -> frame.setClosable(x)));
//        win.anchor().onChange(
//                v -> win.anchor().withValue(
//                        x -> frame.setWindowAnchor(win.id(), toDocAnchor(x))));
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
    public Dimension size() {
        java.awt.Dimension size = desktop.getDesktop().getSize();
        return new Dimension(
                size.getWidth(), size.getHeight()
        );
    }

}
