package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppNumberField;
import net.thevpc.echo.api.tools.AppToolNumber;
import net.thevpc.echo.impl.tools.ToolNumber;

public class NumberField extends AppComponentBase implements AppNumberField {
    public NumberField(AppToolNumber tool) {
        super(tool);
    }

    public NumberField(Application app) {
        super(new ToolNumber(app));
    }

    public AppToolNumber tool() {
        return (AppToolNumber) super.tool();
    }

}

