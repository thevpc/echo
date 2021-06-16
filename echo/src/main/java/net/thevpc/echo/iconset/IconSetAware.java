package net.thevpc.echo.iconset;

import net.thevpc.common.props.ObservableValue;

public interface IconSetAware {
    ObservableValue<String> iconSet();

    ObservableValue<IconConfig> iconConfig();
}
