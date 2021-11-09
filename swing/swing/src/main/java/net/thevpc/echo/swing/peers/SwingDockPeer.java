/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.swing.dock.JDockAnchor;
import net.thevpc.common.swing.dock.JDockPane;
import net.thevpc.common.swing.dock.JDockTools;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDock;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppDockPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;

/**
 * @author thevpc
 */
public class SwingDockPeer implements SwingPeer, AppDockPeer {

    private AppDock dock;
    private JDockPane workspacePanel;

    public static JDockAnchor toDocAnchor(Anchor anchor) {
        return anchor == null ? JDockAnchor.CENTER :
                JDockAnchor.valueOf(anchor.name());
    }

    @Override
    public void install(AppComponent comp) {
        if (dock == null) {
            dock = (AppDock) comp;
            workspacePanel = new JDockPane();
            SwingPeerHelper.installComponent(dock, workspacePanel);
            workspacePanel.addListener(new JDockTools.SelectionListener() {
                @Override
                public void onSelectionChanged(String id, JComponent component, boolean selected, JDockAnchor anchor) {
                    SwingPeerHelper.appComponentOf(component).active().set(selected);
                }
            });
        }
    }

    @Override
    public void addChild(AppComponent child, int index) {
        JComponent j = (JComponent) child.peer().toolkitComponent();
        boolean closable = false;
        if (child instanceof AppWindow) {
            closable = ((AppWindow) child).closable().get();
        }
        workspacePanel.add(
                child.id(),
                j,
                Applications.rawString(child.title(), child),
                SwingHelpers.toAwtIcon(child.icon().get()),
                closable,
                toDocAnchor(child.anchor().get())
        );
        child.active().onChangeAndInit(() -> workspacePanel.setWindowActive(child.id(),child.active().get()));
        child.title().onChange(v -> workspacePanel.setWindowTitle(child.id(), Applications.rawString(child.title(), child)));
        child.locale().onChange(v -> workspacePanel.setWindowTitle(child.id(), Applications.rawString(child.title(), child)));
        child.icon().onChange(v -> workspacePanel.setWindowIcon(child.id(), SwingHelpers.toAwtIcon(child.icon().get())));
        if (child instanceof AppWindow) {
            WritableBoolean cp = ((AppWindow) child).closable();
            cp.onChange(
                    v -> cp.withValue(
                            x -> workspacePanel.setWindowClosable(child.id(), x)));
        }
        child.anchor().onChange(
                v -> child.anchor().withValue(
                        x -> workspacePanel.setWindowAnchor(child.id(), toDocAnchor(x))));
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        AppComponent win = (AppComponent) other;
        workspacePanel.remove(win.id());
    }

    @Override
    public Object toolkitComponent() {
        return workspacePanel;
    }

    public JDockPane getWorkspacePanel() {
        return workspacePanel;
    }
}
