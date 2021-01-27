package net.thevpc.echo.swing.core.swing;

import net.thevpc.echo.AppComponent;
import net.thevpc.echo.AppComponentRendererContext;
import net.thevpc.echo.Application;

public interface NodeSupplierContext {
    <T> T getParentGuiComponent();

    Application getApplication();

    BindingNodeFactory getFactory();

    default <T> T  createGuiComponent(AppComponent comp) {
        return (T) getFactory().createGuiComponent(new AppComponentRendererContext(getParentGuiComponent(), comp, getApplication()));
    }
}
