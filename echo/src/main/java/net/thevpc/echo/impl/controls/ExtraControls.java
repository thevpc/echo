package net.thevpc.echo.impl.controls;

import net.thevpc.echo.Application;
import net.thevpc.common.i18n.Str;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.Label;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class ExtraControls {
    private static Timer timer = new Timer();

    public static AppLabel createDateLabel(Application app, String format) {
        AppLabel label = new Label(app);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format==null?"yyyy-MM-dd HH:mm:ss":format);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.toolkit().runUI(
                        () -> label.text().set(
                                Str.of(fmt.format(LocalDateTime.now()))
                        )
                );
            }
        },0, 1000);
        return label;
    }

    public static LabelProgressComponent createProgressLabel(Application app) {
        return new LabelProgressComponent(timer, app);
    }

    public static AppLabel createMemoryMonitorLabel(Application app) {
        //new MemoryUseIconTray(true)
        AppLabel label = new Label(app);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.toolkit().runUI(
                        () -> label.text().set(
                                Str.of(
                                        (Runtime.getRuntime().freeMemory()
                                                / 1024 / 1024)+"M"
                                )
                        )
                );
            }
        },0, 1000);
        return label;
    }
}
