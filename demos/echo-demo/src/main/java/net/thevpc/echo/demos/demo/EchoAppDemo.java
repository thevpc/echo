package net.thevpc.echo.demos.demo;

import net.thevpc.common.i18n.I18nBundle;
import net.thevpc.common.i18n.Str;
import net.thevpc.common.props.Path;
import net.thevpc.echo.AppWindowAnchor;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponent;
import net.thevpc.echo.api.components.AppContainer;
import net.thevpc.echo.api.tools.AppComponentModel;
import net.thevpc.echo.constraints.Anchor;
import net.thevpc.echo.impl.components.*;
import net.thevpc.echo.jfx.FxApplication;
import net.thevpc.echo.jfx.FxApplicationToolkit;
import net.thevpc.echo.swing.SwingApplication;
import net.thevpc.echo.swing.SwingApplicationToolkit;

import javax.swing.*;
import java.util.Date;
import java.util.Locale;

public class EchoAppDemo {

    public static void main(String[] args) {
        if (true) {
            createApp(new SwingApplication());
        }
        if (false) {
            createApp(new FxApplication());
        }
    }

    public static void createApp(Application app) {
        app.startupConfig().enableAppearance().set(false);
        app.startupConfig().enableDesktop().set(true);
        app.startupConfig().enableDocking().set(true);
        app.startupConfig().enableIcons().set(false);
        app.startupConfig().enableLocales().set(null);
        app.startupConfig().enablePlaf().set(false);
        app.startupConfig().enableQuit().set(false);
        //fallback
        app.i18n().bundles().add(0, (I18nBundle) (String name, Locale locale) -> {
            if (name.startsWith("Action.")) {
                return name.substring(7);
            }
            if (name.startsWith("Message.")) {
                return name.substring(8);
            }
            if (name.startsWith("/")) {
                return name.substring(name.lastIndexOf('/') + 1);
            }
            return name;
        });
        app.start();
        AppContainerChildren<AppComponentModel, AppComponent> mwt = app.components();
//        mwt.addFolder(("/mainFrame/menuBar/File"));
//        mwt.addFolder(("/mainFrame/menuBar/Edit"));
//        mwt.addAction("/mainFrame/menuBar/File/Exit").bind(() -> JOptionPane.showMessageDialog(null, "Exit")).tool();
        mwt.add(
                new Button("B1", (e) -> {
                    System.out.println("Clicked " + new Date());
                    new Alert(app)
                            .setContentText(Str.of("Example Text"))
                            .withButtons("Please", "Never")
                            .showDialog();
                }, app),
                "/mainFrame/toolBar/Default/*");

        mwt.add(createPanel(app), "/mainFrame/Example");
        mwt.add(new Button("NewFile", () -> {
        }, app), Path.of("/mainFrame/toolBar/File/*"));
        mwt.add(new Button("NewFile", () -> {
        }, app), Path.of("/mainFrame/statusBar/File/*"));
        for (AppWindowAnchor value : AppWindowAnchor.values()) {
            for (int i = 0; i < 2; i++) {
                AppContainer ws = (AppContainer) app.mainFrame().get().workspace().get();
                ws.children().add(
                        new Window(
                                "id-"+value.name() + " " + (i + 1),
                                Str.of(value.name() + " " + (i + 1)),
                                value,
                                createComponent(app, value.name() + " " + (i + 1)), app
                        )
                );
            }
        }
        mwt.add(new Button("B2", () -> {
        }, app), Path.of("/mainFrame/toolBar/File/*"));
        mwt.add(new Button("B3", () -> {
        }, app), Path.of("/mainFrame/statusBar/File/*"));
        mwt.addSeparator(Path.of("/mainFrame/menuBar/File/*"));
        app.components().add(new RadioButton("Radio1", "group1", app), "/mainFrame/menuBar/File/*");
        app.components().add(new RadioButton("Radio2", "group1", app), "/mainFrame/menuBar/File/*");
        app.components().add(new RadioButton("Radio3", "group1", app), "/mainFrame/menuBar/File/*");
        mwt.add(new Spacer(app).expandX(), "/mainFrame/statusBar/Default/*");
        mwt.add(new Button(app), "/mainFrame/statusBar/Default/*");
    }

    protected static AppComponent createComponent(Application a, String text) {
        return a.toolkit().createComponent(
                (a.toolkit() instanceof SwingApplicationToolkit)
                        ? new JButton(text)
                        : (a.toolkit() instanceof FxApplicationToolkit)
                        ? new javafx.scene.control.Button(text)
                        : null
        );
    }

    private static AppComponent createPanel(Application app) {
        Panel panel = new Panel(app);
        Button b = new Button(app);
        b.constraints().add(Anchor.CENTER);
        panel.children().add(b);
        return panel;
    }

}
