package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppComponentOptions;
import net.thevpc.echo.api.components.AppText;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.impl.tools.ToolText;

public class TextBase extends AppComponentBase implements AppText {
    public TextBase(AppToolText tool) {
        super(tool);
    }
    public TextBase(Application app) {
        super(new ToolText(app));
    }

    public AppToolText tool() {
        return (AppToolText) super.tool();
    }

}