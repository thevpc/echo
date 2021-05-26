/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.swing.dock.JDockPane;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDock;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppDockPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;

/**
 * @author vpc
 */
public class SwingDockPeer implements SwingPeer, AppDockPeer {

    private AppDock dock;
    private JDockPane workspacePanel;

    @Override
    public void install(AppComponent comp) {
        if (dock == null) {
            dock = (AppDock) comp;
            workspacePanel = new JDockPane();
        }
    }

    @Override
    public Object toolkitComponent() {
        return workspacePanel;
    }

    @Override
    public void addChild(AppComponent other, int index) {
        AppComponent win=(AppComponent) other;
        JComponent j = (JComponent) win.peer().toolkitComponent();
        boolean closable=false;
        if(win instanceof AppWindow){
            closable=((AppWindow)win).closable().get();
        }
        workspacePanel.add(
                win.id(),
                j,
                Applications.rawString(win.title(),win),
                SwingHelpers.toAwtIcon(win.smallIcon().get()),
                closable,
                toDocAnchor(win.anchor().get())
        );
        win.title().onChange(v->workspacePanel.setWindowTitle(win.id(),Applications.rawString(win.title(),win)));
        win.locale().onChange(v->workspacePanel.setWindowTitle(win.id(),Applications.rawString(win.title(),win)));
        win.smallIcon().onChange(v->workspacePanel.setWindowIcon(win.id(),SwingHelpers.toAwtIcon(win.smallIcon().get())));
        if(win instanceof AppWindow) {
            WritableBoolean cp = ((AppWindow) win).closable();
            cp.onChange(
                    v -> cp.withValue(
                            x -> workspacePanel.setWindowClosable(win.id(), x)));
        }
        win.anchor().onChange(
                v->win.anchor().withValue(
                        x->workspacePanel.setWindowAnchor(win.id(), toDocAnchor(x))));
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        AppComponent win=(AppComponent) other;
        workspacePanel.remove(win.id());
    }

    public static JDockPane.DockAnchor toDocAnchor(Anchor anchor) {
        return JDockPane.DockAnchor.valueOf(anchor.name());
    }

    public JDockPane getWorkspacePanel() {
        return workspacePanel;
    }
}
