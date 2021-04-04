package net.thevpc.echo;

import net.thevpc.common.props.WritableValue;

public interface AppToolCheckBox extends AppTool{
    String id();

    WritableValue<String> group();

    WritableValue<Boolean> selected();
}
