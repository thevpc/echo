package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppTextControl;
import net.thevpc.echo.spi.peers.AppButtonPeer;
import net.thevpc.echo.impl.components.ComponentBase;
import net.thevpc.echo.swing.SwingApplicationUtils;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import java.awt.*;

public class SwingButtonPeer implements SwingPeer , AppButtonPeer {
    private Component swingComponent;
    private AppComponent component;

    public SwingButtonPeer() {
    }

    public void install(AppComponent component0) {
        this.component=component0;
        AppTextControl ecomp = (AppTextControl) component;
        Object sParent = component.parent()==null?null:component.parent().peer().toolkitComponent();
        if (
                sParent instanceof JMenu
        ||sParent instanceof JPopupMenu
        ||sParent instanceof JDropDownButton
        ||sParent instanceof JDropDownLabel
        ) {
            if(swingComponent ==null) {
                this.swingComponent = new JMenuItem();
                SwingApplicationUtils.prepareAbstractButton((AbstractButton) swingComponent, ecomp,ecomp.app(),
                        true
                );
            }
        } else {
            if(swingComponent ==null) {
                this.swingComponent = new JButton();

                    SwingApplicationUtils.prepareAbstractButton((AbstractButton) swingComponent, ecomp, ecomp.app(),
                            !(sParent instanceof JToolBar)
                    );
            }
        }
        SwingHelpers.refreshPanel(swingComponent, 2);
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }
}
