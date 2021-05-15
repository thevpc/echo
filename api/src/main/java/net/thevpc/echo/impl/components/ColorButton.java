package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppColorChooser;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppToggle;
import net.thevpc.echo.api.tools.AppToolColor;
import net.thevpc.echo.impl.tools.ToolColor;

public class ColorButton extends AppComponentBase implements AppColorChooser {
    public ColorButton(AppToolColor tool) {
        super(tool);
    }
    public ColorButton(Application app) {
        super(new ToolColor(app));
    }

    public AppToolColor tool() {
        return (AppToolColor) super.tool();
    }

}

