/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.mydoggy;

import javax.swing.*;

import net.thevpc.common.swing.frame.JInternalFrameHelper;
import net.thevpc.common.swing.win.InternalWindowsHelper;
import net.thevpc.common.swing.win.WindowInfo;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppWindowPeer;
import net.thevpc.echo.swing.peers.SwingPeer;
import net.thevpc.echo.swing.icons.SwingAppImage;

import java.awt.*;

/**
 *
 * @author vpc
 */
public class MyDoggyAppDesktopWindow implements AppWindowPeer, SwingPeer {

    private AppWindow win;
    private JInternalFrameHelper helper;
    private InternalWindowsHelper desktop;
    private JInternalFrame internalFrame;

    @Override
    public void install(AppComponent comp) {
        win=(AppWindow) comp;

        this.desktop = ((MyDoggyAppDockingWorkspace)comp.parent().peer()).getDesktop();
        WindowInfo info = new WindowInfo();
        info.setClosable(false);
        info.setIcon(false);
        info.setIconifiable(false);
        info.setMaximizable(false);
        info.setResizable(false);
        info.setTitle(win.tool().title().get().getValue(comp.app().i18n()));
        info.setComponent(
                (Component) win.tool().component().get().peer().toolkitComponent()
        );

        info.setFrameIcon(getIcon(win.tool().smallIcon().get()));

        internalFrame = desktop.addFrame(info);
        helper = new JInternalFrameHelper(internalFrame);
        win.tool().title().listeners().add(x -> {
            helper.getFrame().setTitle(win.tool().title().get().getValue(comp.app().i18n()));
        });
        win.tool().smallIcon().listeners().add(x -> {
            helper.getFrame().setFrameIcon(
                    getIcon(x.getNewValue())
            );
        });
    }


    private Icon getIcon(AppImage i) {
        if(i !=null){
            i = i.scaleTo(16,16);
        }
        Icon ii = i == null ? null : (Icon) i.peer().toolkitImage();
        return ii;
    }

    @Override
    public Object toolkitComponent() {
        return internalFrame;
    }

    @Override
    public void centerOnDesktop() {

    }
}
