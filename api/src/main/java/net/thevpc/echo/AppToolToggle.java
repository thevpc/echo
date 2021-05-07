package net.thevpc.echo;

import net.thevpc.common.props.WritableValue;

public interface AppToolToggle extends AppTool{
    String id();
    
    AppToolButtonType buttonType();

    WritableValue<String> group();

    WritableValue<Boolean> selected();
}
