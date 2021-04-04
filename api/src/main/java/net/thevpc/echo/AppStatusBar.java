package net.thevpc.echo;

import net.thevpc.common.props.WritableValue;

public interface AppStatusBar extends AppToolContainer {

    WritableValue<Boolean> visible();
}
