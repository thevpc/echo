package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppNumberFieldModel;

public class NumberFieldModel extends AppComponentModelBase implements AppNumberFieldModel {
    private WritableValue<Number> value= Props.of("value").valueOf(Number.class);

    public NumberFieldModel(String id, Application app) {
        super(id, app);
        propagateEvents(value);
    }

    public NumberFieldModel(Application app) {
        this(null,app);
    }

    @Override
    public WritableValue<Number> value() {
        return value;
    }
}
