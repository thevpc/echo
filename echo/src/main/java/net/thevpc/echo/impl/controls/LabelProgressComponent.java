package net.thevpc.echo.impl.controls;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.Application;
import net.thevpc.echo.Label;
import net.thevpc.echo.api.components.AppLabel;

import java.util.Timer;
import java.util.TimerTask;

public class LabelProgressComponent {
    private boolean indeterminate;
    private AppLabel label;


    public LabelProgressComponent(Timer timer, Application app) {
        label = new Label(app);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                app.toolkit().runUI(
                        () -> label.text().set(computeText())
                );
            }
        }, 0, 1000);
    }


    private String[] anim = {
            "|"
            , "/"
            , "-"
            , "\\"
            , "|"
            , "/"
            , "-"
            , "\\"
    };

    private Str computeText() {
        if (indeterminate) {
            int i = (int) ((System.currentTimeMillis() / 1000) % anim.length);
            return Str.of(anim[i]);
        }
        return Str.of("");
    }

    public boolean isIndeterminate() {
        return indeterminate;
    }

    public LabelProgressComponent setIndeterminate(boolean indeterminate) {
        this.indeterminate = indeterminate;
        return this;
    }

    public AppLabel component() {
        return label;
    }
}
