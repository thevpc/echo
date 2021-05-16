package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.tools.AppToolCalendar;
import net.thevpc.echo.api.tools.AppToolColor;

public class ToolColor extends AppToolBase implements AppToolColor {
    private WritableValue<AppColor> value= Props.of("value").valueOf(AppColor.class);

    public ToolColor(String id, Application app) {
        super(id, app);
        propagateEvents(value);
    }

    public ToolColor(Application app) {
        this(null,app);
    }

    @Override
    public WritableValue<AppColor> value() {
        return value;
    }
}
