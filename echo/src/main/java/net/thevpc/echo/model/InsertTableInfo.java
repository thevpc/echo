package net.thevpc.echo.model;

public class InsertTableInfo {
    private final int columns;
    private final int rows;
    private final String styleClass;

    public InsertTableInfo(int columns, int rows, String styleClass) {
        this.columns = columns;
        this.rows = rows;
        this.styleClass = styleClass;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
