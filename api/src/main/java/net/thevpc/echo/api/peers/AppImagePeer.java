package net.thevpc.echo.api.peers;

public interface AppImagePeer {
    Object toolkitImage();
    double getHeight();

    double getWidth();

    boolean isVector();

    AppImagePeer scaleTo(double width, double height);

    AppImagePeer scaleBase(float widthFactor, float heightFactor);

    AppImagePeer scale(float widthFactor, float heightFactor);
}
