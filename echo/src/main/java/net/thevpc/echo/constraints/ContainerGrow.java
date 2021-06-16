package net.thevpc.echo.constraints;

public enum ContainerGrow implements AppParentConstraint {
    ALL(false,false,false,false),
    CENTER(true,true,true,true),

    BOTTOM_ROW(true,false,false,false),
    TOP_ROW(false,false,true,false),
    CENTER_ROW(true,false,true,false),

    RIGHT_COLUMN(false,true,false,false),
    LEFT_COLUMN(false,false,false,true),
    CENTER_COLUMN(false,true,false,true),

    LEFT_CORNER(true,false,true,true),
    RIGHT_CORNER(true,true,true,false),
    TOP_CORNER(false,true,true,true),
    BOTTOM_CORNER(true,true,false,true),

    TOP_LEFT_CORNER(false,false,true,true),
    TOP_RIGHT_CORNER(false,true,true,false),
    BOTTOM_LEFT_CORNER(true,false,false,true),
    BOTTOM_RIGHT_CORNER(true,true,false,false),

    ;

    ContainerGrow(boolean topGlue, boolean leftGlue, boolean bottomGlue, boolean rightGlue) {
        this.topGlue = topGlue;
        this.leftGlue = leftGlue;
        this.bottomGlue = bottomGlue;
        this.rightGlue = rightGlue;
    }

    private boolean topGlue;
    private boolean leftGlue;
    private boolean bottomGlue;
    private boolean rightGlue;

    public boolean topGlue() {
        return topGlue;
    }

    public boolean leftGlue() {
        return leftGlue;
    }

    public boolean bottomGlue() {
        return bottomGlue;
    }

    public boolean rightGlue() {
        return rightGlue;
    }
}
