package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppPasswordField;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.impl.tools.ToolText;

public class PasswordField extends TextBase implements AppPasswordField {
    public PasswordField(AppToolText tool) {
        super(tool);
    }

    public PasswordField(Application tool) {
        super(new ToolText(tool));
    }

    public AppToolText tool() {
        return (AppToolText) super.tool();
    }

}

