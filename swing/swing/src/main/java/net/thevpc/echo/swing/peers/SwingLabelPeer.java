package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.spi.peers.AppLabelPeer;
import net.thevpc.echo.swing.icons.SwingAppImage;

import javax.swing.*;
import java.awt.*;

public class SwingLabelPeer implements SwingPeer, AppLabelPeer {
    private Object label;
    private AppComponent component;

    public SwingLabelPeer() {
    }

    public void install(AppComponent component) {
        AppLabel ecomp = (AppLabel) component;
        Object sParent = component.parent() == null ? null : component.parent().peer().toolkitComponent();
        if (
                sParent instanceof JMenu
                        || sParent instanceof JPopupMenu
                        || sParent instanceof JDropDownButton
                        || sParent instanceof JDropDownLabel
        ) {
            if (label == null) {
                this.label = new JMenuItem();
                JMenuItem mLabel = (JMenuItem) this.label;
                mLabel.setText(
                        ecomp.text().getOr(tt-> tt== null ? "" :
                                tt.value(ecomp.app().i18n())
                        ));
                ecomp.text().onChange(x -> mLabel.setText(
                        ecomp.text().getOr(tt-> tt== null ? "" :
                                tt.value(ecomp.app().i18n())
                )));
                ecomp.smallIcon().onChange(x -> mLabel.setIcon(
                        SwingAppImage.iconOf(ecomp.smallIcon().get())
                ));
            }
        } else {
            if (label == null) {
                this.label = new JLabel();
                JLabel mLabel = (JLabel) this.label;
                mLabel.setText(
                        ecomp.text().getOr(tt-> tt== null ? "" :
                                tt.value(ecomp.app().i18n())
                        ));
                ecomp.text().onChange(x -> mLabel.setText(
                        ecomp.text().get() == null ? "" :
                                ecomp.text().get().value(ecomp.app().i18n())
                ));
//                etool.title().onChange(x->mLabel.setText(etool.title().get()));
                ecomp.smallIcon().onChange(x -> mLabel.setIcon(
                        SwingAppImage.iconOf(ecomp.smallIcon().get())
                ));
            }
        }
    }

    @Override
    public Object toolkitComponent() {
        return label;
    }

    @Override
    public Component awtComponent() {
        return (Component) toolkitComponent();
    }

    @Override
    public JComponent jcomponent() {
        return (JComponent) toolkitComponent();
    }
}
