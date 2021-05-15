package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.impl.components.AppComponentBase;
import net.thevpc.echo.swing.icons.SwingAppImage;

import javax.swing.*;
import java.awt.*;

public class SwingLabelPeer implements SwingPeer {
    private Object label;
    private AppComponent component;

    public SwingLabelPeer() {
    }

    public void install(AppComponent component) {
        AppComponentBase ecomp = (AppComponentBase) component;
        AppToolText etool = (AppToolText) ecomp.tool();
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
                etool.title().listeners().add(x -> mLabel.setText(
                        etool.title().get() == null ? "" :
                                etool.title().get().getValue(etool.app())
                ));
                etool.smallIcon().listeners().add(x -> mLabel.setIcon(
                        SwingAppImage.iconOf(etool.smallIcon().get())
                ));
            }
        } else {
            if (label == null) {
                this.label = new JLabel();
                JLabel mLabel = (JLabel) this.label;
                etool.text().listeners().add(x -> mLabel.setText(
                        etool.text().get() == null ? "" :
                                etool.text().get().getValue(etool.app())
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
