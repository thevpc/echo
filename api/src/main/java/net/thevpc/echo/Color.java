package net.thevpc.echo;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.spi.peers.AppColorPeer;

public class Color extends Paint implements AppColor {
    private AppColorPeer peer;
    private double red;
    private double green;
    private double blue;
    private double opacity;

    public static String format(AppColor c) {
        return c==null?"":((Color)c).format();
    }

    public static Color of(String text, Application app) {
        if(text==null||text.isEmpty()){
            return null;
        }
        return new Color(text, app);
    }

    public Color(String text, Application app) {
        super(app);
        int rgba=app.toolkit().parseColor(text);
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

    @Override
    public int rgba() {
        return  ((opacity255() & 0xFF) << 24) |
                ((red255() & 0xFF) << 16) |
                ((green255() & 0xFF) << 8)  |
                ((blue255() & 0xFF) << 0);
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

    public static Color BLACK(Application app) {
        return new Color(0, 0, 0, 1, app);
    }

    public static Color WHITE(Application app) {
        return new Color(1, 1, 1, 1, app);
    }

    public static Color LIGHT_GRAY(Application app) {
        return new Color(192, 192, 192, app);
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

    public String format() {
        return "#"
                + toHex(red255(), 4)
                + toHex(green255(), 4)
                + toHex(blue255(), 4)
                + toHex(opacity255(), 4);
    }
    private static String toHex(int value, int pad) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(value));
        while (sb.length() < pad) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

}
