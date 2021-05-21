package net.thevpc.echo.demos.demo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.*;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.impl.DefaultApplication;

import java.util.Arrays;
import java.util.List;

public class ExamplePanel {

    public static void main(String[] args) {
        if (true) {
            createApp(new DefaultApplication("swing"));
        }
        if (false) {
            createApp(new DefaultApplication("javafx"));
        }
    }

    public static void createApp(Application app) {
        app.mainFrame().set(
                new Frame(app)
                .with((Frame f)->{
                    Panel p1= new BorderPane( app);
                    Label lab1= new Label(Str.of("Hello"), app);
                    Button button= new Button("Button",Str.of("Bye"), app);
                    p1.children().add(lab1);
                    p1.children().clear();
                    p1.children().add(button);
                    f.content().set(p1);
                })
        );
        app.start();
        app.waitFor();
//        JOptionPane.showMessageDialog(null,
//                new Workspace(app)
//                        .peer().toolkitComponent()
//        );
    }

}
