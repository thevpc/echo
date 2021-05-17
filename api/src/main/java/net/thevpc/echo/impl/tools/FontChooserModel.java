package net.thevpc.echo.impl.tools;

import net.thevpc.common.props.Props;
import net.thevpc.common.props.WritableValue;
import net.thevpc.echo.Application;
import net.thevpc.echo.api.AppFont;
import net.thevpc.echo.api.tools.AppFontChooserModel;

public class FontChooserModel extends AppComponentModelBase implements AppFontChooserModel {
    private WritableValue<AppFont> value= Props.of("value").valueOf(AppFont.class);

    public FontChooserModel(String id, Application app) {
        super(id, app);
        propagateEvents(value);
    }

    public FontChooserModel(Application app) {
        this(null,app);
    }

    @Override
    public WritableValue<AppFont> value() {
        return value;
    }
}
