package net.thevpc.echo.impl.tools;

import net.thevpc.echo.*;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.props.AppProps;

public class ToolText extends AppToolBase implements AppToolText {

    private WritableStr value;
    public ToolText(Application app) {
        this(null,app);
    }
    public ToolText(String id,Application app) {
        super(id, app);
        value = AppProps.of("text",app()).strOf( null);
        propagateEvents(value);
    }

    @Override
    public WritableStr text() {
        return value;
    }
}
