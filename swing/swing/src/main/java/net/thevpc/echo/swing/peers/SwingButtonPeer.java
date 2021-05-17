package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppButtonPeer;
import net.thevpc.echo.impl.components.AppComponentBase;
import net.thevpc.echo.swing.SwingApplicationUtils;

import javax.swing.*;

public class SwingButtonPeer implements SwingPeer , AppButtonPeer {
    private Object peer;
    private AppComponent component;

    public SwingButtonPeer() {
    }

    public void install(AppComponent component0) {
        this.component=component0;
        AppComponentBase ecomp = (AppComponentBase) component;
        Object sParent = component.parent()==null?null:component.parent().peer().toolkitComponent();
        if (
                sParent instanceof JMenu
        ||sParent instanceof JPopupMenu
        ||sParent instanceof JDropDownButton
        ||sParent instanceof JDropDownLabel
        ) {
            if(peer ==null) {
                this.peer = new JMenuItem();
                component.app().toolkit().runUI(()->{
                SwingApplicationUtils.prepareAbstractButton((AbstractButton) peer, ecomp.model(),ecomp.app(),
                        true
                );});
            }
        } else {
            if(peer ==null) {
                this.peer = new JButton();
                component.app().toolkit().runUI(()-> {

                    SwingApplicationUtils.prepareAbstractButton((AbstractButton) peer, ecomp.model(), ecomp.app(),
                            !(sParent instanceof JToolBar)
                    );
                });
            }
        }
    }

    @Override
    public Object toolkitComponent() {
        return peer;
    }
}
