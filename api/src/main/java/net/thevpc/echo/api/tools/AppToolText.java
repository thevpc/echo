package net.thevpc.echo.api.tools;

import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableString;
import net.thevpc.echo.api.WritableStr;

public interface AppToolText extends AppTool {
    WritableStr text();
}