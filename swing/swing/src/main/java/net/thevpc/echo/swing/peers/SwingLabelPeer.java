package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppLabelPeer;
import net.thevpc.echo.api.tools.AppTextModel;
import net.thevpc.echo.impl.components.AppComponentBase;
import net.thevpc.echo.swing.icons.SwingAppImage;

import javax.swing.*;
import java.awt.*;

public class SwingLabelPeer implements SwingPeer, AppLabelPeer {
    private Object label;
    private AppComponent component;

    public SwingLabelPeer() {
    }

    public void install(AppComponent component) {
        AppComponentBase ecomp = (AppComponentBase) component;
        AppTextModel etool = (AppTextModel) ecomp.model();
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
                        etool.text().getOr(tt-> tt== null ? "" :
                                tt.getValue(etool.app().i18n())
                        ));
                etool.text().listeners().add(x -> mLabel.setText(
                        etool.text().getOr(tt-> tt== null ? "" :
                                tt.getValue(etool.app().i18n())
                )));
                etool.smallIcon().listeners().add(x -> mLabel.setIcon(
                        SwingAppImage.iconOf(etool.smallIcon().get())
                ));
            }
        } else {
            if (label == null) {
                this.label = new JLabel();
                JLabel mLabel = (JLabel) this.label;
                mLabel.setText(
                        etool.text().getOr(tt-> tt== null ? "" :
                                tt.getValue(etool.app().i18n())
                        ));
                etool.text().listeners().add(x -> mLabel.setText(
                        etool.text().get() == null ? "" :
                                etool.text().get().getValue(etool.app().i18n())
                ));
//                etool.title().listeners().add(x->mLabel.setText(etool.title().get()));
                etool.smallIcon().listeners().add(x -> mLabel.setIcon(
                        SwingAppImage.iconOf(etool.smallIcon().get())
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
