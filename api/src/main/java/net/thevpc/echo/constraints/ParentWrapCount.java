package net.thevpc.echo.constraints;

import java.util.Objects;

public class ParentWrapCount implements AppParentConstraint {
    private final int count;

    public ParentWrapCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentWrapCount that = (ParentWrapCount) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
