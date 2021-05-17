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
import net.thevpc.echo.api.components.AppTabs;
import net.thevpc.echo.api.components.AppWindow;
import net.thevpc.echo.api.peers.AppDockPeer;
import net.thevpc.echo.api.peers.AppTabsPeer;

import javax.swing.*;

/**
 * @author vpc
 */
public class SwingTabsPeer implements SwingPeer, AppTabsPeer {

    private JTabbedPane tabbedPane;
    private AppTabs appTabs;

    @Override
    public void install(AppComponent comp) {
        if (appTabs == null) {
            appTabs = (AppTabs) comp;
            tabbedPane=new JTabbedPane();
        }
    }

    @Override
    public Object toolkitComponent() {
        return tabbedPane;
    }

    @Override
    public void addChild(AppComponent other, int index) {
        AppWindow win=(AppWindow) other;
        JComponent j = (JComponent) win.peer().toolkitComponent();
        tabbedPane.addTab(
                win.model().title().getOr(x->x==null?null:x.getValue(win.app().i18n())),
                win.model().smallIcon().getOr(x->x==null?null:(Icon) x.peer().toolkitImage()),
                j
        );
        win.model().title().listeners().add(
                v->win.model().title().withValue(
                        x->tabbedPane.setTitleAt(indexOf(win),x==null?null:x.getValue(win.app().i18n()))));
        win.model().smallIcon().listeners().add(
                v->win.model().smallIcon().withValue(
                        x->tabbedPane.setIconAt(indexOf(win),x==null?null:(Icon) x.peer().toolkitImage())));
    }

    private int indexOf(AppWindow win){
        JComponent j = (JComponent) win.peer().toolkitComponent();
        return tabbedPane.indexOfComponent(j);
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        AppWindow win=(AppWindow) other;
        tabbedPane.removeTabAt(indexOf(win));
    }
}
