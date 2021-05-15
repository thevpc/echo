package net.thevpc.echo.impl.tools;

import net.thevpc.echo.*;
import net.thevpc.echo.api.WritableStr;
import net.thevpc.echo.api.tools.AppToolText;
import net.thevpc.echo.props.AppProps;

public class ToolText extends AppToolBase implements AppToolText {

    private WritableStr value;
    public ToolText(Application app) {
        super(null, app);
    }
    protected void init(){
        value = AppProps.of("text",app()).strOf( null);
    }

    @Override
    public WritableStr text() {
        return value;
    }
}
