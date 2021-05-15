package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.api.tools.AppToolFont;
import net.thevpc.echo.impl.tools.ToolFont;

public class FontChooser extends AppComponentBase implements AppFontChooser {
    public FontChooser(AppToolFont tool) {
        super(tool);
    }
    public FontChooser(Application app) {
        super(new ToolFont(app));
    }

    public AppToolFont tool() {
        return (AppToolFont) super.tool();
    }

}

