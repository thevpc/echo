package net.thevpc.echo;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.spi.peers.AppColorPeer;
import net.thevpc.echo.util.UserObjectsHelper;

public class Color extends Paint implements AppColor {
    private AppColorPeer peer;
    private double red;
    private double green;
    private double blue;
    private double opacity;

    public Color(String text, Application app) {
        super(app);
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("null or empty text: Call Color.of(...) instead");
        }
        text = text.trim();
        int rgba = app.toolkit().parseColor(text);
        red = ((rgba >> 16) & 0xFF) / 255.0;
        green = ((rgba >> 8) & 0xFF) / 255.0;
        blue = ((rgba >> 0) & 0xFF) / 255.0;
        opacity = ((rgba >> 24) & 0xff) / 255.0;
        peer = app.toolkit().createColorPeer(this);
    }

    public Color(int red, int green, int blue, Application app) {
        this(red / 255.0, green / 255.0, blue / 255.0, 1, app);
    }

    public Color(int rgba, boolean hasalpha, Application app) {
        super(app);
        if (!hasalpha) {
            rgba = 0xff000000 | rgba;
        }
        red = ((rgba >> 16) & 0xFF) / 255.0;
        green = ((rgba >> 8) & 0xFF) / 255.0;
        blue = ((rgba >> 0) & 0xFF) / 255.0;
        opacity = ((rgba >> 24) & 0xff) / 255.0;
        peer = app.toolkit().createColorPeer(this);
    }

    public Color(double red, double green, double blue, double opacity, Application app) {
        super(app);
        this.app = app;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.opacity = opacity;
        peer = app.toolkit().createColorPeer(this);
    }

    public static String format(AppColor c) {
        return c == null ? "" : ((Color) c).format();
    }

    public static AppColor ofDefault(String text, Application app) {
        if (text == null || text.trim().isEmpty()) {
            text = "<default>";
        }
        return of(text, app);
    }

    public static AppColor of(String text, Application app) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }
        if (text.equals("<default>")) {
            return DefaultColor.INSTANCE;
        }
        return new Color(text, app);
    }

    public static AppColor BLACK(Application app) {
        return app.userObjects().computeIfAbsent(
                UserObjectsHelper.CONST(Color.class, "BLACK")
                , i ->  new Color(0, 0, 0, 1, app)
        );
    }

    public static AppColor WHITE(Application app) {
        return app.userObjects().computeIfAbsent(
                UserObjectsHelper.CONST(Color.class, "WHITE")
                , i -> new Color(1.0, 1.0, 1.0, 1.0, app)
        );
    }

    public static AppColor YELLOW(Application app) {
        return app.userObjects().computeIfAbsent(
                UserObjectsHelper.CONST(Color.class, "YELLOW")
                , i -> new Color(255, 255, 0, app)
        );
    }
    public static AppColor LIGHT_GRAY(Application app) {
        return app.userObjects().computeIfAbsent(
                UserObjectsHelper.CONST(Color.class, "LIGHT_GRAY")
                , i -> new Color(192, 192, 192, app)
        );
    }

    public static AppColor DARK_GRAY(Application app) {
        return app.userObjects().computeIfAbsent(
                UserObjectsHelper.CONST(Color.class, "DARK_GRAY")
                , i -> new Color(64, 64, 64, app)
        );
    }

    private static String toHex(int value, int pad) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(value));
        while (sb.length() < pad) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    @Override
    public double red() {
        return red;
    }

    @Override
    public double green() {
        return green;
    }

    @Override
    public double blue() {
        return blue;
    }

    @Override
    public double opacity() {
        return opacity;
    }

    @Override
    public int red255() {
        return (int) (red() * 255);
    }

    @Override
    public int green255() {
        return (int) (green() * 255);
    }

    @Override
    public int blue255() {
        return (int) (blue() * 255);
    }

    @Override
    public int opacity255() {
        return (int) (opacity() * 255);
    }

    @Override
    public AppColorPeer peer() {
        return peer;
    }

    @Override
    public int rgba() {
        return ((opacity255() & 0xFF) << 24) |
                ((red255() & 0xFF) << 16) |
                ((green255() & 0xFF) << 8) |
                ((blue255() & 0xFF) << 0);
    }

    public String format() {
        return "#"
                + toHex(red255(), 4)
                + toHex(green255(), 4)
                + toHex(blue255(), 4)
                + toHex(opacity255(), 4);
    }

    @Override
    public String toString() {
        return format();
    }
}
