package net.thevpc.echo.impl.components;

import net.thevpc.echo.Application;
import net.thevpc.echo.api.components.AppChoice;
import net.thevpc.echo.api.tools.AppToolChoice;
import net.thevpc.echo.impl.tools.ToolChoice;

public class Choice<T> extends AppComponentBase implements AppChoice<T> {
    public Choice(AppToolChoice<T> tool) {
        super(tool);
    }
    public Choice(Class<T> componentType,Application app) {
        super(new ToolChoice<T>(componentType,app));
    }

    public AppToolChoice<T> tool() {
        return (AppToolChoice<T>) super.tool();
    }

}

