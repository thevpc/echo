package net.thevpc.echo.constraints;

import java.util.Objects;

public class Margin implements AppChildConstraint {
    private double top;
    private double left;
    private double bottom;
    private double right;

    public static Margin of(double all){
        return new Margin(all, all, all, all);
    }

    public static Margin of(double top, double left){
        return new Margin(top, left, top, left);
    }

    public static Margin of(double top, double left, double bottom, double right){
        return new Margin(top, left, bottom, right);
    }

    public Margin(double top, double left, double bottom, double right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public double getTop() {
        return top;
    }

    public double getLeft() {
        return left;
    }

    public double getBottom() {
        return bottom;
    }

    public double getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Margin margin = (Margin) o;
        return Double.compare(margin.top, top) == 0 && Double.compare(margin.left, left) == 0 && Double.compare(margin.bottom, bottom) == 0 && Double.compare(margin.right, right) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(top, left, bottom, right);
    }
}
