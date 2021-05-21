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
                j, win.title().getOr(x->x==null?null:x.value(win.app().i18n())),
                SwingHelpers.toAwtIcon(win.smallIcon().get()),
                closable,
                toDocAnchor(win.anchor().get())
        );
        win.title().onChange(
                v->win.title().withValue(
                        x->workspacePanel.setWindowTitle(win.id(),x==null?null:x.value(win.app().i18n()))));
        win.smallIcon().onChange(
                v->win.smallIcon().withValue(
                        x->workspacePanel.setWindowIcon(win.id(),SwingHelpers.toAwtIcon(x))));
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
