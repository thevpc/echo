package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.impl.tools.ToolText;

public class TextField extends TextBase implements AppTextField {
    public TextField(AppToolText tool) {
        super(tool);
    }
    public TextField(Application app) {
        super(new ToolText(app));
    }

}