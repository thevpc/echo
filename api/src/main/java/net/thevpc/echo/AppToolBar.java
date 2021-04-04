package net.thevpc.echo;

import net.thevpc.common.props.WritableValue;

public interface AppToolBar extends AppToolContainer {
    WritableValue<Boolean> visible();
}
