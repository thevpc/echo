package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.tools.AppToolColor;
import net.thevpc.echo.api.tools.AppToolFont;

public class ToolFont extends AppToolBase implements AppToolFont {
    private WritableValue<AppFont> value= Props.of("value").valueOf(AppFont.class);

    public ToolFont(String id, Application app) {
        super(id, app);
        propagateEvents(value);
    }

    public ToolFont(Application app) {
        this(null,app);
    }

    @Override
    public WritableValue<AppFont> value() {
        return value;
    }
}
