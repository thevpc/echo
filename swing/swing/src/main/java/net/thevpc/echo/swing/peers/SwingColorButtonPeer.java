package net.thevpc.echo.swing.peers;

import java.awt.Menu;
import javax.swing.JComponent;
import net.thevpc.common.swing.color.JButtonColorChooser;
import net.thevpc.common.swing.color.JMenuItemColorChooser;
import net.thevpc.echo.Color;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppColorButtonPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

public class SwingColorButtonPeer implements SwingPeer, AppColorButtonPeer {

    private JComponent swingComponent;
    private AppColorButton appComponent;

    public SwingColorButtonPeer() {
    }

    public void install(AppComponent component0) {
        this.appComponent = (AppColorButton) component0;
        AppComponent ep = Applications.effectiveParent(component0);
        if (ep instanceof net.thevpc.echo.Menu) {
            swingComponent = new JMenuItemColorChooser(null, true);
            appComponent.title().onChangeAndInit(
                    () -> ((JMenuItemColorChooser) swingComponent).setText(
                            Applications.rawString(appComponent.title(), appComponent)
                    )
            );
        } else {
            swingComponent = new JButtonColorChooser(null, true);
        }
        SwingPeerHelper.installComponent(this.appComponent, swingComponent);
        appComponent.value().onChangeAndInit(() -> {
            if (swingComponent instanceof JButtonColorChooser) {
                ((JButtonColorChooser) swingComponent).setValue(SwingHelpers.toAwtColor(appComponent.value().get()));
            } else {
                ((JMenuItemColorChooser) swingComponent).setValue(SwingHelpers.toAwtColor(appComponent.value().get()));
            }
        });
        swingComponent.addPropertyChangeListener(JButtonColorChooser.PROPERTY_COLOR_SET,
                e -> {
                    java.awt.Color c = (swingComponent instanceof JButtonColorChooser)
                            ? ((JButtonColorChooser) swingComponent).getValue()
                            : ((JMenuItemColorChooser) swingComponent).getValue();

                    appComponent.value().set(
                            SwingHelpers.fromAwtColor(c, appComponent.app())
                    );
                });
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }
}
