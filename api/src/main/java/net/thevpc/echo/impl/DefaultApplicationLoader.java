package net.thevpc.echo.impl;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableInt;
import net.thevpc.common.props.WritableValue;
import net.thevpc.common.props.impl.SimpleProperty;
import net.thevpc.echo.Application;
import net.thevpc.echo.ApplicationLoader;
import net.thevpc.echo.ApplicationLoaderHandler;

public class DefaultApplicationLoader extends SimpleProperty implements ApplicationLoader {
    private WritableValue<Integer> max= Props.of("max").intOf(100);
    private WritableInt progress= Props.of("max").intOf(0);
    private WritableValue<ApplicationLoaderHandler> handler= Props.of("max").valueOf(ApplicationLoaderHandler.class);
    private Application app;
    public DefaultApplicationLoader(String name,Application app) {
        super(name);
        this.app=app;
        propagateEvents(max,progress,handler);
    }

    @Override
    public WritableValue<Integer> max() {
        return max;
    }

    @Override
    public WritableInt progress() {
        return progress;
    }

    @Override
    public WritableValue<ApplicationLoaderHandler> handler() {
        return handler;
    }
}
