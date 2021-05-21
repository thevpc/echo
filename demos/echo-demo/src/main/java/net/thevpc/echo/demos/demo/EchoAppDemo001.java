package net.thevpc.echo.demos.demo;

import net.thevpc.echo.Application;
import net.thevpc.echo.Frame;
import net.thevpc.echo.WindowState;
import net.thevpc.echo.impl.DefaultApplication;

public class EchoAppDemo001 {

    public static void main(String[] args) {
        if (true) {
            createApp(new DefaultApplication("swing"));
        }
        if (false) {
            createApp(new DefaultApplication("javafx"));
        }
    }

    public static void createApp(Application app) {
        Frame frame=new Frame(app);
        app.mainFrame().set(frame);
        app.start();
    }
}
