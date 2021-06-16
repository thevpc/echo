package net.thevpc.echo.impl;

import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.Application;

public class DefaultNodeSupplierContext implements NodeSupplierContext {
    private AppComponent parentGuiComponent;
    private Application application;
//    private BindingNodeFactory factory;

    public DefaultNodeSupplierContext(AppComponent parentGuiComponent, Application application/*, BindingNodeFactory factory*/) {
        this.parentGuiComponent = parentGuiComponent;
        this.application = application;
//        this.factory = factory;
    }

    public AppComponent getParent() {
        return parentGuiComponent;
    }

    @Override
    public Application getApplication() {
        return application;
    }

//    @Override
//    public BindingNodeFactory getFactory() {
//        return factory;
//    }
}
