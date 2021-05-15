package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppTextArea;
import net.thevpc.echo.api.components.AppTextField;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.impl.tools.ToolText;

public class TextArea extends TextBase implements AppTextArea {
    public TextArea(AppToolText tool) {
        super(tool);
    }

    public TextArea(Application app) {
        super(new ToolText(app));
    }

    public AppToolText tool() {
        return (AppToolText) super.tool();
    }
}
