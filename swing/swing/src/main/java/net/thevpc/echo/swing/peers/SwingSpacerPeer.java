package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppSpacer;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppSpacerPeer;
import net.thevpc.echo.impl.components.ComponentBase;

import javax.swing.*;
import java.awt.*;

public class SwingSpacerPeer implements SwingPeer, AppSpacerPeer {
    private Object separator;
    private AppComponent component;

    public SwingSpacerPeer() {
    }

    public void install(AppComponent component) {
        if(separator !=null) {
            return;
        }
        AppSpacer ecomp = (AppSpacer) component;
        AppComponent ep = Applications.effectiveParent(component);
        Object sParent = ep==null?null:ep.peer().toolkitComponent();
        double height = ecomp.height().get().doubleValue();
        double width = ecomp.width().get().doubleValue();
        if (
                sParent instanceof JMenu
                        ||sParent instanceof JPopupMenu
                        ||sParent instanceof JDropDownButton
                        ||sParent instanceof JDropDownLabel
        ) {
            separator= createSpacer(height, width);
        } else if(sParent instanceof JToolBar){
            separator= createSpacer(height, width);
        } else {
            separator= createSpacer(height, width);
        }
    }

    private Box.Filler createSpacer(double height, double width) {
        return new Box.Filler(new Dimension(0, 0), new Dimension(0, 0),
                new Dimension(
                        (int) (width <= 0 ? 0 : isMax(width) ? Short.MAX_VALUE : width)
                        , (int) (width <= 0 ? 0 : isMax(height) ? Short.MAX_VALUE : height)
                ));
    }

    @Override
    public Object toolkitComponent() {
        return separator;
    }

    boolean isMax(Double d) {
        return d.equals(Double.MAX_VALUE) || d.compareTo((double) Short.MAX_VALUE) >= 0;
    }
}
