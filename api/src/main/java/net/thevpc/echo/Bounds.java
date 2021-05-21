package net.thevpc.echo;

public class Bounds {
    private double x;
    private double y;
    private double width;
    private double height;

    public Bounds(double x, double y, double width, double height) {
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
