package net.thevpc.echo;

import net.thevpc.common.props.PValue;

public interface ApplicationBuilder {
    PValue<AppWindowBuilder> mainWindowBuilder();
}
