package net.thevpc.echo.demos.demo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.Application;
import net.thevpc.echo.Button;
import net.thevpc.echo.Panel;
import net.thevpc.echo.VerticalPane;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.impl.DefaultApplication;

import javax.swing.*;

public class EchoAppDemo002 {

    public static void main(String[] args) {
        if (true) {
            createApp(new DefaultApplication("swing"));
        }
        if (false) {
            createApp(new DefaultApplication("javafx"));
        }
    }

    public static void createApp(Application app) {
        //app.start();
        JOptionPane.showMessageDialog(null,
                new VerticalPane( app)
                        .with((Panel p) -> {
                                    p.children().addAll(
                                            new Button(app)
                                                    .with((Button v) -> v.text().set(Str.of("Hello"))),
                                            new Button(app)
                                                    .with((Button v) -> v.text().set(Str.of("Bye")))
                                    );
                                }
                        )
                        .peer().toolkitComponent()
        );
    }
}
