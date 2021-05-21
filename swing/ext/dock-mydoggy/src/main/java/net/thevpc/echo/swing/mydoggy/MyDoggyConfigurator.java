package net.thevpc.echo.swing.mydoggy;

import net.thevpc.echo.api.ApplicationToolkit;
import net.thevpc.echo.spi.ApplicationToolkitConfigurator;
import net.thevpc.echo.spi.peers.AppDockPeer;
import net.thevpc.echo.spi.peers.AppWindowPeer;
import net.thevpc.echo.impl.AbstractApplicationToolkit;

public class MyDoggyConfigurator implements ApplicationToolkitConfigurator {
    @Override
    public void configure(ApplicationToolkit toolkit) {
        AbstractApplicationToolkit a=(AbstractApplicationToolkit) toolkit;
        a.addPeerFactory(AppDockPeer.class, MyDoggyDockPeer.class);
        a.addPeerFactory(AppWindowPeer.class, MyDoggyAppContentWindow.class);
    }
}
