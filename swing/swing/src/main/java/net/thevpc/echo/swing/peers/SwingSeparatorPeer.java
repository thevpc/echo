package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppSeparator;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppSeparatorPeer;
import net.thevpc.echo.swing.SwingPeerHelper;

import javax.swing.*;

public class SwingSeparatorPeer implements SwingPeer, AppSeparatorPeer {
    private Object separator;
    private AppComponent component;

    public SwingSeparatorPeer() {
    }

    public void install(AppComponent component) {
        if (separator != null) {
            return;
        }
        AppSeparator etool = (AppSeparator) component;
        AppComponent ep = Applications.effectiveParent(component);
        Object sParent = ep == null ? null : ep.peer().toolkitComponent();
        double height = ((Number) etool.height().get()).doubleValue();
        double width = ((Number) etool.width().get()).doubleValue();
        if (
                sParent instanceof JMenu
                        || sParent instanceof JPopupMenu
                        || sParent instanceof JDropDownButton
                        || sParent instanceof JDropDownLabel
        ) {
            if (height <= 0 && width <= 0) {
                separator = new JPopupMenu.Separator();
            } else {
                separator = createSpacer(height, width);
            }
        } else if (sParent instanceof JToolBar) {
            if (height <= 0 && width <= 0) {
                separator = new JToolBar.Separator();
            } else {
                separator = createSpacer(height, width);
            }
        } else {
            separator = createSpacer(height, width);
        }
        SwingPeerHelper.installComponent(component, (JComponent) separator);
    }

    @Override
    public Object toolkitComponent() {
        return separator;
    }

    private JComponent createSpacer(double height, double width) {
        if(height<width){
            return new JSeparator(JSeparator.VERTICAL);
        }else {
            return new JSeparator(JSeparator.HORIZONTAL);
        }
//        return new Box.Filler(new Dimension(0, 0), new Dimension(0, 0),
//                new Dimension(
//                        (int) (width <= 0 ? 0 : isMax(width) ? Short.MAX_VALUE : width)
//                        , (int) (width <= 0 ? 0 : isMax(height) ? Short.MAX_VALUE : height)
//                ));
    }

    boolean isMax(Double d) {
        return d.equals(Double.MAX_VALUE) || d.compareTo((double) Short.MAX_VALUE) >= 0;
    }
}
