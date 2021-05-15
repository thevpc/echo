package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.AppPath;
import net.thevpc.echo.api.components.AppButton;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppFontChooser;
import net.thevpc.echo.api.tools.AppToolAction;
import net.thevpc.echo.api.peers.AppComponentPeer;
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

