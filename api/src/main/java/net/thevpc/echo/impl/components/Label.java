package net.thevpc.echo.impl.components;

import net.thevpc.echo.*;
import net.thevpc.echo.api.components.AppLabel;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.impl.tools.ToolText;

public class Label extends AppComponentBase implements AppLabel {
    public Label(Application app) {
        super(new ToolText(app));
    }

    public Label(AppToolText app) {
        super(app);
    }


    public AppToolText tool() {
        return (AppToolText) super.tool();
    }

}

