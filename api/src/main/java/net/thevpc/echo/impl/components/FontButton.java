package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.api.tools.AppToolFont;
import net.thevpc.echo.impl.tools.ToolFont;

public class FontButton extends AppComponentBase implements AppFontChooser {
    public FontButton(AppToolFont tool) {
        super(tool);
    }
    public FontButton(Application app) {
        super(new ToolFont(app));
    }


    public AppToolFont tool() {
        return (AppToolFont) super.tool();
    }

}

