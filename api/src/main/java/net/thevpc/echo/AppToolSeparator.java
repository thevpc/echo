package net.thevpc.echo;

import net.thevpc.common.props.WritablePValue;

public interface AppToolSeparator extends AppTool {
    WritablePValue<Integer> width();

    WritablePValue<Integer> height();

    ItemPath path();
}
