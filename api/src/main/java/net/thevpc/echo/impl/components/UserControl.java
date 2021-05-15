package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppUserControl;
import net.thevpc.echo.api.tools.AppToolCustom;
import net.thevpc.echo.impl.tools.ToolAction;

public class UserControl extends AppComponentBase implements AppUserControl {
    public UserControl(Application app) {
        super(new ToolAction(app));
    }
    public UserControl(AppToolCustom tool) {
        super(tool);
    }


    public AppToolCustom tool() {
        return (AppToolCustom) super.tool();
    }

}

