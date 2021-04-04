package net.thevpc.echo;

import net.thevpc.common.props.WritableValue;

public interface AppToolSeparator extends AppTool {
    WritableValue<Integer> width();

    WritableValue<Integer> height();

    ItemPath path();
}
