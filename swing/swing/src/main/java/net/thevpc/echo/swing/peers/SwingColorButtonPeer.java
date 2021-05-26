package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.color.JButtonColorChooser;
import net.thevpc.echo.api.components.*;
import net.thevpc.echo.spi.peers.AppColorButtonPeer;
import net.thevpc.echo.swing.SwingPeerHelper;
import net.thevpc.echo.swing.helpers.SwingHelpers;

public class SwingColorButtonPeer implements SwingPeer, AppColorButtonPeer {
    private JButtonColorChooser swingComponent;
    private AppColorButton appComponent;

    public SwingColorButtonPeer() {
    }

    public void install(AppComponent component0) {
        this.appComponent = (AppColorButton) component0;

        swingComponent=new JButtonColorChooser(null,true);
        SwingPeerHelper.installComponent(this.appComponent,swingComponent);
        appComponent.value().onChangeAndInit(()->{
            swingComponent.setValue(SwingHelpers.toAwtColor(appComponent.value().get()));
        });
        swingComponent.addPropertyChangeListener(JButtonColorChooser.PROPERTY_COLOR_SET,
                e->{
                    appComponent.value().set(
                            SwingHelpers.fromAwtColor(swingComponent.getValue(),appComponent.app())
                    );
                });
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }
}