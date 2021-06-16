package net.thevpc.echo.constraints;

import java.util.Objects;

public class Weight implements AppChildConstraint {
    private double x;
    private double y;

    public Weight(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return Double.compare(weight.x, x) == 0 && Double.compare(weight.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
