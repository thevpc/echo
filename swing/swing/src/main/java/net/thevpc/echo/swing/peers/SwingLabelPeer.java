package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppLabelPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
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
                SwingPeerHelper.installComponent(component, mLabel);
                ecomp.text().onChangeAndInit(() -> mLabel.setText(Applications.rawString(ecomp.text(),ecomp)));
                ecomp.locale().onChange(() -> mLabel.setText(Applications.rawString(ecomp.text(),ecomp)));

                ecomp.icon().onChangeAndInit(() ->
                        mLabel.setIcon(
                                SwingAppImage.iconOf(ecomp.icon().get())
                        )
                );
            }
        } else {
            if (label == null) {
                this.label = new JLabel();
                JLabel mLabel = (JLabel) this.label;
                SwingPeerHelper.installComponent(component, mLabel);
                ecomp.text().onChangeAndInit(() -> mLabel.setText(Applications.rawString(ecomp.text(),ecomp)));
                ecomp.locale().onChange(() -> mLabel.setText(Applications.rawString(ecomp.text(),ecomp)));
                ecomp.textStyle().align().onChangeAndInit(() -> {
                    Anchor anchor = ecomp.textStyle().align().get();
                    if (anchor == null) {
                        anchor = Anchor.LEFT;
                    }
                    mLabel.setHorizontalTextPosition(
                            anchor == Anchor.LEFT ? SwingConstants.LEFT
                                    : anchor == Anchor.RIGHT ? SwingConstants.RIGHT
                                    : anchor == Anchor.CENTER ? SwingConstants.CENTER
                                    : SwingConstants.LEFT
                    );
                });
                ecomp.icon().onChangeAndInit(() ->
                        mLabel.setIcon(
                                SwingAppImage.iconOf(ecomp.icon().get())
                        )
                );
//                etool.title().onChange(x->mLabel.setText(etool.title().get()));

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
