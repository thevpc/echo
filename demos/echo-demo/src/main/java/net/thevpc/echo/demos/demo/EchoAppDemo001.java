package net.thevpc.echo.demos.demo;

import net.thevpc.echo.Application;
import net.thevpc.echo.jfx.FxApplication;
import net.thevpc.echo.swing.SwingApplication;

public class EchoAppDemo001 {

    public static void main(String[] args) {
        if (true) {
            createApp(new SwingApplication());
        }
        if (false) {
            createApp(new FxApplication());
        }
    }

    public static void createApp(Application app) {
        app.start();
    }
}
