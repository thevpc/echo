package net.thevpc.echo.demos.demo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.AppWorkspace;
import net.thevpc.echo.Application;
import net.thevpc.echo.constraints.ParentLayout;
import net.thevpc.echo.impl.components.Button;
import net.thevpc.echo.impl.components.Panel;
import net.thevpc.echo.impl.components.Workspace;
import net.thevpc.echo.jfx.FxApplication;
import net.thevpc.echo.swing.SwingApplication;

import javax.swing.*;

public class EchoAppDemo003 {

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
        app.mainFrame().get().workspace().get()
                .with((AppWorkspace w)->{
                    w.addWindow("Test",
                                    new Button(app)
                                    .with((Button b)->{b.tool().title().set(Str.of("Hello"));})
                            ,
                                    AppWindowAnchor.LEFT
                            );
                });
//        JOptionPane.showMessageDialog(null,
//                new Workspace(app)
//                        .peer().toolkitComponent()
//        );
    }
}
