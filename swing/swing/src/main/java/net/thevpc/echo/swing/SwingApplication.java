package net.thevpc.echo.swing;

import net.thevpc.echo.impl.AbstractApplication;

public class SwingApplication extends AbstractApplication {

    public SwingApplication() {
        super(new SwingApplicationStartupConfig());
        setToolkit(new SwingApplicationToolkit(this));
    }
}
