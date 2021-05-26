package net.thevpc.echo.constraints;

import java.util.Objects;

public class AllMargins implements AppParentConstraint {

    private double top;
    private double left;
    private double bottom;
    private double right;

    public static AllMargins of(double top, double left, double bottom, double right) {
        return new AllMargins(top, left, bottom, right);
    }

    public static AllMargins of(double all) {
        return new AllMargins(all, all, all, all);
    }

    public static AllMargins of(double top, double left) {
        return new AllMargins(top, left, top, left);
    }

    public AllMargins(double top, double left, double bottom, double right) {
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AllMargins margin = (AllMargins) o;
        return Double.compare(margin.top, top) == 0 && Double.compare(margin.left, left) == 0 && Double.compare(margin.bottom, bottom) == 0 && Double.compare(margin.right, right) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(top, left, bottom, right);
    }
}
