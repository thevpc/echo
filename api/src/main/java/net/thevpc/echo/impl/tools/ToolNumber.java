package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.tools.AppToolColor;
import net.thevpc.echo.api.tools.AppToolNumber;

public class ToolNumber extends AppToolBase implements AppToolNumber {
    private WritableValue<Number> value= Props.of("value").valueOf(Number.class);

    public ToolNumber(String id, Application app) {
        super(id, app);
    }

    public ToolNumber(Application app) {
        super(app);
    }

    @Override
    public WritableValue<Number> value() {
        return value;
    }
}
