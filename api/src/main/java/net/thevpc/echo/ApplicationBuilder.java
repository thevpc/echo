package net.thevpc.echo;

import net.thevpc.common.props.ObservableValue;

public interface ApplicationBuilder {
    ObservableValue<AppWindowBuilder> mainWindowBuilder();
}
