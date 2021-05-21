/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTabPane;
import net.thevpc.echo.spi.peers.AppTabPanePeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;

/**
 * @author vpc
 */
public class SwingTabPanePeer implements SwingPeer, AppTabPanePeer {

    private JTabbedPane tabbedPane;
    private AppTabPane appTabPane;

    @Override
    public void install(AppComponent comp) {
        if (appTabPane == null) {
            appTabPane = (AppTabPane) comp;
            tabbedPane=new JTabbedPane();
        }
    }

    @Override
    public Object toolkitComponent() {
        return tabbedPane;
    }

    @Override
    public void addChild(AppComponent child, int index) {
        JComponent j = (JComponent) child.peer().toolkitComponent();
        tabbedPane.addTab(
                child.title().getOr(x->x==null?null:x.value(child.app().i18n())),
                SwingHelpers.toAwtIcon(child.smallIcon().get()),
                j
        );
        child.title().onChange(
                v->child.title().withValue(
                        x->tabbedPane.setTitleAt(indexOf(child),x==null?null:x.value(child.app().i18n()))));
        child.smallIcon().onChange(
                v->child.smallIcon().withValue(
                        x->tabbedPane.setIconAt(indexOf(child),SwingHelpers.toAwtIcon(x))));
    }

    private int indexOf(AppComponent child){
        JComponent j = (JComponent) child.peer().toolkitComponent();
        return tabbedPane.indexOfComponent(j);
    }

    @Override
    public void removeChild(AppComponent other, int index) {
        tabbedPane.removeTabAt(indexOf(other));
    }
}
