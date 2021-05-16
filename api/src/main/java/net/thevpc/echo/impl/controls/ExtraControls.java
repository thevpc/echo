package net.thevpc.echo.impl.controls;

import net.thevpc.echo.Application;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.impl.components.Label;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class ExtraControls {
    private static Timer timer = new Timer();

    public static AppLabel createDateLabel(Application app, String format) {
        AppLabel label = new Label(app);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.toolkit().runUI(
                        () -> label.tool().text().set(
                                Str.of(LocalDateTime.now().toString())
                        )
                );
            }
        }, 1000);
        return label;
    }

    public static Object createMemoryMonitorLabel(Application app) {
        //new MemoryUseIconTray(true)
        AppLabel label = new Label(app);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.toolkit().runUI(
                        () -> label.tool().text().set(
                                Str.of(
                                        Runtime.getRuntime().freeMemory()
                                                / 1024 / 1024
                                )
                        )
                );
            }
        }, 1000);
        return label;
    }
}
