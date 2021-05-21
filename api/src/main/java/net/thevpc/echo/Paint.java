package net.thevpc.echo;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppPaint;
import net.thevpc.echo.spi.peers.AppColorPeer;

public abstract class Paint implements AppPaint {
    protected Application app;
    public static String format(Paint c) {
        return c==null?"":((Paint)c).format();
    }

    public static Paint of(String text, Application app) {
        if(text==null||text.isEmpty()){
            return null;
        }
        return new Color(text, app);
    }

    public Paint(Application app) {
        this.app = app;
    }
}
