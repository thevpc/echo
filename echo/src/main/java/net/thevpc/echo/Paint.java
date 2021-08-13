package net.thevpc.echo;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppPaint;
import net.thevpc.echo.spi.peers.AppColorPeer;

public abstract class Paint implements AppPaint {
    protected Application app;
    public static String format(Paint c) {
        return c==null?"":((Paint)c).format();
    }

    public static AppColor ofDefault(String text, Application app) {
        if (text == null || text.trim().isEmpty()) {
            text="<default>";
        }
        return of(text,app);
    }

    public static AppColor of(String text, Application app) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        return Color.of(text, app);
    }

    public Paint(Application app) {
        this.app = app;
    }
}
