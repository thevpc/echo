package net.thevpc.echo.constraints;

public class Padding implements AppChildConstraint {
    private double width;
    private double height;

    public Padding(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public Padding setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public Padding setHeight(double height) {
        this.height = height;
        return this;
    }
}
