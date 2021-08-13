package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.color.JButtonColorChooser;
import net.thevpc.common.swing.color.JMenuItemColorChooser;
import net.thevpc.echo.api.components.AppColorButton;
import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppColorButtonPeer;
import net.thevpc.echo.spi.peers.AppColorChooserPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SwingColorChooserPeer implements SwingPeer, AppColorChooserPeer {

    private JColorChooser swingComponent;
    private AppColorChooser appComponent;

    public SwingColorChooserPeer() {
    }

    public void install(AppComponent component0) {
        this.appComponent = (AppColorChooser) component0;
        AppComponent ep = Applications.effectiveParent(component0);
        swingComponent = new JColorChooser();
        SwingPeerHelper.installComponent(this.appComponent, swingComponent);
        appComponent.value().onChangeAndInit(() -> {
            swingComponent.setColor(SwingHelpers.toAwtColor(appComponent.value().get()));
        });
        swingComponent.getSelectionModel().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Color c = swingComponent.getColor();
                appComponent.value().set(SwingHelpers.fromAwtColor(c, appComponent.app()));
            }
        });
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }
}
