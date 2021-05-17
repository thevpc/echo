package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppColor;
import net.thevpc.echo.api.tools.AppColorChooserModel;

public class ColorChooserModel extends AppComponentModelBase implements AppColorChooserModel {
    private WritableValue<AppColor> value= Props.of("value").valueOf(AppColor.class);

    public ColorChooserModel(String id, Application app) {
        super(id, app);
        propagateEvents(value);
    }

    public ColorChooserModel(Application app) {
        this(null,app);
    }

    @Override
    public WritableValue<AppColor> value() {
        return value;
    }
}
