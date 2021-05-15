package net.thevpc.echo;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.peers.AppComponentPeer;

import java.util.List;

public interface AppComponentFactory {
    List<Class> getSupportedToolTypes();
    SupportSupplier<AppComponent> createComponent(AppComponentRendererContext context);
}
