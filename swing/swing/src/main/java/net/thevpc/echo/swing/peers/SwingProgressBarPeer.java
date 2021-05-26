package net.thevpc.echo.swing.peers;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppProgressBar;
import net.thevpc.echo.impl.Applications;
import net.thevpc.echo.spi.peers.AppProgressBarPeer;

import javax.swing.*;

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
        appProgressBar.min().onChangeAndInit(()->{
            double val= appProgressBar.min().get().doubleValue();
            swingComponent.setMinimum((int)val);
        });
        appProgressBar.max().onChangeAndInit(()->{
            double val= appProgressBar.max().get().doubleValue();
            swingComponent.setMaximum((int)val);
        });
        appProgressBar.value().onChangeAndInit(()->{
            double val= appProgressBar.value().get().doubleValue();
            swingComponent.setValue((int)val);
        });
        appProgressBar.indeterminate().onChangeAndInit(()->{
            swingComponent.setIndeterminate(appProgressBar.indeterminate().get());
        });
        appProgressBar.text().onChangeAndInit(()->{
            swingComponent.setString(Applications.rawString(appProgressBar.text(),appProgressBar));
        });
        appProgressBar.locale().onChangeAndInit(()->{
            swingComponent.setString(Applications.rawString(appProgressBar.text(),appProgressBar));
        });
        appProgressBar.textPainted().onChangeAndInit(()->{
            swingComponent.setStringPainted(appProgressBar.textPainted().get());
        });
    }

    @Override
    public Object toolkitComponent() {
        return swingComponent;
    }
}
