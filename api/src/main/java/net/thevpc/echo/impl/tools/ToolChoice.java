package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppToolCalendar;
import net.thevpc.echo.api.tools.AppToolChoice;
import net.thevpc.echo.props.AppProps;

public class ToolChoice<T> extends AppToolBase implements AppToolChoice<T> {
    private WritableValue<T> value;
    private WritableList<T> values;
    private WritableBoolean multipleValues=Props.of("multipleValues").booleanOf(false);

    public ToolChoice(Class<T> componentType,Application app) {
        super(null, app);
        value= Props.of("value").valueOf(componentType);
        values= Props.of("values").listOf(componentType);
    }

    public WritableValue<T> value(){
        return value;
    }

    @Override
    public WritableList<T> values(){
        return values;
    }

    @Override
    public WritableBoolean multipleValues(){
        return multipleValues;
    }


}