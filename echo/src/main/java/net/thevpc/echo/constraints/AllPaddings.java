package net.thevpc.echo.constraints;

public class AllPaddings implements AppParentConstraint {
    private double width;
    private double height;

    public static AllPaddings of(double width){
        return new AllPaddings(width, width);
    }

    public static AllPaddings of(double width, double height){
        return new AllPaddings(width, height);
    }
    
    public AllPaddings(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public AllPaddings setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getHeight() {
        return height;
    }

    public AllPaddings setHeight(double height) {
        this.height = height;
        return this;
    }
}
