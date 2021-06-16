package net.thevpc.echo.constraints;

import java.util.Objects;

public class ColumnCount implements AppParentConstraint {
    private final int count;

    public static ColumnCount of(int count){
        return new ColumnCount(count);
    }
    
    public ColumnCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnCount that = (ColumnCount) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
