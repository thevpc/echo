package net.thevpc.echo.constraints;

import java.util.Objects;

public class Span implements AppChildConstraint {

    private int colSpan;
    private int rowSpan;

    public static Span col(int colSpan) {
        return of(colSpan, 1);
    }

    public static Span row(int rowSpan) {
        return of(1, rowSpan);
    }

    public static Span of(int colSpan, int rowSpan) {
        return new Span(colSpan, rowSpan);
    }

    public Span(int colSpan, int rowSpan) {
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Span span1 = (Span) o;
        return colSpan == span1.colSpan && rowSpan == span1.rowSpan;
    }

    @Override
    public int hashCode() {
        return Objects.hash(colSpan, rowSpan);
    }
}
