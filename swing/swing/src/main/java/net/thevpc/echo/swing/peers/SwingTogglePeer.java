package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppCheckBox;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppRadioButton;
import net.thevpc.echo.api.tools.AppToolToggle;
import net.thevpc.echo.impl.components.AppComponentBase;
import net.thevpc.echo.swing.SwingApplicationUtils;

import javax.swing.*;

public class SwingTogglePeer implements SwingPeer {
    private Object peer;
    private AppComponent component;

    public SwingTogglePeer() {
    }

    public void install(AppComponent component) {
        AppComponentBase ecomp = (AppComponentBase) component;
        AppToolToggle etool=(AppToolToggle) ecomp.tool();
//        AppComponentType ct = component.componentType();
        Object sParent = component.parent()==null?null:component.parent().peer().toolkitComponent();
        if (
                sParent instanceof JMenu
        ||sParent instanceof JPopupMenu
        ||sParent instanceof JDropDownButton
        ||sParent instanceof JDropDownLabel
        ) {
            if(peer ==null) {
                this.peer =
                        (component instanceof AppCheckBox)? new JCheckBoxMenuItem():
                                (component instanceof AppRadioButton)? new JRadioButtonMenuItem():
                                new JCheckBoxMenuItem();
                ;
                SwingApplicationUtils.prepareAbstractButton((AbstractButton) peer, ecomp.tool(),ecomp.app(),
                        true
                );
            }
        } else {
            if(peer ==null) {
                this.peer =
                        (component instanceof AppCheckBox)? new JCheckBox():
                                (component instanceof AppRadioButton)? new JRadioButton():
                                        new JToggleButton();
                ;
                SwingApplicationUtils.prepareAbstractButton((AbstractButton) peer, ecomp.tool(),ecomp.app(),
                        !(sParent instanceof JToolBar)
                        );
            }
        }
    }

    @Override
    public Object toolkitComponent() {
        return peer;
    }

}