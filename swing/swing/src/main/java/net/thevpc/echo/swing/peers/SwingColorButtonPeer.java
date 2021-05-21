package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.color.JButtonColorChooser;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.spi.peers.AppColorButtonPeer;
import net.thevpc.echo.swing.helpers.SwingHelpers;

import java.awt.*;

public class SwingColorButtonPeer implements SwingPeer, AppColorButtonPeer {
    private JButtonColorChooser swingComponent;
    private AppColorButton appComponent;

    public SwingColorButtonPeer() {
    }

    public void install(AppComponent component0) {
        this.appComponent = (AppColorButton) component0;

        swingComponent=new JButtonColorChooser(null,true);
        appComponent.value().listeners().addInstall(()->{
            swingComponent.setValue(SwingHelpers.toAwtColor(appComponent.value().get()));
        });
        swingComponent.addPropertyChangeListener(JButtonColorChooser.PROPERTY_COLOR_CHANGED,
                e->{
                    appComponent.value().set(
                            SwingHelpers.fromAwtColor((Color) e.getNewValue(),appComponent.app())
                    );
                });
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }
}