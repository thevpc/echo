package net.thevpc.echo;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.api.components.AppTemporalField;
import net.thevpc.echo.impl.components.ControlBase;
import net.thevpc.echo.spi.peers.AppTemporalFieldPeer;

import java.time.temporal.Temporal;

public class TemporalField<T extends Temporal> extends ControlBase implements AppTemporalField {
    private WritableValue<T> value;

    public TemporalField(String id, Class<T> valueType, Application app) {
        super(id, app, AppTemporalField.class, AppTemporalFieldPeer.class);
        value = Props.of("value").valueOf(valueType);
        propagateEvents(value);
    }

    public TemporalField(Class<T> type, Application app) {
        this(null, type, app);
    }

    @Override
    public WritableValue<T> value() {
        return value;
    }

}

