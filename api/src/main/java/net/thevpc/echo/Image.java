package net.thevpc.echo;

import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppImage;
import net.thevpc.echo.spi.peers.AppImagePeer;

import java.io.InputStream;
import java.net.URL;

public class Image implements AppImage {
    private Application app;
    private AppImagePeer peer;

    public Image(URL url, Application app) {
        this.app = app;
        peer = app.toolkit().createImagePeer(url);
    }

    public Image(double width, double height, AppColor color, Application app) {
        this.app = app;
        peer = app.toolkit().createImagePeer(width, height, color);
    }

    public Image(InputStream source, Application app) {
        this.app = app;
        peer = app.toolkit().createImagePeer(source);
    }

    public Image(AppImagePeer peer, Application app) {
        this.app = app;
        this.peer = peer;
    }

    public static Image of(URL resource, Application app) {
        if (resource == null) {
            return null;
        }
        return new Image(resource, app);
    }

    @Override
    public double getHeight() {
        return peer.getHeight();
    }

    @Override
    public double getWidth() {
        return peer.getWidth();
    }

    @Override
    public AppImage scaleTo(double width, double height) {
        return new Image(peer.scaleTo(width, height), app);
    }

    @Override
    public AppImage scaleBase(float widthFactor, float heightFactor) {
        return new Image(peer.scaleBase(widthFactor, heightFactor), app);
    }

    @Override
    public AppImage scale(float widthFactor, float heightFactor) {
        return new Image(peer.scale(widthFactor, heightFactor), app);
    }

    public AppImagePeer peer() {
        return peer;
    }
}
