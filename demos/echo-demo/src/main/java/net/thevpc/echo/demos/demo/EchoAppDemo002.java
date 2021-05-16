package net.thevpc.echo.demos.demo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.Application;
import net.thevpc.echo.constraints.ParentLayout;
import net.thevpc.echo.impl.components.Button;
import net.thevpc.echo.impl.components.Panel;
import net.thevpc.echo.jfx.FxApplication;
import net.thevpc.echo.swing.SwingApplication;

import javax.swing.*;

public class EchoAppDemo002 {

    public static void main(String[] args) {
        if (true) {
            createApp(new SwingApplication());
        }
        if (false) {
            createApp(new FxApplication());
        }
    }

    public static void createApp(Application app) {
        //app.start();
        JOptionPane.showMessageDialog(null,
                new Panel(app)
                        .with((Panel p) -> {
                            p.constraints().addAll(ParentLayout.VERTICAL);
                                p.children().addAll(
                                        new Button(app)
                                                .with( (Button v) -> v.tool().title().set(Str.of("Hello"))),
                                        new Button(app)
                                                .with((Button v) -> v.tool().title().set(Str.of("Bye")))
                                );
                            }
                        )
                        .peer().toolkitComponent()
        );
    }
}
