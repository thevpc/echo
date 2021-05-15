package net.thevpc.echo.constraints;

import java.util.Objects;

public class Pos implements AppChildConstraint {
    private int x;
    private int y;

    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos span1 = (Pos) o;
        return x == span1.x && y == span1.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
