package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppToggle;
import net.thevpc.echo.api.tools.AppToolToggle;
import net.thevpc.echo.impl.tools.ToolToggle;

public class Toggle extends AppComponentBase implements AppToggle {
    public Toggle(Application app) {
        super(new ToolToggle(app));
    }

    public Toggle(AppToolToggle tool) {
        super(tool);
    }

    public AppToolToggle tool() {
        return (AppToolToggle) super.tool();
    }

}

