package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppTemporalFieldModel;

import java.time.temporal.Temporal;

public class TemporalFieldModel<T extends Temporal> extends AppComponentModelBase implements AppTemporalFieldModel<T> {
    private WritableValue<T> value;

    public TemporalFieldModel(String id, Class<T> type, Application app) {
        super(id, app);
        value= Props.of("value").valueOf(type);
        propagateEvents(value);
    }

    public TemporalFieldModel(Class<T> type, Application app) {
        this(null, type, app);
    }

    @Override
    public WritableValue<T> value() {
        return value;
    }
}
