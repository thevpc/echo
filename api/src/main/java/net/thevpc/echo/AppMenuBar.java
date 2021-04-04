package net.thevpc.echo;

import net.thevpc.common.props.WritableValue;

public interface AppMenuBar extends AppToolContainer {
    WritableValue<Boolean> visible();
}
