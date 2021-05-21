package net.thevpc.echo.demos.demo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.*;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.constraints.Layout;
import net.thevpc.echo.impl.DefaultApplication;

public class EchoAppDemo003 {

    public static void main(String[] args) {
        if (true) {
            createApp(new DefaultApplication("swing"));
        }
        if (false) {
            createApp(new DefaultApplication("javafx"));
        }
    }

    public static void createApp(Application app) {
        Frame frame = new Frame(app);
        DockPane content = new DockPane(app)
                .with((DockPane w) -> {
                    w.children().addAll(
                            new Window(
                                    "HelloId",
                                    Str.of("Hello"), Anchor.LEFT,
                                    new Button(app)
                                            .with((Button b) -> {
                                                b.text().set(Str.of("Hello"));
                                            })
                            )
                            ,
                            new Window(
                                    "ByeId",
                                    Str.of("Bye"), Anchor.LEFT,
                                    new Button(app)
                                            .with((Button b) -> {
                                                b.text().set(Str.of("Bye"));
                                            })
                            )
                            ,
                            new Window(
                                    "centerId",
                                    Str.of("Center"), Anchor.CENTER,
                                    new Panel(app)
                                            .with((Panel b) -> {
                                                b.parentConstraints().addAll(Layout.BORDER);
                                                b.children().addAll(
                                                        new Label(Str.of("My Title"), app)
                                                                .with((Label l) -> l.anchor().set(Anchor.TOP))
                                                        ,
                                                        new TextField(Str.of("My Title"), app)
                                                                .with((TextField l) -> l.anchor().set(Anchor.BOTTOM))
                                                        ,
                                                        new TabPane(app)
                                                                .with((TabPane t) -> {
                                                                    t.anchor().set(Anchor.CENTER);
                                                                    t.children().add(
                                                                            new Window("T", Str.i18n("T1"), Anchor.CENTER, app)
                                                                    );
                                                                })
                                                );
                                            })
                            )
                    );
                });
        frame.content().set(content);
        app.mainFrame().set(frame);
        frame.addDefaultMenus();
        app.start();
        app.waitFor();
//        JOptionPane.showMessageDialog(null,
//                new Workspace(app)
//                        .peer().toolkitComponent()
//        );
    }

}
