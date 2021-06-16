package net.thevpc.echo.demos.demo;

import net.thevpc.echo.Application;
import net.thevpc.echo.impl.DefaultApplication;

import net.thevpc.echo.FontChooser;

public class EchoAppDemo005 {

    public static void main(String[] args) {
        if (true) {
            createApp(new DefaultApplication("swing"));
        }
        if (false) {
            createApp(new DefaultApplication("javafx"));
        }
    }

    public static void createApp(Application app) {
//        FontChooserDeprecated c=new FontChooserDeprecated(app);
//        c.showDialog(null);
        FontChooser c2=new FontChooser(app);
        c2.showDialog(null);
    }

  
}
