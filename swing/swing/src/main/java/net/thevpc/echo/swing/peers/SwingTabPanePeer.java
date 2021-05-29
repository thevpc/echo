/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTabPane;
import net.thevpc.echo.impl.Applications;
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
                Applications.rawString(child.title(),child),
                SwingHelpers.toAwtIcon(child.icon().get()),
                j
        );
        child.title().onChangeAndInit(
                v->child.title().withValue(
                        x->tabbedPane.setTitleAt(indexOf(child),
                                Applications.rawString(x,child)
                        )));
        child.locale().onChangeAndInit(
                v->child.title().withValue(
                        x->tabbedPane.setTitleAt(indexOf(child),
                                Applications.rawString(x,child)
                        )));

        child.icon().onChangeAndInit(
                v->child.icon().withValue(
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
