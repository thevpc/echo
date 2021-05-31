package net.thevpc.echo.demos.demo;

import net.thevpc.echo.Application;
import net.thevpc.echo.Color;
import net.thevpc.echo.Dimension;
import net.thevpc.echo.Panel;
import net.thevpc.echo.constraints.ContainerGrow;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.impl.DefaultApplication;

import javax.swing.*;

public class EchoAppDemo004 {

    public static void main(String[] args) {
        if (true) {
            createApp(new DefaultApplication("swing"));
        }
        if (false) {
            createApp(new DefaultApplication("javafx"));
        }
    }

    public static void createApp(Application app) {
//        createApp(app, ContainerGrow.TOP_ROW);
//        createApp(app, ContainerGrow.BOTTOM_ROW);
//        createApp(app, ContainerGrow.CENTER_ROW);
//        createApp(app, ContainerGrow.LEFT_COLUMN);
//        createApp(app, ContainerGrow.RIGHT_COLUMN);
//        createApp(app, ContainerGrow.CENTER_COLUMN);
//        createApp(app, ContainerGrow.CENTER);
//        createApp(app, ContainerGrow.ALL);
//        createApp(app, ContainerGrow.TOP_LEFT_CORNER);
//        createApp(app, ContainerGrow.TOP_RIGHT_CORNER);
//        createApp(app, ContainerGrow.BOTTOM_LEFT_CORNER);
//        createApp(app, ContainerGrow.BOTTOM_RIGHT_CORNER);
//        createApp(app, ContainerGrow.LEFT_CORNER);
//        createApp(app, ContainerGrow.RIGHT_CORNER);
//        createApp(app, ContainerGrow.TOP_CORNER);
//        createApp(app, ContainerGrow.BOTTOM_CORNER);
        for (ContainerGrow grow : ContainerGrow.values()) {
            createApp(app, grow);
        }
    }

    public static void createApp(Application app, ContainerGrow grow) {
        Panel p = new Panel("Parent", Layout.BORDER, app);
        p.parentConstraints().add(grow);
        p.prefSize().set(new Dimension(400, 400));
        Panel c = new Panel("Child", Layout.BORDER, app);
        c.opaque().set(true);
        c.backgroundColor().set(Color.BLACK(app));
        c.prefSize().set(new Dimension(60, 60));
        p.children().add(c);
        JOptionPane.showMessageDialog(null,
                p.peer().toolkitComponent(), grow.toString(), JOptionPane.INFORMATION_MESSAGE
        );
    }
}
