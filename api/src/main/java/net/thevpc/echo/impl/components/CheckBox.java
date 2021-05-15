package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppButton;
import net.thevpc.echo.api.components.AppCheckBox;
import net.thevpc.echo.api.tools.AppToolAction;
import net.thevpc.echo.api.tools.AppToolToggle;
import net.thevpc.echo.impl.tools.ToolAction;

public class CheckBox extends Toggle implements AppCheckBox {
    public CheckBox(Application app) {
        super(app);
    }
    public CheckBox(AppToolToggle tool) {
        super(tool);
    }

}

