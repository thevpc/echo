package net.thevpc.echo;


import java.awt.event.ActionListener;
import net.thevpc.common.props.WritableValue;

public interface AppToolAction extends AppTool{
    String id();

    WritableValue<ActionListener> action();
}
