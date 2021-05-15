package net.thevpc.echo.jfx;

import net.thevpc.echo.impl.AbstractApplication;

public class FxApplication extends AbstractApplication {

    public FxApplication() {
        super(new FxApplicationStartupConfig());
        setToolkit(new FxApplicationToolkit(this));
    }

}
