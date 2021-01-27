package net.thevpc.echo;

import net.thevpc.common.props.WritablePValue;

public interface AppMenuBar extends AppToolContainer {
    WritablePValue<Boolean> visible();
}
