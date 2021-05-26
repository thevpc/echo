package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppCheckBox;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppRadioButton;
import net.thevpc.echo.impl.components.ToggleBase;
import net.thevpc.echo.spi.peers.AppCheckBoxPeer;
import net.thevpc.echo.spi.peers.AppRadioButtonPeer;
import net.thevpc.echo.spi.peers.AppToggleButtonPeer;
import net.thevpc.echo.swing.SwingApplicationUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class SwingToggleButtonPeer implements SwingPeer, AppToggleButtonPeer, AppCheckBoxPeer, AppRadioButtonPeer {
    private Component swingComponent;
    private AppComponent component;

    public SwingToggleButtonPeer() {
    }

    public void install(AppComponent component) {
        ToggleBase ecomp = (ToggleBase) component;
//        AppComponentType ct = component.componentType();
        Object sParent = component.parent() == null ? null : component.parent().peer().toolkitComponent();
        if (
                sParent instanceof JMenu
                        || sParent instanceof JPopupMenu
                        || sParent instanceof JDropDownButton
                        || sParent instanceof JDropDownLabel
        ) {
            if (swingComponent == null) {
                this.swingComponent =
                        (component instanceof AppCheckBox) ? new JCheckBoxMenuItem() :
                                (component instanceof AppRadioButton) ? new JRadioButtonMenuItem() :
                                        new JCheckBoxMenuItem();
                ;
                SwingApplicationUtils.prepareAbstractButton((AbstractButton) swingComponent, ecomp, ecomp.app(),
                        true
                );

            }
        } else {
            if (swingComponent == null) {
                this.swingComponent =
                        (component instanceof AppCheckBox) ? new JCheckBox() :
                                (component instanceof AppRadioButton) ? new JRadioButton() :
                                        new JToggleButton();
                ;
                SwingApplicationUtils.prepareAbstractButton((AbstractButton) swingComponent, ecomp, ecomp.app(),
                        !(sParent instanceof JToolBar)
                );
            }
        }
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }

}
