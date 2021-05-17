package net.thevpc.echo.impl.tools;

import net.thevpc.common.i18n.Str;
import net.thevpc.echo.*;
import net.thevpc.common.i18n.WritableStr;
import net.thevpc.echo.api.tools.AppTextModel;
import net.thevpc.echo.props.AppProps;

public class TextModel extends AppComponentModelBase implements AppTextModel {

    private WritableStr text;
    public TextModel(Application app) {
        this((String)null,app);
    }
    public TextModel(Str text, Application app) {
        this((String) null,app);
        text().set(text);
    }
    public TextModel(String id, Application app) {
        super(id, app);
        text = AppProps.of("text",app()).strOf( null);
        propagateEvents(text);
    }

    @Override
    public WritableStr text() {
        return text;
    }
}
