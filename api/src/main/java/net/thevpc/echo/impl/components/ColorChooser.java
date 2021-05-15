package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.api.tools.AppToolColor;
import net.thevpc.echo.impl.tools.ToolColor;

public class ColorChooser extends AppComponentBase implements AppColorChooser {
    public ColorChooser(AppToolColor tool) {
        super(tool);
    }

    public ColorChooser(Application app) {
        super(new ToolColor(app));
    }

    public AppToolColor tool() {
        return (AppToolColor) super.tool();
    }

}

