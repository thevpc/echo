package net.thevpc.echo.demos.demo;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppDock;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.constraints.ParentLayout;
import net.thevpc.echo.impl.components.*;
import net.thevpc.echo.jfx.FxApplication;
import net.thevpc.echo.swing.SwingApplication;

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
        app.mainFrame().listeners().add(e->{
            Frame f = e.getNewValue();
            if(f !=null){
                f.workspace().set(new DockPane(app));
            }
        });
        app.start();
        app.mainFrame().get().workspace().get()
                .with((AppDock w) -> {
                    w.children().addAll(
                            new Window(
                                    "HelloId",
                                    Str.of("Hello"), AppWindowAnchor.LEFT,
                                    new Button(app)
                                            .with((Button b) -> {
                                                b.model().title().set(Str.of("Hello"));
                                            })
                            )
                            ,
                            new Window(
                                    "ByeId",
                                    Str.of("Bye"),AppWindowAnchor.LEFT,
                                    new Button(app)
                                            .with((Button b) -> {
                                                b.model().title().set(Str.of("Bye"));
                                            })
                            )
                            ,
                            new Window(
                                    "centerId",
                                    Str.of("Center"),AppWindowAnchor.CENTER,
                                    new Panel(app)
                                            .with((Panel b) -> {
                                                b.constraints().addAll(ParentLayout.BORDER);
                                                b.children().addAll(
                                                        new Label(Str.of("My Title"),app)
                                                            .with((Label l)->l.constraints().addAll(Anchor.TOP))
                                                        ,
                                                        new TextField(Str.of("My Title"),app)
                                                            .with((TextField l)->l.constraints().addAll(Anchor.BOTTOM))
                                                        ,
                                                        new Tabs(app)
                                                                .with((Tabs t)->{
                                                                    t.constraints().addAll(Anchor.CENTER);
                                                                    t.children().add(
                                                                            new Window("T",Str.i18n("T1"), AppWindowAnchor.CENTER, app)
                                                                    );
                                                                })
                                                );
                                            })
                            )
                    );
                });
        app.waitFor();
//        JOptionPane.showMessageDialog(null,
//                new Workspace(app)
//                        .peer().toolkitComponent()
//        );
    }

}
