/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.dock.JDockPane;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDock;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppDockPeer;
import net.thevpc.echo.swing.peers.SwingPeer;

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
        AppWindow win=(AppWindow) other;
        JComponent j = (JComponent) win.peer().toolkitComponent();
        workspacePanel.add(
                win.model().id(),
                j, win.model().title().getOr(x->x==null?null:x.getValue(win.app().i18n())),
                win.model().smallIcon().getOr(x->x==null?null:(Icon) x.peer().toolkitImage()),
                win.model().closable().get(),
                toDocAnchor(win.model().anchor().get())
        );
        win.model().title().listeners().add(
                v->win.model().title().withValue(
                        x->workspacePanel.setWindowTitle(win.model().id(),x==null?null:x.getValue(win.app().i18n()))));
        win.model().smallIcon().listeners().add(
                v->win.model().smallIcon().withValue(
                        x->workspacePanel.setWindowIcon(win.model().id(),x==null?null:(Icon) x.peer().toolkitImage())));
        win.model().closable().listeners().add(
                v->win.model().closable().withValue(
                        x->workspacePanel.setWindowClosable(win.model().id(),x)));
        win.model().anchor().listeners().add(
                v->win.model().anchor().withValue(
                        x->workspacePanel.setWindowAnchor(win.model().id(), toDocAnchor(x))));
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        AppWindow win=(AppWindow) other;
        workspacePanel.remove(win.model().id());
    }

    public static JDockPane.DockAnchor toDocAnchor(AppWindowAnchor anchor) {
        return JDockPane.DockAnchor.valueOf(anchor.name());
    }

    public JDockPane getWorkspacePanel() {
        return workspacePanel;
    }
}
