package net.thevpc.echo;

import net.thevpc.common.props.WritableInt;
import net.thevpc.common.props.WritableValue;

public interface ApplicationLoader {
    WritableValue<Integer> max();
    WritableInt progress();
    WritableValue<ApplicationLoaderHandler> handler();
}
