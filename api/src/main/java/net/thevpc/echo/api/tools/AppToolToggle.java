package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableString;

public interface AppToolToggle extends AppTool {

    WritableString group();

    WritableBoolean selected();
}
