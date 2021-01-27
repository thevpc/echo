package net.thevpc.echo;

import net.thevpc.common.props.WritablePValue;

public interface AppToolBar extends AppToolContainer {
    WritablePValue<Boolean> visible();
}
