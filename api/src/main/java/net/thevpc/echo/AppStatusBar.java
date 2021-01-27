package net.thevpc.echo;

import net.thevpc.common.props.WritablePValue;

public interface AppStatusBar extends AppToolContainer {

    WritablePValue<Boolean> visible();
}
