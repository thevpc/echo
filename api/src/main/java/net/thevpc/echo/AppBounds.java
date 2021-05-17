package net.thevpc.echo;

public class AppBounds {
    private double x;
    private double y;
    private double width;
    private double height;

    public AppBounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
