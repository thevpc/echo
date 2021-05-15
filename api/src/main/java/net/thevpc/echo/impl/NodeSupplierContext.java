package net.thevpc.echo.impl;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppComponent;

public interface NodeSupplierContext {
    AppComponent getParent();

    Application getApplication();

//    BindingNodeFactory getFactory();

}
