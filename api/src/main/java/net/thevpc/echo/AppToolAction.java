package net.thevpc.echo;

import net.thevpc.common.props.WritablePValue;

import java.awt.event.ActionListener;

public interface AppToolAction extends AppTool{
    String id();

    WritablePValue<ActionListener> action();
}
