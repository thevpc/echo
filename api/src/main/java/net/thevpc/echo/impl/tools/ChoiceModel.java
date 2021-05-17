package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableBoolean;
import net.thevpc.common.props.WritableList;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.tools.AppChoiceModel;

public class ChoiceModel<T> extends AppComponentModelBase implements AppChoiceModel<T> {
    private WritableList<T> values;
    private WritableBoolean multipleValues=Props.of("multipleValues").booleanOf(false);

    public ChoiceModel(Class<T> componentType, Application app) {
        super(null, app);
        values= Props.of("values").listOf(componentType);
        propagateEvents(values,multipleValues);
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
