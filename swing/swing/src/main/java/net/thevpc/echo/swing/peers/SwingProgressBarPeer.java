package net.thevpc.echo.swing.peers;

import net.thevpc.common.swing.button.JDropDownButton;
import net.thevpc.common.swing.label.JDropDownLabel;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppProgressBar;
import net.thevpc.echo.api.components.AppSpacer;
import net.thevpc.echo.spi.peers.AppProgressBarPeer;

import javax.swing.*;
import java.awt.*;

public class SwingProgressBarPeer implements SwingPeer, AppProgressBarPeer {
    private JProgressBar swingComponent;
    private AppProgressBar<? extends Number> appProgressBar;

    public SwingProgressBarPeer() {
    }

    public void install(AppComponent component) {
        if(swingComponent !=null) {
            return;
        }
        appProgressBar=(AppProgressBar<? extends Number>) component;
        swingComponent=new JProgressBar();
        appProgressBar.min().listeners().addInstall(()->{
            double val= appProgressBar.min().get().doubleValue();
            swingComponent.setMinimum((int)val);
        });
        appProgressBar.max().listeners().addInstall(()->{
            double val= appProgressBar.max().get().doubleValue();
            swingComponent.setMaximum((int)val);
        });
        appProgressBar.value().listeners().addInstall(()->{
            double val= appProgressBar.value().get().doubleValue();
            swingComponent.setValue((int)val);
        });
        appProgressBar.indeterminate().listeners().addInstall(()->{
            swingComponent.setIndeterminate(appProgressBar.indeterminate().get());
        });
        appProgressBar.text().listeners().addInstall(()->{
            swingComponent.setString(appProgressBar.text().getOr(x->x.value(appProgressBar.app().i18n())));
        });
        appProgressBar.textPainted().listeners().addInstall(()->{
            swingComponent.setStringPainted(appProgressBar.textPainted().get());
        });
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }
}
