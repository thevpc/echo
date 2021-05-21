package net.thevpc.echo;

import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.spi.peers.AppFontPeer;

public class Font implements AppFont {
    private String family;
    private FontWeight weight;
    private FontPosture posture;
    private double size;
    private Application app;
    private AppFontPeer peer;

    public static String format(AppFont font){
        if(font==null){
            return "";
        }
        return font.format();
    }

    public static Font of(String font, Application app){
        if(font==null|| font.isEmpty()){
            return null;
        }
        return new Font(font,app);
    }
    public Font(String family, Application app) {
        throw new IllegalArgumentException("invalid font name");
    }
    public Font(String family, double size, FontWeight weight, FontPosture posture, Application app) {
        this.app=app;
        if (family == null || family.isEmpty()) {
            throw new IllegalArgumentException("invalid font name");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("invalid font size " + size);
        }
        this.family = family;
        this.weight = weight == null ? FontWeight.NORMAL : weight;
        this.posture = posture == null ? FontPosture.REGULAR : posture;
        this.size = size;
        peer = app.toolkit().createFontPeer(this);
    }

    @Override
    public AppFontPeer peer() {
        return peer;
    }

    @Override
    public Font derive(FontPosture posture) {
        return derive(null, null, null, posture);
    }

    @Override
    public Font derive(FontWeight weight) {
        return derive(null, null, weight, null);
    }

    @Override
    public Font derive(double size) {
        return derive(null, size, null, null);
    }

    @Override
    public Font derive(String name) {
        return derive(name, null, null, null);
    }

    @Override
    public Font derive(String name, Double size, FontWeight weight, FontPosture posture) {
        return new Font(
                name == null ? this.family : name,
                size == null ? this.size : size,
                weight == null ? this.weight : weight,
                posture == null ? this.posture : posture,app
        );
    }

    @Override
    public String family() {
        return family;
    }

    @Override
    public FontWeight weight() {
        return weight;
    }

    @Override
    public FontPosture posture() {
        return posture;
    }

    @Override
    public double size() {
        return size;
    }

    @Override
    public String format() {
        throw new IllegalArgumentException("implement me");
    }
}
